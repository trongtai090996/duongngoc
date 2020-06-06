package com.example.duongngoc2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.ChiTietDonHangAdapter;
import com.example.duongngoc2.adapter.DonHangAdapterQuanLi;
import com.example.duongngoc2.model.ChiTietDonHang;
import com.example.duongngoc2.model.DonHang;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Sever;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NhanVienActivity extends AppCompatActivity {
    ArrayList<DonHang> donHangArrayListquanli;
    DonHangAdapterQuanLi donHangAdapterQuanLi;
    ListView lvnhanvien;
    ArrayList<ChiTietDonHang> chiTietDonHangArrayList;
    ChiTietDonHangAdapter chiTietDonHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        lvnhanvien=(ListView) findViewById(R.id.lvnhanvien);
        laydulieulistview();
        xemchitiet();
    }

    private void xemchitiet() {
        lvnhanvien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog=new Dialog(getApplicationContext());
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
                chiTietDonHangAdapter=new ChiTietDonHangAdapter(chiTietDonHangArrayList,getApplicationContext());
                lv.setAdapter(chiTietDonHangAdapter);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Sever.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                Call<ArrayList<ChiTietDonHang>> call=apiService.getChiTietDonHang(donHangArrayListquanli.get(i).getIddonhang());
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
                        Toast.makeText(getApplicationContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();
            }
        });
    }


    private void laydulieulistview() {
        donHangArrayListquanli=new ArrayList<>();
        donHangAdapterQuanLi=new DonHangAdapterQuanLi(donHangArrayListquanli,getApplicationContext());
        lvnhanvien.setAdapter(donHangAdapterQuanLi);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<DonHang>> call=apiService.DonHangNhanVien();
        call.enqueue(new Callback<ArrayList<DonHang>>() {
            @Override
            public void onResponse(Call<ArrayList<DonHang>> call, Response<ArrayList<DonHang>> response) {
                ArrayList<DonHang> donHangs=response.body();
                for (int i=0;i<donHangs.size();i++){
                    donHangArrayListquanli.add(donHangs.get(i));
                }
                donHangAdapterQuanLi.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<DonHang>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Lỗi",Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int iditem=item.getItemId();
        if (iditem==R.id.dangxuat){
            CheckLogin.setCheck(0);
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }
}
