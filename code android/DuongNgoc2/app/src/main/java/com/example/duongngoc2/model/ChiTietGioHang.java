package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietGioHang {
    @SerializedName("idtaikhoan")
    @Expose
    private int idtaikhoan;
    @SerializedName("idmonan")
    @Expose
    private int idmonan;
    @SerializedName("tenmonan")
    @Expose
    private String tenmonan;
    @SerializedName("gia")
    @Expose
    private int gia;
    @SerializedName("soluong")
    @Expose
    private int soluong ;
    @SerializedName("hinhanh")
    @Expose
    private String hinhanh;

    public ChiTietGioHang(int idtaikhoan, int idmonan, String tenmonan, int gia, int soluong, String hinhanh) {

        this.idtaikhoan = idtaikhoan;
        this.idmonan = idmonan;
        this.tenmonan = tenmonan;
        this.gia = gia;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
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

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
