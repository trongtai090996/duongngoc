package com.example.duongngoc2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.activity.ChiTietMonAn;
import com.example.duongngoc2.activity.DangNhapActivity;
import com.example.duongngoc2.activity.DatMonAnActivity;
import com.example.duongngoc2.activity.GioHangActivity;
import com.example.duongngoc2.activity.MainActivity;
import com.example.duongngoc2.model.ChiTietGioHang;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.utils.CheckLogin;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DoAnTheoLoaiAdapter extends BaseAdapter {
    ArrayList<MonAn> doantheoloaiarraylist;
    Context context;

    public DoAnTheoLoaiAdapter(ArrayList<MonAn> doantheoloaiarraylist, Context context) {
        this.doantheoloaiarraylist = doantheoloaiarraylist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return doantheoloaiarraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return doantheoloaiarraylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview_monantheoloai,null);
        TextView textViewtendonanchinh=(TextView) view.findViewById(R.id.tvtendoanchinh);
        TextView textViewgiadoanchinh=(TextView) view.findViewById(R.id.tvgiadoanchinh);
        TextView textViewmotadoanchinh=(TextView) view.findViewById(R.id.tvmotadoanchinh);
        ImageView imageViewdoanchinh=(ImageView) view.findViewById(R.id.imgdoanchinh);
        /*ImageButton imgbtaddcart=view.findViewById(R.id.imgbtaddcart);
        ImageButton imgbtoderfood=view.findViewById(R.id.imgbtdatngay);*/
        final MonAn doantheoloai=(MonAn) getItem(i);
        textViewtendonanchinh.setText(doantheoloai.getTenmonan());
        String pattern = "###,###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        int vitri=i;
        textViewgiadoanchinh.setText(""+decimalFormat.format(doantheoloai.getGia())+" D");
        textViewmotadoanchinh.setMaxLines(2);
        textViewmotadoanchinh.setEllipsize(TextUtils.TruncateAt.END);
        textViewmotadoanchinh.setText(doantheoloai.getMota());
        Picasso.with(context).load(doantheoloai.getHinhanhmonan())
                .placeholder(R.drawable.loading)
                .error(R.drawable.cancel)
                .into(imageViewdoanchinh);
        /*imgbtaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckLogin.getCheck()==0){
                    Intent intent=new Intent(context, DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    int idtaikhoan=CheckLogin.getIdtaikhoan();
                    if (MainActivity.chiTietGioHangList.size()>0){
                        boolean exists=false;
                        for (int i=0;i<MainActivity.chiTietGioHangList.size();i++){
                            if (MainActivity.chiTietGioHangList.get(i).getIdtaikhoan()==idtaikhoan && MainActivity.chiTietGioHangList.get(i).getIdmonan()==doantheoloai.getIdmonan()){
                                int soluongmoi=MainActivity.chiTietGioHangList.get(i).getSoluong()+1;
                                MainActivity.chiTietGioHangList.get(i).setSoluong(soluongmoi);
                                exists=true;
                            }
                        }
                        if (exists==false){
                            MainActivity.chiTietGioHangList.add(new ChiTietGioHang(idtaikhoan,doantheoloai.getIdmonan(),doantheoloai.getTenmonan(),doantheoloai.getGia(),1,doantheoloai.getHinhanhmonan()));
                        }
                    }else{
                        MainActivity.chiTietGioHangList.add(new ChiTietGioHang(idtaikhoan,doantheoloai.getIdmonan(),doantheoloai.getTenmonan(),doantheoloai.getGia(),1,doantheoloai.getHinhanhmonan()));

                    }
                    Intent intent=new Intent(context, GioHangActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }
            }
        });*/
        /*imgbtoderfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckLogin.getCheck()==0){
                    Intent intent=new Intent(context,DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    Intent intent =new Intent(context, DatMonAnActivity.class);
                    Bundle bundletruyen=new Bundle();
                    bundletruyen.putInt("id_mon_an",doantheoloai.getIdmonan());
                    bundletruyen.putString("ten_mon_an",doantheoloai.getTenmonan());
                    bundletruyen.putInt("gia",doantheoloai.getGia());
                    bundletruyen.putString("hinhanh_monan",doantheoloai.getHinhanhmonan());
                    intent.putExtras(bundletruyen);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });*/
        return view;
    }
}
