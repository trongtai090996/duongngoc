package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaiKhoan{
    @SerializedName("idtaikhoan")
    @Expose
    private int idtaikhoan;
    @SerializedName("hoten")
    @Expose
    private String hoten;
    @SerializedName("sodienthoai")
    @Expose
    private int sodienthoai;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("level")
    @Expose
    private int level;

    public TaiKhoan(int idtaikhoan, String hoten, int sodienthoai, String username, String password, int level) {
        this.idtaikhoan = idtaikhoan;
        this.hoten = hoten;
        this.sodienthoai = sodienthoai;
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public int getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(int idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(int sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
