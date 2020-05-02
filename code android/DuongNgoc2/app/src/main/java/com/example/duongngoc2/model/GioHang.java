package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GioHang {
    @SerializedName("idgiohang")
    @Expose
    private int idgiohang;
    @SerializedName("idtaikhoan")
    @Expose
    private int idtaikhoan;
    @SerializedName("idmonan")
    @Expose
    private int idmonan;
    @SerializedName("soluong")
    @Expose
    private int soluong;

    public GioHang(int idgiohang, int idtaikhoan, int idmonan, int soluong) {
        this.idgiohang = idgiohang;
        this.idtaikhoan = idtaikhoan;
        this.idmonan = idmonan;
        this.soluong = soluong;
    }

    public int getIdgiohang() {
        return idgiohang;
    }

    public void setIdgiohang(int idgiohang) {
        this.idgiohang = idgiohang;
    }

    public int getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(int idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public int getIdmonan() {
        return idmonan;
    }

    public void setIdmonan(int idmonan) {
        this.idmonan = idmonan;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
