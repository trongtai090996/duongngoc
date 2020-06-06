package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XaPhuong {
    @SerializedName("idxaphuong")
    @Expose
    private int idxaphuong;
    @SerializedName("idhuyen")
    @Expose
    private int idhuyen;
    @SerializedName("tenxaphuong")
    @Expose
    String tenxaphuong;
    @SerializedName("khoangcach")
    @Expose
    private float khoangcach;

    public XaPhuong(int idxaphuong, int idhuyen, String tenxaphuong, float khoangcach) {
        this.idxaphuong = idxaphuong;
        this.idhuyen = idhuyen;
        this.tenxaphuong = tenxaphuong;
        this.khoangcach = khoangcach;
    }

    public int getIdxaphuong() {
        return idxaphuong;
    }

    public void setIdxaphuong(int idxaphuong) {
        this.idxaphuong = idxaphuong;
    }

    public int getIdhuyen() {
        return idhuyen;
    }

    public void setIdhuyen(int idhuyen) {
        this.idhuyen = idhuyen;
    }

    public String getTenxaphuong() {
        return tenxaphuong;
    }

    public void setTenxaphuong(String tenxaphuong) {
        this.tenxaphuong = tenxaphuong;
    }

    public float getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(float khoangcach) {
        this.khoangcach = khoangcach;
    }
}
