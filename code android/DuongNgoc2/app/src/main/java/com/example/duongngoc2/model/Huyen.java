package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Huyen {
    @SerializedName("idhuyen")
    @Expose
    private int idhuyen;
    @SerializedName("tenhuyen")
    @Expose
    private String tenhuyen;

    public Huyen(int idhuyen, String tenhuyen) {
        this.idhuyen = idhuyen;
        this.tenhuyen = tenhuyen;
    }

    public int getIdhuyen() {
        return idhuyen;
    }

    public void setIdhuyen(int idhuyen) {
        this.idhuyen = idhuyen;
    }

    public String getTenhuyen() {
        return tenhuyen;
    }

    public void setTenhuyen(String tenhuyen) {
        this.tenhuyen = tenhuyen;
    }
}
