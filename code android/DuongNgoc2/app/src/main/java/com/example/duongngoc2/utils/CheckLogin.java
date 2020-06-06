package com.example.duongngoc2.utils;

public class CheckLogin {
    public static int check=0;
    public static int idtaikhoan=0;
    public static String hoten="";
    public static int sodienthoai=0;
    public static String username="";
    public static String password="";
    public static int level=0;

    public CheckLogin() {
    }

    public static int getCheck() {
        return check;
    }

    public static void setCheck(int check) {
        CheckLogin.check = check;
    }

    public static int getIdtaikhoan() {
        return idtaikhoan;
    }

    public static void setIdtaikhoan(int idtaikhoan) {
        CheckLogin.idtaikhoan = idtaikhoan;
    }

    public static String getHoten() {
        return hoten;
    }

    public static void setHoten(String hoten) {
        CheckLogin.hoten = hoten;
    }

    public static int getSodienthoai() {
        return sodienthoai;
    }

    public static void setSodienthoai(int sodienthoai) {
        CheckLogin.sodienthoai = sodienthoai;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CheckLogin.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CheckLogin.password = password;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        CheckLogin.level = level;
    }
}
