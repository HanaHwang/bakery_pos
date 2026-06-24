package com.bakerypos.bakery_pos.model;

import jakarta.persistence.*;

@Entity
@Table(name="detail_penjualan")
public class DetailPenjualan{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_detail")
    private int idDetail;
    @Column(name="qty", nullable = false)
    private int qty;
    @Column(name="harga_satuan", nullable = false)
    private int hargaSatuan;

    @ManyToOne
    @JoinColumn(name="id_barang")
    private Barang barang;

    @ManyToOne
    @JoinColumn(name="id_penjualan")
    private Penjualan penjualan;

    public DetailPenjualan(){}

    public DetailPenjualan(int qty, int hargaSatuan, Barang barang, Penjualan penjualan){
        this.qty=qty;
        this.hargaSatuan=hargaSatuan;
        this.barang=barang;
        this.penjualan=penjualan;
    }

    @Transient
    public int getSubtotal(){
        return qty * hargaSatuan;
    }

    public int getIdDetail(){
        return idDetail;
    }

    public void setIdDetail(int idDetail){
        this.idDetail=idDetail;
    }

    public int getQty(){
        return qty;
    }

    public void setQty(int qty){
        this.qty=qty;
    }

    public int getHargaSatuan(){
        return hargaSatuan;
    }

    public void setHargaSatuan(int hargaSatuan){
        this.hargaSatuan=hargaSatuan;
    }

    public Barang getBarang(){
        return barang;
    }

    public void setBarang(Barang barang){
        this.barang=barang;
    }

    public Penjualan getPenjualan(){
        return penjualan;
    }

    public void setPenjualan(Penjualan penjualan){
        this.penjualan=penjualan;
    }
}