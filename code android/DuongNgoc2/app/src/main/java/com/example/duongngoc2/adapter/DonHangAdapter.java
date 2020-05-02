package com.example.duongngoc2.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.activity.InforFragment;
import com.example.duongngoc2.model.ChiTietDonHang;
import com.example.duongngoc2.model.DonHang;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DonHangAdapter extends BaseAdapter {
    ArrayList<DonHang> donHangArrayList;
    Context context;
    ArrayList<ChiTietDonHang> chiTietDonHangArrayList;
    ChiTietDonHangAdapter chiTietDonHangAdapter;

    public DonHangAdapter(ArrayList<DonHang> donHangArrayList, Context context) {
        this.donHangArrayList = donHangArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return donHangArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return donHangArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview_donhang,null);
        TextView tvmadonhang=(TextView) view.findViewById(R.id.tvmadonhang);
        TextView tvngaydatdonhang=(TextView) view.findViewById(R.id.tvngaydatdonhang);
        TextView tvdiadiemdonhang=(TextView) view.findViewById(R.id.tvdiadiemdonhang);
        TextView tvtinhtrangdonhang=(TextView) view.findViewById(R.id.tvtinhtrangdonhang);
        final TextView tvtongtien=(TextView) view.findViewById(R.id.tvtongtiendonhang);
        Button btxemchitiet=(Button) view.findViewById(R.id.btxemchitiet);
        Button bthuydonhang=(Button) view.findViewById(R.id.bthuydonhang);

        final DonHang donHang=(DonHang) getItem(i);
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
                tvtongtien.setText(tongtien+ " VND");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        tvmadonhang.setText(String.valueOf(donHang.getIddonhang()));
        tvngaydatdonhang.setText(donHang.getNgaydat());
        tvdiadiemdonhang.setText(donHang.getDiadiemgiaohang());
        tvtinhtrangdonhang.setText(donHang.getTinhtrangdonhang());
        tvdiadiemdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"bấm",Toast.LENGTH_LONG).show();
            }
        });
        bthuydonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (donHang.getTinhtrangdonhang().equals("Chờ xử lý")==false){
                    Toast.makeText(context,"Không thể hủy đơn hàng trong tình trạng đang giao hàng hoặc đã giao hàng",Toast.LENGTH_LONG).show();
                }else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Sever.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIService apiService = retrofit.create(APIService.class);
                    Call<String> call=apiService.getHuyDonHang(donHang.getIddonhang());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua=response.body();
                            if (ketqua.equals("DELETESUCCES")){
                                Toast.makeText(context,"Đã hủy đơn hàng thành công",Toast.LENGTH_LONG).show();
                                for (int i=0;i<donHangArrayList.size();i++){
                                    if (donHangArrayList.get(i).getIddonhang()==donHang.getIddonhang()){
                                        donHangArrayList.remove(i);
                                    }
                                }
                                InforFragment.donHangAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }

            }
        });

        btxemchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.dialog_chitietdonhang);
                ListView lv;
                Button btok=dialog.findViewById(R.id.btok);
                lv=dialog.findViewById(R.id.lvtest);
                btok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                chiTietDonHangArrayList =new ArrayList<>();
                chiTietDonHangAdapter=new ChiTietDonHangAdapter(chiTietDonHangArrayList,context);
                lv.setAdapter(chiTietDonHangAdapter);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Sever.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                Call<ArrayList<ChiTietDonHang>> call=apiService.getChiTietDonHang(donHang.getIddonhang());
                call.enqueue(new Callback<ArrayList<ChiTietDonHang>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ChiTietDonHang>> call, Response<ArrayList<ChiTietDonHang>> response) {
                        ArrayList<ChiTietDonHang> chiTietDonHangs=response.body();
                        for (int i=0;i<chiTietDonHangs.size();i++){
                            chiTietDonHangArrayList.add(chiTietDonHangs.get(i));
                        }
                        chiTietDonHangAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ChiTietDonHang>> call, Throwable t) {
                        Toast.makeText(context,"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();
            }
        });
        return view;
    }
}
