package com.example.duongngoc2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.BinhLuan;
import com.example.duongngoc2.model.DonHang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BinhLuanAdapter extends BaseAdapter {
    ArrayList<BinhLuan> binhLuanArrayList;
    Context context;

    public BinhLuanAdapter(ArrayList<BinhLuan> binhLuanArrayList, Context context) {
        this.binhLuanArrayList = binhLuanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return binhLuanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return binhLuanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview_binhluan_chitiet,null);
        TextView tvhotenbinhluan,tvngaybinhluan,tvnoidungbinhluan;
        tvhotenbinhluan=view.findViewById(R.id.tvhotenbinhluan);
        tvngaybinhluan=view.findViewById(R.id.tvngaybinhluan);
        tvnoidungbinhluan=view.findViewById(R.id.tvnoidungbinhluan);
        BinhLuan binhLuan=(BinhLuan) getItem(i);
        tvhotenbinhluan.setText(binhLuan.getHoten());
        tvnoidungbinhluan.setText(binhLuan.getNoidung());
        tvngaybinhluan.setText(binhLuan.getNgaybinhluan());
        return view;
    }
}
