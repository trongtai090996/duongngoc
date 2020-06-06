package com.example.duongngoc2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoaiMonAn implements Serializable {
    @SerializedName("iddanhmuc")
    public int iddanhmuc;
    @SerializedName("tenloaimonan")
    public String tenloaimonan;
    @SerializedName("linkanh")
    public String linkanh;

    public LoaiMonAn(int iddanhmuc, String tenloaimonan, String linkanh) {
        this.iddanhmuc = iddanhmuc;
        this.tenloaimonan = tenloaimonan;
        this.linkanh = linkanh;
    }

    public int getIddanhmuc() {
        return iddanhmuc;
    }

    public void setIddanhmuc(int iddanhmuc) {
        this.iddanhmuc = iddanhmuc;
    }

    public String getTenloaimonan() {
        return tenloaimonan;
    }

    public void setTenloaimonan(String tenloaimonan) {
        this.tenloaimonan = tenloaimonan;
    }

    public String getLinkanh() {
        return linkanh;
    }

    public void setLinkanh(String linkanh) {
        this.linkanh = linkanh;
    }
}
