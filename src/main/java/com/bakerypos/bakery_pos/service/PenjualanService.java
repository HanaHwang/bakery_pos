package com.bakerypos.bakery_pos.service;

import org.springframework.stereotype.Service;

import com.bakerypos.bakery_pos.model.Barang;
import com.bakerypos.bakery_pos.model.DetailPenjualan;
import com.bakerypos.bakery_pos.model.Penjualan;
import com.bakerypos.bakery_pos.repository.BarangRepository;
import com.bakerypos.bakery_pos.repository.CustomerRepository;
import com.bakerypos.bakery_pos.repository.PenjualanRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.bakerypos.bakery_pos.model.Customer;

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
        hitungTotal(penjualan);
        validasiStok(penjualan);
        updateStok(penjualan);
        updatePoinCustomer(penjualan);
        return penjualanRepository.save(penjualan);
    }

    public void delete(int id){
        penjualanRepository.deleteById(id);
    }

    private void hitungTotal(Penjualan penjualan){
        int subtotal=0;
        for(DetailPenjualan detail : penjualan.getDaftarDetail()){
            detail.setPenjualan(penjualan);
            subtotal+=detail.getSubtotal();
        }

        penjualan.setSubtotal(subtotal);
        int diskon = subtotal >= 10000 ? subtotal * 10 / 100 : 0;
        penjualan.setDiskon(diskon);
        int total = subtotal - diskon;
        penjualan.setTotalBayar(total);
        penjualan.setPoinDigunakan(total / 10000);
    }

    private void validasiStok(Penjualan penjualan){
        for(DetailPenjualan detail : penjualan.getDaftarDetail()){
            Barang barang = barangRepository.findById(detail.getBarang().getIdBarang()).orElseThrow();
            if(barang.getStok() < detail.getQty()){
                throw new RuntimeException("Stok tidak cukup.");
            }
            detail.setBarang(barang);
        }
    }

    private void updateStok(Penjualan penjualan){
        for(DetailPenjualan detail : penjualan.getDaftarDetail()){
            Barang barang = detail.getBarang();
            barang.setStok(barang.getStok() - detail.getQty());
        }
    }

    private void updatePoinCustomer(Penjualan penjualan){
        Customer customer = penjualan.getCustomer();
        if(customer == null) return;
        Customer dbCustomer = customerRepository.findById(customer.getIdCustomer()).orElseThrow();
        int poinDipakai = penjualan.getPoinDigunakan();
        if(poinDipakai > dbCustomer.getPoin()){
            throw new RuntimeException("Poin customer tidak mencukupi.");
        }
        dbCustomer.setPoin(dbCustomer.getPoin() - poinDipakai + penjualan.getPoinDidapatkan());
    }
}