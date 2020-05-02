package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonAn {
    @SerializedName("idmonan")
    @Expose
    private int idmonan;
    @SerializedName("iddanhmuc")
    @Expose
    private int iddanhmuc;
    @SerializedName("tenmonan")
    @Expose
    private String tenmonan;
    @SerializedName("mota")
    @Expose
    private String mota;
    @SerializedName("gia")
    @Expose
    private int gia;
    @SerializedName("tinhtrang")
    @Expose
    private String tinhtrang;
    @SerializedName("soluong")
    @Expose
    private int soluong;
    @SerializedName("hinhanhmonan")
    @Expose
    private String hinhanhmonan;

    public int getIdmonan() {
        return idmonan;
    }

    public void setIdmonan(int idmonan) {
        this.idmonan = idmonan;
    }

    public int getIddanhmuc() {
        return iddanhmuc;
    }

    public void setIddanhmuc(int iddanhmuc) {
        this.iddanhmuc = iddanhmuc;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhanhmonan() {
        return hinhanhmonan;
    }

    public void setHinhanhmonan(String hinhanhmonan) {
        this.hinhanhmonan = hinhanhmonan;
    }
}
