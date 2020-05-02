package com.example.duongngoc2.adapter;

import android.content.Context;
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

import com.example.duongngoc2.R;
import com.example.duongngoc2.activity.GioHangActivity;
import com.example.duongngoc2.activity.MainActivity;
import com.example.duongngoc2.model.ChiTietGioHang;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
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
        ImageView imghinhanhgiohang=(ImageView) view.findViewById(R.id.imghinhgiohang);
        TextView tvtenmonangiohang=(TextView) view.findViewById(R.id.tvtenmonangiohang);
        TextView tvgiamonangiohang=(TextView) view.findViewById(R.id.tvgiamonangiohang);
        final TextView tvsoluonggiohang=(TextView) view.findViewById(R.id.tvsoluonggiohang);
        Button btconggiohang=(Button) view.findViewById(R.id.btconggiohang);
        Button bttrugiohang=(Button) view.findViewById(R.id.bttrugiohang);
        final ChiTietGioHang chiTietGioHang=(ChiTietGioHang) getItem(i);
        tvtenmonangiohang.setText(chiTietGioHang.getTenmonan());
        String pattern = "###,###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        tvgiamonangiohang.setText(""+decimalFormat.format(chiTietGioHang.getGia())+" Đ");
        Picasso.with(context).load(chiTietGioHang.getHinhanh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.cancel)
                .into(imghinhanhgiohang);
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
