package com.example.duongngoc2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.AllBinhLuan;
import com.example.duongngoc2.model.BinhLuan;

import java.util.ArrayList;

public class QuanLiBinhLuanAdapter extends BaseAdapter {
    ArrayList<AllBinhLuan> allBinhLuanArrayList;
    Context context;

    public QuanLiBinhLuanAdapter(ArrayList<AllBinhLuan> allBinhLuanArrayList, Context context) {
        this.allBinhLuanArrayList = allBinhLuanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return allBinhLuanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return allBinhLuanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview_quanli_binhluan,null);
        TextView tvhotenqlbl,tvtenmonanqlbl,tvnoidungqlbl,tvngayqlbl;
        tvtenmonanqlbl=view.findViewById(R.id.tvtenmonanqlbinhluan);
        tvnoidungqlbl=view.findViewById(R.id.tvnoidungqlbinhluan);
        AllBinhLuan allBinhLuan =(AllBinhLuan) getItem(i);
        tvtenmonanqlbl.setText("Món ăn "+allBinhLuan.getTenmonan());
        tvnoidungqlbl.setText("Nội dung: "+allBinhLuan.getNoidung());
        return view;
    }
}