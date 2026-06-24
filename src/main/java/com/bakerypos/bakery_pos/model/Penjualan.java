package com.bakerypos.bakery_pos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name="penjualan")
public class Penjualan{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_penjualan")
    private int idPenjualan;
    @Column(name="tanggal")
    private LocalDateTime tanggal;
    @Column(name="subtotal")
    private int subtotal;
    @Column(name="diskon")
    private int diskon;
    @Column(name="total_bayar")
    private int totalBayar;
    @Column(name="poin_didapatkan")
    private int poinDidapatkan;
    @Column(name="poin_digunakan")
    private int poinDigunakan;
    @Column(name="metode_pembayaran")
    private String metodePembayaran;

    @ManyToOne
    @JoinColumn(name="id_customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;

    @OneToMany(mappedBy = "penjualan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailPenjualan> daftarDetail = new ArrayList<>();

    public Penjualan(){}

    public Penjualan(LocalDateTime tanggal, int subtotal, int diskon, int totalBayar, int poinDidapatkan, int poinDigunakan, String metodePembayaran, Customer customer, User user){
        this.tanggal=tanggal;
        this.subtotal=subtotal;
        this.diskon=diskon;
        this.totalBayar=totalBayar;
        this.poinDidapatkan=poinDidapatkan;
        this.poinDigunakan=poinDigunakan;
        this.metodePembayaran=metodePembayaran;
        this.customer=customer;
        this.user=user;
    }

    public int getIdPenjualan(){
        return idPenjualan;
    }

    public void setIdPenjualan(int idPenjualan){
        this.idPenjualan=idPenjualan;
    }

    public LocalDateTime getTanggal(){
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal){
        this.tanggal=tanggal;
    }

    public int getSubtotal(){
        return subtotal;
    }

    public void setSubtotal(int subtotal){
        this.subtotal=subtotal;
    }

    public int getDiskon(){
        return diskon;
    }

    public void setDiskon(int diskon){
        this.diskon=diskon;
    }

    public int getTotalBayar(){
        return totalBayar;
    }

    public void setTotalBayar(int totalBayar){
        this.totalBayar=totalBayar;
    }

    public int getPoinDidapatkan(){
        return poinDidapatkan;
    }

    public void setPoinDidapatkan(int poinDidapatkan){
        this.poinDidapatkan=poinDidapatkan;
    }

    public int getPoinDigunakan(){
        return poinDigunakan;
    }

    public void setPoinDigunakan(int poinDigunakan){
        this.poinDigunakan=poinDigunakan;
    }

    public String getMetodePembayaran(){
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran){
        this.metodePembayaran=metodePembayaran;
    }

    public Customer getCustomer(){
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer=customer;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user=user;
    }

    public List<DetailPenjualan> getDaftarDetail(){
        return daftarDetail;
    }

    public void setDaftarDetail(List<DetailPenjualan> daftarDetail){
        this.daftarDetail=daftarDetail;
    }

    public void addDetail(DetailPenjualan detail){
        daftarDetail.add(detail);
        detail.setPenjualan(this);
    }

    public void removeDetail(DetailPenjualan detail){
        daftarDetail.remove(detail);
        detail.setPenjualan(null);
    }
}