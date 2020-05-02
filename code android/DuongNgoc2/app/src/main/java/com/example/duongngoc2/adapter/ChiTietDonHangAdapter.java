package com.example.duongngoc2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.ChiTietDonHang;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChiTietDonHangAdapter  extends BaseAdapter {
    ArrayList<ChiTietDonHang> chiTietDonHangArrayList;
    Context context;

    public ChiTietDonHangAdapter(ArrayList<ChiTietDonHang> chiTietDonHangArrayList, Context context) {
        this.chiTietDonHangArrayList = chiTietDonHangArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return chiTietDonHangArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return chiTietDonHangArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview_chitietdonhang,null);
        final TextView tvtenchitiet=view.findViewById(R.id.tvtenmonanchitiet);
        final TextView tvgiachitiet=view.findViewById(R.id.tvdongiachitiet);
        TextView tvsoluongchitiet=view.findViewById(R.id.tvsoluongmonanchitiet);
        final ImageView imghinhanhchitietdonhang=(ImageView) view.findViewById(R.id.imghinhanhchitiet);
        final ChiTietDonHang chiTietDonHang = (ChiTietDonHang) getItem(i);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<MonAn>> call=apiService.getAllMonantheoid(chiTietDonHang.getIdmonan());
        call.enqueue(new Callback<ArrayList<MonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<MonAn>> call, Response<ArrayList<MonAn>> response) {
                ArrayList<MonAn> monAns=response.body();
                tvtenchitiet.setText(monAns.get(0).getTenmonan());
                tvgiachitiet.setText("Đơn giá: "+monAns.get(0).getGia()+" VND");
                Picasso.with(context).load(monAns.get(0).getHinhanhmonan())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.cancel)
                        .into(imghinhanhchitietdonhang);
            }

            @Override
            public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {

            }
        });
        tvsoluongchitiet.setText("Số lượng: "+chiTietDonHang.getSoluong());
        return view;
    }
}
