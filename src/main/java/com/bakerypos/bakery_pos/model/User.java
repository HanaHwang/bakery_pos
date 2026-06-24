package com.bakerypos.bakery_pos.model;

import jakarta.persistence.*;

@Entity
@Table(name="user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private int idUser;
    @Column(name="nama_user")
    private String namaUser;
    @Column(name="no_telp")
    private String noTelp;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private String role;

    public User(){}

    public User(String namaUser, String noTelp, String password, String role){
        this.namaUser=namaUser;
        this.noTelp=noTelp;
        this.password=password;
        this.role=role;
    }

    public int getIdUser(){
        return idUser;
    }

    public void setIdUser(int idUser){
        this.idUser=idUser;
    }

    public String getNamaUser(){
        return namaUser;
    }

    public void setNamaUser(String namaUser){
        this.namaUser=namaUser;
    }

    public String getNoTelp(){
        return noTelp;
    }

    public void setNoTelp(String noTelp){
        this.noTelp=noTelp;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role=role;
    }

    public boolean isAdmin(){
        return role != null && role.equalsIgnoreCase("admin");
    }

    public boolean isCashier(){
        return role != null && role.equalsIgnoreCase("cashier");
    }
}