package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GioHang {
    @SerializedName("idmonan")
    @Expose
    private int idmonan;
    @SerializedName("soluong")
    @Expose
    private int soluong;

    public GioHang(int idmonan, int soluong) {
        this.idmonan = idmonan;
        this.soluong = soluong;
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
