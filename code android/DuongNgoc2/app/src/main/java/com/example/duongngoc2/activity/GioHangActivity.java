package com.example.duongngoc2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.GioHangAdapter;
import com.example.duongngoc2.model.ChiTietGioHang;
import com.example.duongngoc2.model.GioHang;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Sever;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GioHangActivity extends AppCompatActivity {
    ListView lvgiohang;
    static TextView tvtongtien;
    Button btdathang,bttieptuc;
    public static ArrayList<ChiTietGioHang> chiTietGioHangArrayList;
    ArrayList<GioHang> gioHangArrayList;
    public static GioHangAdapter gioHangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        Laydulieu();
        tinhtongtien();
        Dathang();
    }

    private void Dathang() {
        btdathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chiTietGioHangArrayList.size()==0){
                    Toast.makeText(getApplicationContext(),"Bạn không có món ăn nào trong giỏ hàng để đặt hàng",Toast.LENGTH_LONG).show();
                }else {
                    Dialogshow();
                }
            }
        });
    }
    public void Dialogshow(){
        final Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialogdathang);
        final EditText edtdiadiem=dialog.findViewById(R.id.edtdiadiem);
        Button btdatdoan=dialog.findViewById(R.id.btdatmon);
        Button btquayve=dialog.findViewById(R.id.btquayve);
        btdatdoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Sever.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                String diadiemgiaohang=edtdiadiem.getText().toString();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                Date date=new Date();
                String ngaydat=dateFormat.format(date);
                int idtaikhoan=CheckLogin.getIdtaikhoan();
                Call<String> call=apiService.getThemDonHang(idtaikhoan,ngaydat,diadiemgiaohang);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String ketqua=response.body();
                        int iddonhang=Integer.parseInt(ketqua);
                        for (int i=0;i<chiTietGioHangArrayList.size();i++){
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            Call<String> call1=apiService.getThemChiTietDonHang(iddonhang,chiTietGioHangArrayList.get(i).getIdmonan(),
                                    chiTietGioHangArrayList.get(i).getSoluong());
                            call1.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("SUCCES")==true){
                                        Toast.makeText(getApplicationContext(),"thêm chi tiết thành công",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(),"thêm chi tiết ko thành công",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                for (int i=0;i<MainActivity.chiTietGioHangList.size();i++){
                    if (MainActivity.chiTietGioHangList.get(i).getIdtaikhoan()==CheckLogin.getIdtaikhoan()){
                        MainActivity.chiTietGioHangList.remove(i);
                        i--;
                    }
                }
                //MainActivity.chiTietGioHangList.clear();
                Toast.makeText(getApplicationContext(),"Đặt đồ ăn thành công",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }

        });
        btquayve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public static void tinhtongtien() {
        int tongtien=0;
        for (int i=0;i<chiTietGioHangArrayList.size();i++){
            tongtien=tongtien+(chiTietGioHangArrayList.get(i).getGia()*chiTietGioHangArrayList.get(i).getSoluong());
        }
        tvtongtien.setText(""+tongtien+" VND");
    }

    public void Laydulieu() {
        chiTietGioHangArrayList=new ArrayList<>();
        gioHangAdapter=new GioHangAdapter(chiTietGioHangArrayList,getApplicationContext());
        lvgiohang.setAdapter(gioHangAdapter);
        if (MainActivity.chiTietGioHangList.size()<=0){
            Toast.makeText(getApplicationContext(),"Bạn chưa có món ăn nào",Toast.LENGTH_LONG).show();
        }else {
            for (int i=0;i<MainActivity.chiTietGioHangList.size();i++){
                if (MainActivity.chiTietGioHangList.get(i).getIdtaikhoan()==CheckLogin.getIdtaikhoan()){
                    chiTietGioHangArrayList.add(MainActivity.chiTietGioHangList.get(i));
                }
            }
            gioHangAdapter.notifyDataSetChanged();
        }

    }

    private void Anhxa() {
        lvgiohang=(ListView) findViewById(R.id.lvgiohang);
        tvtongtien=(TextView) findViewById(R.id.tvtongtien);
        btdathang=(Button) findViewById(R.id.btdathang);
        bttieptuc=(Button) findViewById(R.id.bttieptuc);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);

    }
}
