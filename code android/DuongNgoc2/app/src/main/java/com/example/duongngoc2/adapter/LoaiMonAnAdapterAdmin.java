package com.example.duongngoc2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.LoaiMonAn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiMonAnAdapterAdmin extends BaseAdapter {
    Context context;
    ArrayList<LoaiMonAn> loaiMonAnArrayList;

    public LoaiMonAnAdapterAdmin(Context context, ArrayList<LoaiMonAn> loaiMonAnArrayList) {
        this.context = context;
        this.loaiMonAnArrayList = loaiMonAnArrayList;
    }

    @Override
    public int getCount() {
        return loaiMonAnArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiMonAnArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_recycleview_loaimonan,null);
        ImageView imghinhanhloaimonan=(ImageView) view.findViewById(R.id.imgviewloaimonan);
        TextView tvtenloaimonan=(TextView)view.findViewById(R.id.tvloaimonan);
        LoaiMonAn loaiMonAn= (LoaiMonAn) getItem(i);
        Picasso.with(context).load(loaiMonAn.getLinkanh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.cancel)
                .into(imghinhanhloaimonan);
        tvtenloaimonan.setText(loaiMonAn.getTenloaimonan());

        return view;
    }
}
