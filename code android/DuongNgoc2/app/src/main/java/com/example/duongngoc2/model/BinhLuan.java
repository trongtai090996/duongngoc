package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BinhLuan {
    @SerializedName("idbinhluan")
    @Expose
    private int idbinhluan;
    @SerializedName("idmonan")
    @Expose
    private int idmonan;
    @SerializedName("idtaikhoan")
    @Expose
    private int idtaikhoan;
    @SerializedName("hoten")
    @Expose
    private String hoten;
    @SerializedName("noidung")
    @Expose
    private String noidung;
    @SerializedName("ngaybinhluan")
    @Expose
    private String ngaybinhluan;

    public BinhLuan(int idbinhluan, int idmonan, int idtaikhoan, String hoten, String noidung, String ngaybinhluan) {
        this.idbinhluan = idbinhluan;
        this.idmonan = idmonan;
        this.idtaikhoan = idtaikhoan;
        this.hoten = hoten;
        this.noidung = noidung;
        this.ngaybinhluan = ngaybinhluan;
    }

    public int getIdbinhluan() {
        return idbinhluan;
    }

    public void setIdbinhluan(int idbinhluan) {
        this.idbinhluan = idbinhluan;
    }

    public int getIdmonan() {
        return idmonan;
    }

    public void setIdmonan(int idmonan) {
        this.idmonan = idmonan;
    }

    public int getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(int idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgaybinhluan() {
        return ngaybinhluan;
    }

    public void setNgaybinhluan(String ngaybinhluan) {
        this.ngaybinhluan = ngaybinhluan;
    }
}
