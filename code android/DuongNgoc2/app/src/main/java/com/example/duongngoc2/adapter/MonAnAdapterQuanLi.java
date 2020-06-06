package com.example.duongngoc2.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.LoaiMonAn;
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

public class MonAnAdapterQuanLi  extends BaseAdapter {
    ArrayList<MonAn> monAnArrayList;
    Context context;

    public MonAnAdapterQuanLi(ArrayList<MonAn> monAnArrayList, Context context) {
        this.monAnArrayList = monAnArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return monAnArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return monAnArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview_monan_quanli,null);
        ImageView imghinhanhmonanquanli=(ImageView) view.findViewById(R.id.imghinhanhmonanquanli);
        TextView tvtenmonanquanli=(TextView)view.findViewById(R.id.tvtenmonanquanli);
        final TextView tvtenloaimonanquanli=(TextView)view.findViewById(R.id.tvtenloaimonanquanli);
        TextView tvgiamonanquanli=(TextView)view.findViewById(R.id.tvgiamonanquanli);
        TextView tvtinhtrangmonanquanli=(TextView)view.findViewById(R.id.tvtinhtrangmonanquanli);
        TextView tvmotamonanquanli=(TextView)view.findViewById(R.id.tvmotamonanquanli);
        MonAn monAn= (MonAn) getItem(i);
        Picasso.with(context).load(monAn.getHinhanhmonan())
                .placeholder(R.drawable.loading)
                .error(R.drawable.cancel)
                .into(imghinhanhmonanquanli);
        tvtenmonanquanli.setText("Tên món ăn: "+monAn.getTenmonan());
        tvgiamonanquanli.setText("Giá: "+monAn.getGia()+ " VND");
        tvtinhtrangmonanquanli.setText("Tình trạng: "+monAn.getTinhtrang());
        tvmotamonanquanli.setMaxLines(3);
        tvmotamonanquanli.setEllipsize(TextUtils.TruncateAt.END);
        tvmotamonanquanli.setText("Mô tả: "+monAn.getMota());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<String> call=apiService.postTenLoaiMonAn(monAn.getIddanhmuc());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ketqua=response.body();
                tvtenloaimonanquanli.setText("Loại món ăn: "+ketqua);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context,"Lỗi: "+t,Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
