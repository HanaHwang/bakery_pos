package com.bakerypos.bakery_pos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="barang")
public class Barang{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_barang")
    private int idBarang;
    
    @Column(name="nama_barang")
    private String namaBarang;

    @Column(name="harga")
    private int harga;

    @Column(name="stok")
    private int stok;

    @Column(name="deskripsi")
    private String deskripsi;

    public Barang(){}

    public Barang(int idBarang, String namaBarang, int harga, int stok, String deskripsi){
        this.idBarang=idBarang;
        this.namaBarang=namaBarang;
        this.harga=harga;
        this.stok=stok;
        this.deskripsi=deskripsi;
    }

    public int getIdBarang(){
        return idBarang;
    }

    public void setIdBarang(int idBarang){
        this.idBarang=idBarang;
    }

    public String getNamaBarang(){
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang){
        this.namaBarang=namaBarang;
    }

    public int getHarga(){
        return harga;
    }

    public void setHarga(int harga){
        this.harga=harga;
    }

    public int getStok(){
        return stok;
    }

    public void setStok(int stok){
        this.stok=stok;
    }

    public String getDeskripsi(){
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi){
        this.deskripsi=deskripsi;
    }
}