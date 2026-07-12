package com.bakerypos.bakery_pos.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bakerypos.bakery_pos.model.*;
import com.bakerypos.bakery_pos.repository.*;
import java.util.List;

@Service
@Transactional
public class PenjualanService {
    private final PenjualanRepository penjualanRepository;
    private final BarangRepository barangRepository;
    private final CustomerRepository customerRepository;

    public PenjualanService(PenjualanRepository penjualanRepository, BarangRepository barangRepository, CustomerRepository customerRepository){
        this.penjualanRepository=penjualanRepository;
        this.barangRepository=barangRepository;
        this.customerRepository=customerRepository;
    }

    public List<Penjualan> getAllPenjualan(){
        return penjualanRepository.findAll();
    }

    public Penjualan getPenjualanById(int id){
        return penjualanRepository.findById(id).orElse(null);
    }

    public Penjualan save(Penjualan penjualan){
        // processing item/validation, accumulates subtotal, reduce stocks
        int subtotal = procesItemDanStok(penjualan);

        // calculate
        hitungTotalKeuangan(penjualan, subtotal);

        //update info customers
        updatePoinCustomer(penjualan);

        return penjualanRepository.save(penjualan);
    }

    public void delete(int id){
        penjualanRepository.deleteById(id);
    }

    private int procesItemDanStok(Penjualan penjualan){
        int acumulatedSubtotal = 0;
        for(DetailPenjualan detail: penjualan.getDaftarDetail()){
            detail.setPenjualan(penjualan);

            // fetch db, manage entity state
            Barang barang = barangRepository.findById(detail.getBarang().getIdBarang()).orElseThrow(()->new RuntimeException("Barang tidak ditemukan"));

            //condition check
            if(barang.getStok()<detail.getQty()){
                throw new RuntimeException("Stok " + barang.getNamaBarang() + " tidak cukup.");
            }
            
            barang.setStok(barang.getStok()-detail.getQty());
            detail.setBarang(barang);

            detail.setHargaSatuan(barang.getHarga());

            acumulatedSubtotal += detail.getSubtotal();
        }
        return acumulatedSubtotal;
    }

    private void hitungTotalKeuangan(Penjualan penjualan, int subtotal){
        penjualan.setSubtotal(subtotal);

        //10% discount if orders 100k>
        int diskon = subtotal >=100000 ? subtotal * 10 / 100 : 0;
        penjualan.setDiskon(diskon);

        // deduct discoundt and point
        int total = subtotal - diskon - penjualan.getPoinDigunakan();
        if(total<0) total = 0;

        penjualan.setTotalBayar(total);
        penjualan.setPoinDidapatkan(total/10000);
    }

    private void updatePoinCustomer(Penjualan penjualan){
        Customer customer = penjualan.getCustomer();
        if(customer == null || customer.getIdCustomer() == 0) return;

        Customer dbCustomer = customerRepository.findById(customer.getIdCustomer()).orElseThrow(()->new RuntimeException("Customer tidak ditemukan"));
        
        int poinDipakai = penjualan.getPoinDigunakan();
        if(poinDipakai > dbCustomer.getPoin()){
            throw new RuntimeException("Poin customer tidak mencukupi.");
        }

        dbCustomer.setPoin(dbCustomer.getPoin() - poinDipakai + penjualan.getPoinDidapatkan());
    }
}