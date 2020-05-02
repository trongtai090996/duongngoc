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
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.model.SoLuongDaBan;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonAnDatNhieuNhatAdapter extends BaseAdapter {
    ArrayList<SoLuongDaBan> soLuongDaBanArrayList;
    Context context;

    public MonAnDatNhieuNhatAdapter(ArrayList<SoLuongDaBan> soLuongDaBanArrayList, Context context) {
        this.soLuongDaBanArrayList = soLuongDaBanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return soLuongDaBanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return soLuongDaBanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview_monan_quanli,null);
        final ImageView imghinhanhmonanquanli=(ImageView) view.findViewById(R.id.imghinhanhmonanquanli);
        final TextView tvtenmonanquanli=(TextView)view.findViewById(R.id.tvtenmonanquanli);
        final TextView tvtenloaimonanquanli=(TextView)view.findViewById(R.id.tvtenloaimonanquanli);
        final TextView tvgiamonanquanli=(TextView)view.findViewById(R.id.tvgiamonanquanli);
        final TextView tvtinhtrangmonanquanli=(TextView)view.findViewById(R.id.tvtinhtrangmonanquanli);
        TextView tvsoluongmonanquanli=(TextView)view.findViewById(R.id.tvsoluongmonanquanli);
        final TextView tvmotamonanquanli=(TextView)view.findViewById(R.id.tvmotamonanquanli);
        SoLuongDaBan soLuongDaBan=(SoLuongDaBan) getItem(i);
        tvsoluongmonanquanli.setText("Số lượng đã đặt: "+soLuongDaBan.getSlm());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<MonAn>> dulieumonan=apiService.postMonAnTheoId(soLuongDaBan.getIdmonan());
        dulieumonan.enqueue(new Callback<ArrayList<MonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<MonAn>> call, Response<ArrayList<MonAn>> response) {
                ArrayList<MonAn> monAns=response.body();
                tvtenmonanquanli.setText("Tên món ăn: "+monAns.get(0).getTenmonan());
                tvgiamonanquanli.setText("Giá: "+monAns.get(0).getGia()+ " VND");
                tvtinhtrangmonanquanli.setText("Tình trạng: "+monAns.get(0).getTinhtrang());
                tvmotamonanquanli.setMaxLines(5);
                tvmotamonanquanli.setEllipsize(TextUtils.TruncateAt.END);
                tvmotamonanquanli.setText("Mô tả: "+monAns.get(0).getMota());
                Picasso.with(context).load(monAns.get(0).getHinhanhmonan())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.cancel)
                        .into(imghinhanhmonanquanli);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Sever.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                Call<String> tenloai=apiService.postTenLoaiMonAn(monAns.get(0).getIddanhmuc());
                tenloai.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String ketqua=response.body();
                        tvtenloaimonanquanli.setText("Danh mục món ăn: "+ketqua);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context,"Lỗi: "+t,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {
                Toast.makeText(context,"Lỗi: "+t,Toast.LENGTH_LONG).show();
            }
        });









        return view;
    }
}
