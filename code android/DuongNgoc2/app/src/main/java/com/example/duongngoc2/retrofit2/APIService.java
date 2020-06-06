package com.example.duongngoc2.retrofit2;

import com.example.duongngoc2.model.AllBinhLuan;
import com.example.duongngoc2.model.BinhLuan;
import com.example.duongngoc2.model.ChiTietDonHang;
import com.example.duongngoc2.model.DonHang;
import com.example.duongngoc2.model.GioHang;
import com.example.duongngoc2.model.Huyen;
import com.example.duongngoc2.model.LoaiMonAn;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.model.SoLuongDaBan;
import com.example.duongngoc2.model.TaiKhoan;
import com.example.duongngoc2.model.XaPhuong;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET("loaimonan.php")
    Call<ArrayList<LoaiMonAn>> getAllLoaimonan();

    @GET("monannoibat.php")
    Call<ArrayList<MonAn>> getAllMonannoibat();

    @FormUrlEncoded
    @POST("doantheoloai.php")
    Call<ArrayList<MonAn>> getAllMonantheoloai (@Field("iddanhmuc") int iddanhmuc);

    @FormUrlEncoded
    @POST("dangnhap.php")
    Call<ArrayList<TaiKhoan>> getAllTaiKhoan (@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("dangki.php")
    Call<String> getDangKi (@Field("hoten") String hoten,@Field("sodienthoai") int sodienthoai,@Field("username") String username,
                            @Field("password") String password,@Field("level") int level);

    @FormUrlEncoded
    @POST("themdonhang.php")
    Call<String> getThemDonHang (@Field("idtaikhoan") int idtaikhoan, @Field("ngaydat") String ngaydat, @Field("phiship") float phiship,
                                 @Field("hinhthucnhan") String hinhthucnhan,@Field("diadiemgiaohang") String diadiemgiaohang);

    @FormUrlEncoded
    @POST("doantheoid.php")
    Call<ArrayList<MonAn>> getAllMonantheoid (@Field("idmonan") int idmonan);

    @FormUrlEncoded
    @POST("themchitietdonhang.php")
    Call<String> getThemChiTietDonHang (@Field("iddonhang") int iddonhang,@Field("idmonan") int idmonan,@Field("soluong") int soluong);

    @FormUrlEncoded
    @POST("donhangtheotaikhoan.php")
    Call<ArrayList<DonHang>> getAllDonHangtheotaikhoan (@Field("idtaikhoan") int idtaikhoan);

    @FormUrlEncoded
    @POST("huydonhang.php")
    Call<String> getHuyDonHang (@Field("iddonhang") int iddonhang);

    @FormUrlEncoded
    @POST("chitietdonhangtheoiddonhang.php")
    Call<ArrayList<ChiTietDonHang>> getChiTietDonHang (@Field("iddonhang") int iddonhang);

    @FormUrlEncoded
    @POST("capnhattaikhoan.php")
    Call<String> getCapNhatTaiKhoan (@Field("idtaikhoan") int idtaikhoan,@Field("hoten") String hoten,@Field("sodienthoai") int sodienthoai);

    @FormUrlEncoded
    @POST("doimatkhau.php")
    Call<String> postDoiMatKhau (@Field("idtaikhoan") int idtaikhoan,@Field("matkhaumoi") String matkhaumoi);

    @FormUrlEncoded
    @POST("xoaloaimonan.php")
    Call<String> postXoaLoaiMonAn (@Field("idloaimonan") int idloaimonan);

    @FormUrlEncoded
    @POST("sualoaimonan.php")
    Call<String> postSuaLoaiMonAn (@Field("iddanhmuc") int iddanhmuc,@Field("tenmoi") String tenmoi,@Field("anhmoi") String anhmoi);

    @FormUrlEncoded
    @POST("themloaimonan.php")
    Call<String> postThemLoaiMonAn (@Field("tenloaimonan") String tenloaimonan,@Field("linkanh") String linkanh);

    @FormUrlEncoded
    @POST("laytenloaimonan.php")
    Call<String> postTenLoaiMonAn (@Field("iddanhmuc") int iddanhmuc);

    @GET("monan.php")
    Call<ArrayList<MonAn>> AllMonAn();

    @FormUrlEncoded
    @POST("themmonan.php")
    Call<String> postThemMonAn (@Field("iddanhmuc") int iddanhmuc,@Field("tenmonan") String tenmonan,@Field("gia") int gia,
                                @Field("tinhtrang") String tinhtrang,
                                @Field("linkanh") String linkanh,@Field("mota") String mota);

    @FormUrlEncoded
    @POST("xoamonan.php")
    Call<String> postXoaMonAn (@Field("idmonan") int idmonan);

    @FormUrlEncoded
    @POST("suamonan.php")
    Call<String> postSuaMonAn (@Field("idmonan") int idmonan,@Field("iddanhmucmoi") int iddanhmucmoi,@Field("tenmoi") String tenmoi,
                                @Field("tinhtrangmoi") String tinhtrangmoi,
                                @Field("anhmoi") String anhmoi,@Field("motamoi") String motamoi,@Field("giamoi") int giamoi);

    @FormUrlEncoded
    @POST("tinhtongtien.php")
    Call<Integer> postTinhTongTien (@Field("iddonhang") int iddonhang);

    @GET("alldonhang.php")
    Call<ArrayList<DonHang>> AllDonHang();

    @GET("donhangnhanvien.php")
    Call<ArrayList<DonHang>> DonHangNhanVien();

    @FormUrlEncoded
    @POST("chuyentinhtrangdonhang.php")
    Call<String> postChuyenTinhTrang (@Field("iddonhang") int iddonhang,@Field("tinhtrangmoi") String tinhtrangmoi);

    @GET("tongdoanhthu.php")
    Call<Integer> TongDoanhThu();

    @FormUrlEncoded
    @POST("thongke.php")
    Call<Integer> postThongKe (@Field("ngaybatdau") String ngaybatdau,@Field("ngayketthuc") String ngayketthuc);

    @FormUrlEncoded
    @POST("monantheoid.php")
    Call<ArrayList<MonAn>> postMonAnTheoId (@Field("idmonan") int idmonan);

    @GET("tinhsoluongdaban.php")
    Call<ArrayList<SoLuongDaBan>> postSoLuongDaBan();

    @GET("alltaikhoan.php")
    Call<ArrayList<TaiKhoan>> AllTaiKhoan();

    @FormUrlEncoded
    @POST("xoataikhoan.php")
    Call<String> postXoaTaiKhoan (@Field("idtaikhoan") int idtaikhoan);

    @FormUrlEncoded
    @POST("suataikhoan.php")
    Call<String> postSuaTaiKhoan (@Field("idtaikhoan") int idtaikhoan,@Field("hoten") String hoten,@Field("sodienthoai") int sodienthoai,
                                  @Field("password") String password);
    @FormUrlEncoded
    @POST("khachhangtimkiem.php")
    Call<ArrayList<MonAn>> postTimKiem (@Field("tukhoa") String tukhoa);

    @FormUrlEncoded
    @POST("binhluantheomonan.php")
    Call<ArrayList<BinhLuan>> postBinhLuan (@Field("idmonan") int idmonan);

    @FormUrlEncoded
    @POST("thembinhluan.php")
    Call<String> postThemBinhLuan (@Field("idmonan") int idmonan,@Field("idtaikhoan") int idtaikhoan,@Field("noidung") String noidung,@Field("ngaybinhluan") String ngaybinhluan);

    @FormUrlEncoded
    @POST("xoabinhluan.php")
    Call<String> postXoaBinhLuan (@Field("idbinhluan") int idbinhluan);

    @GET("allbinhluan.php")
    Call<ArrayList<AllBinhLuan>> AllBinhLuan ();

    @GET("allhuyen.php")
    Call<ArrayList<Huyen>> AllHuyen ();

    @FormUrlEncoded
    @POST("xaphuongtheohuyen.php")
    Call<ArrayList<XaPhuong>> postXaPhuong (@Field("idhuyen") int idhuyen);
}

