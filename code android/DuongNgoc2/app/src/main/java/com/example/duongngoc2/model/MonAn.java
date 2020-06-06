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

    @SerializedName("hinhanhmonan")
    @Expose
    private String hinhanhmonan;

    public MonAn(int idmonan, int iddanhmuc, String tenmonan, String mota, int gia, String tinhtrang, String hinhanhmonan) {
        this.idmonan = idmonan;
        this.iddanhmuc = iddanhmuc;
        this.tenmonan = tenmonan;
        this.mota = mota;
        this.gia = gia;
        this.tinhtrang = tinhtrang;
        this.hinhanhmonan = hinhanhmonan;
    }

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


    public String getHinhanhmonan() {
        return hinhanhmonan;
    }

    public void setHinhanhmonan(String hinhanhmonan) {
        this.hinhanhmonan = hinhanhmonan;
    }
}
