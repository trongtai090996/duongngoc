package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietDonHang {
    @SerializedName("idchitietdonhang")
    @Expose
    private int idchitietdonhang;
    @SerializedName("iddonhang")
    @Expose
    private int iddonhang;
    @SerializedName("idmonan")
    @Expose
    private int idmonan;
    @SerializedName("soluong")
    @Expose
    private int soluong ;

    public ChiTietDonHang(int idchitietdonhang, int iddonhang, int idmonan, int soluong) {
        this.idchitietdonhang = idchitietdonhang;
        this.iddonhang = iddonhang;
        this.idmonan = idmonan;
        this.soluong = soluong;
    }

    public int getIdchitietdonhang() {
        return idchitietdonhang;
    }

    public void setIdchitietdonhang(int idchitietdonhang) {
        this.idchitietdonhang = idchitietdonhang;
    }

    public int getIddonhang() {
        return iddonhang;
    }

    public void setIddonhang(int iddonhang) {
        this.iddonhang = iddonhang;
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
