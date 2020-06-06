package com.example.duongngoc2.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.duongngoc2.R;
import com.example.duongngoc2.activity.GioHangActivity;
import com.example.duongngoc2.activity.MainActivity;
import com.example.duongngoc2.model.ChiTietGioHang;
import com.example.duongngoc2.model.GioHang;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Save;
import com.example.duongngoc2.utils.Sever;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GioHangAdapter extends BaseAdapter {


    ArrayList<ChiTietGioHang> chiTietGioHangArrayList;
    Context context;

    public GioHangAdapter(ArrayList<ChiTietGioHang> chiTietGioHangArrayList, Context context) {
        this.chiTietGioHangArrayList = chiTietGioHangArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return chiTietGioHangArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return chiTietGioHangArrayList.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview_giohang,null);
        final ImageView imghinhanhgiohang=(ImageView) view.findViewById(R.id.imghinhgiohang);
        final TextView tvtenmonangiohang=(TextView) view.findViewById(R.id.tvtenmonangiohang);
        final TextView tvgiamonangiohang=(TextView) view.findViewById(R.id.tvgiamonangiohang);
        final TextView tvsoluonggiohang=(TextView) view.findViewById(R.id.tvsoluonggiohang);
        Button btconggiohang=(Button) view.findViewById(R.id.btconggiohang);
        Button bttrugiohang=(Button) view.findViewById(R.id.bttrugiohang);
        final ChiTietGioHang chiTietGioHang=(ChiTietGioHang) getItem(i);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<MonAn>> call=apiService.postMonAnTheoId(chiTietGioHang.getIdmonan());
        call.enqueue(new Callback<ArrayList<MonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<MonAn>> call, Response<ArrayList<MonAn>> response) {
                ArrayList<MonAn> monAns=response.body();
                tvtenmonangiohang.setText(monAns.get(0).getTenmonan());
                String pattern = "###,###,###";
                DecimalFormat decimalFormat = new DecimalFormat(pattern);
                tvgiamonangiohang.setText(""+decimalFormat.format(monAns.get(0).getGia())+" Đ");
                Picasso.with(context).load(monAns.get(0).getHinhanhmonan())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.cancel)
                        .into(imghinhanhgiohang);
            }

            @Override
            public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {

            }
        });

        tvsoluonggiohang.setText(""+chiTietGioHang.getSoluong());
        btconggiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmua=Integer.parseInt(tvsoluonggiohang.getText().toString());
                soluongmua=soluongmua+1;
                tvsoluonggiohang.setText(String.valueOf(soluongmua));
                for (int i=0;i< MainActivity.chiTietGioHangList.size();i++){
                    if (MainActivity.chiTietGioHangList.get(i).getIdtaikhoan()==CheckLogin.getIdtaikhoan()
                            && MainActivity.chiTietGioHangList.get(i).getIdmonan()==chiTietGioHang.getIdmonan()){
                        MainActivity.chiTietGioHangList.get(i).setSoluong(soluongmua);
                    }
                }
                for (int i=0;i<chiTietGioHangArrayList.size();i++){
                    if (chiTietGioHangArrayList.get(i).getIdmonan()==chiTietGioHang.getIdmonan()){
                        chiTietGioHangArrayList.get(i).setSoluong(soluongmua);
                    }
                }
                GioHangActivity.tinhtongtien();
            }
        });
        bttrugiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmua=Integer.parseInt(tvsoluonggiohang.getText().toString());
                if (soluongmua>0){soluongmua=soluongmua-1;}else {soluongmua=0;}
                tvsoluonggiohang.setText(String.valueOf(soluongmua));
                for (int i=0;i< MainActivity.chiTietGioHangList.size();i++){
                    if (MainActivity.chiTietGioHangList.get(i).getIdtaikhoan()==CheckLogin.getIdtaikhoan()
                            && MainActivity.chiTietGioHangList.get(i).getIdmonan()==chiTietGioHang.getIdmonan()){
                        if (soluongmua>0){
                            MainActivity.chiTietGioHangList.get(i).setSoluong(soluongmua);
                        }else {
                            MainActivity.chiTietGioHangList.remove(i);
                        }

                    }
                }
                for (int i=0;i<chiTietGioHangArrayList.size();i++){
                    if (chiTietGioHangArrayList.get(i).getIdmonan()==chiTietGioHang.getIdmonan()){
                        if (soluongmua>0){
                            chiTietGioHangArrayList.get(i).setSoluong(soluongmua);
                        }else {
                            chiTietGioHangArrayList.remove(i);
                            GioHangActivity.gioHangAdapter.notifyDataSetChanged();
                        }
                    }
                }
                GioHangActivity.tinhtongtien();
            }
        });



        return view;
    }
}
