package com.example.duongngoc2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.DonHang;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DonHangAdapterQuanLi extends BaseAdapter {
    ArrayList<DonHang> donHangArrayListquanli;
    Context context;

    public DonHangAdapterQuanLi(ArrayList<DonHang> donHangArrayListquanli, Context context) {
        this.donHangArrayListquanli = donHangArrayListquanli;
        this.context = context;
    }

    @Override
    public int getCount() {
        return donHangArrayListquanli.size();
    }

    @Override
    public Object getItem(int i) {
        return donHangArrayListquanli.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview_donhang_quanli,null);
        TextView tvmadonhangquanli=view.findViewById(R.id.tvmadonhangquanli);
        TextView tvmataikhoanquanli=view.findViewById(R.id.tvmataikhoanquanli);
        TextView tvngatdatquanli=view.findViewById(R.id.tvngaydatquanli);
        TextView tvdiadiemquanli=view.findViewById(R.id.tvdiadiemquanli);
        TextView tvtinhtrangquanli=view.findViewById(R.id.tvtinhtrangquanli);
        TextView tvphigiaoquanli=view.findViewById(R.id.tvphigiaoquanli);
        final TextView tvtongtienquanli=view.findViewById(R.id.tvtongtienquanli);
        final DonHang donHang=(DonHang) getItem(i);
        tvmadonhangquanli.setText("Mã đơn hàng: "+donHang.getIddonhang());
        tvmataikhoanquanli.setText("Mã tài khoản: "+donHang.getIdtaikhoan());
        tvngatdatquanli.setText("Ngày đặt: "+donHang.getNgaydat());
        tvdiadiemquanli.setText("Địa điểm giao hàng: "+donHang.getDiadiemgiaohang());
        tvtinhtrangquanli.setText("Tình trạng đơn hàng: "+donHang.getTinhtrangdonhang());
        tvphigiaoquanli.setText("Phí giao hàng:" +(int)donHang.getPhiship()+" đ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<Integer> call=apiService.postTinhTongTien(donHang.getIddonhang());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int tongtien=response.body();
                tongtien=tongtien+(int)donHang.getPhiship();
                tvtongtienquanli.setText("Tổng tiền: "+tongtien+ " đ");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        return view;
    }
}
