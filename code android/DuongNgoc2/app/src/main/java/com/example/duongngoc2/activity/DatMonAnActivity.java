package com.example.duongngoc2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Sever;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatMonAnActivity extends AppCompatActivity {
    TextView tvtendat,tvgiadat,tvsoluongdat,tvtongtiendat;
    EditText edtdiadiemdat;
    Button btcongdat,bttrudat,btthuchiendat,btquayvedat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_mon_an);
        AnhXa();
        gandulieu();
        congtrusoluong();
        datmonan();
    }

    private void datmonan() {
        btthuchiendat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diadiem=edtdiadiemdat.getText().toString();
                if (diadiem.equals("")==true){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập địa chỉ giao hàng",Toast.LENGTH_LONG).show();
                }else {
                    String diadiemgiaohang=edtdiadiemdat.getText().toString();
                    DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    Date date=new Date();
                    String ngaydat=dateFormat.format(date);
                    int idtaikhoan= CheckLogin.getIdtaikhoan();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Sever.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIService apiService = retrofit.create(APIService.class);
                    Call<String> call=apiService.getThemDonHang(idtaikhoan,ngaydat,diadiemgiaohang);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua=response.body();
                            int iddonhang=Integer.parseInt(ketqua);
                            Bundle bundle =getIntent().getExtras();
                            int idmonan=bundle.getInt("id_mon_an");
                            String tenmonan=bundle.getString("ten_mon_an");
                            int gia=bundle.getInt("gia");
                            String hinhanh=bundle.getString("hinhanh_monan");
                            int soluong=Integer.parseInt(tvsoluongdat.getText().toString());
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            Call<String> call1=apiService.getThemChiTietDonHang(iddonhang,idmonan,soluong);
                            call1.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("SUCCES")==true){
                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Đặt món ăn không thành công",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

    }

    private void congtrusoluong() {
        btcongdat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slht=Integer.parseInt(tvsoluongdat.getText().toString());
                int slm=slht+1;
                tvsoluongdat.setText(String.valueOf(slm));
                gandulieu();
            }
        });
        bttrudat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slht=Integer.parseInt(tvsoluongdat.getText().toString());
                int slm;
                if (slht>1){
                    slm=slht-1;
                }else {
                    slm=1;
                }
                tvsoluongdat.setText(String.valueOf(slm));
                gandulieu();
            }
        });
    }

    private void gandulieu() {
        Bundle bundle =getIntent().getExtras();
        int idmonan=bundle.getInt("id_mon_an");
        String tenmonan=bundle.getString("ten_mon_an");
        int gia=bundle.getInt("gia");
        tvtendat.setText("Tên món ăn: "+tenmonan);
        tvgiadat.setText("Giá món ăn: "+gia+ " VND");
        int tt=gia*Integer.parseInt(tvsoluongdat.getText().toString());
        tvtongtiendat.setText("Tổng tiền: "+tt+" VND");
    }

    private void AnhXa() {
        tvtendat=findViewById(R.id.tvtendatmon);
        tvgiadat=findViewById(R.id.tvgiadatmon);
        tvsoluongdat=findViewById(R.id.tvsoluongdat);
        tvtongtiendat=findViewById(R.id.tvtongtiendat);
        edtdiadiemdat=findViewById(R.id.edtdiachidat);
        btcongdat=findViewById(R.id.btcongdat);
        bttrudat=findViewById(R.id.bttrudat);
        btthuchiendat=findViewById(R.id.btthuchiendat);
        btquayvedat=findViewById(R.id.btquayvedat);
    }
}
