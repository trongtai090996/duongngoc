package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoLuongDaBan {
    @SerializedName("idmonan")
    @Expose
    private int idmonan;
    @SerializedName("slm")
    @Expose
    private int slm;

    public SoLuongDaBan(int idmonan, int slm) {
        this.idmonan = idmonan;
        this.slm = slm;
    }

    public int getIdmonan() {
        return idmonan;
    }

    public void setIdmonan(int idmonan) {
        this.idmonan = idmonan;
    }

    public int getSlm() {
        return slm;
    }

    public void setSlm(int slm) {
        this.slm = slm;
    }
}
