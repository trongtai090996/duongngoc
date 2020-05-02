package com.example.duongngoc2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonHang {
    @SerializedName("iddonhang")
    @Expose
    private int iddonhang;
    @SerializedName("idtaikhoan")
    @Expose
    private int idtaikhoan;
    @SerializedName("ngaydat")
    @Expose
    private String ngaydat;
    @SerializedName("diadiemgiaohang")
    @Expose
    private String diadiemgiaohang;
    @SerializedName("tinhtrangdonhang")
    @Expose
    private String tinhtrangdonhang ;

    public DonHang(int iddonhang, int idtaikhoan, String ngaydat, String diadiemgiaohang, String tinhtrangdonhang) {
        this.iddonhang = iddonhang;
        this.idtaikhoan = idtaikhoan;
        this.ngaydat = ngaydat;
        this.diadiemgiaohang = diadiemgiaohang;
        this.tinhtrangdonhang = tinhtrangdonhang;
    }

    public int getIddonhang() {
        return iddonhang;
    }

    public void setIddonhang(int iddonhang) {
        this.iddonhang = iddonhang;
    }

    public int getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(int idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getDiadiemgiaohang() {
        return diadiemgiaohang;
    }

    public void setDiadiemgiaohang(String diadiemgiaohang) {
        this.diadiemgiaohang = diadiemgiaohang;
    }

    public String getTinhtrangdonhang() {
        return tinhtrangdonhang;
    }

    public void setTinhtrangdonhang(String tinhtrangdonhang) {
        this.tinhtrangdonhang = tinhtrangdonhang;
    }
}
