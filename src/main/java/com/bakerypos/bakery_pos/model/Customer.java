package com.bakerypos.bakery_pos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="customer")
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_customer")
    private int idCustomer;

    @Column(name="nama_customer")
    private String nama;

    @Column(name="no_telp_customer")
    private String noTelp;

    @Column(name="poin")
    private int poin;

    @Column(name="expired_poin")
    private LocalDate expiredPoin;

    public Customer(){}

    public Customer(int idCustomer, String nama, String noTelp, int poin, LocalDate expiredPoin){
        this.idCustomer=idCustomer;
        this.nama=nama;
        this.noTelp=noTelp;
        this.poin=poin;
        this.expiredPoin=expiredPoin;
    }

    public int getIdCustomer(){
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer){
        this.idCustomer=idCustomer;
    }

    public String getNama(){
        return nama;
    }

    public void setNama(String nama){
        this.nama=nama;
    }

    public String getNoTelp(){
        return noTelp;
    }

    public void setNoTelp(String noTelp){
        this.noTelp=noTelp;
    }

    public int getPoin(){
        return poin;
    }

    public void setPoin(int poin){
        this.poin=poin;
    }

    public LocalDate getExpiredPoin(){
        return expiredPoin;
    }

    public void setExpiredPoin(LocalDate expiredPoin){
        this.expiredPoin=expiredPoin;
    }

    @Override
    public String toString(){
        return nama;
    }
}