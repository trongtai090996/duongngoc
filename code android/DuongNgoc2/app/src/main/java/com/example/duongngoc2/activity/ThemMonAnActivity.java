package com.example.duongngoc2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.LoaiMonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemMonAnActivity extends AppCompatActivity {
    EditText edttenmonanthemquanli,edtmotathemquanli,edtgiamonanthemquanli,edtlinkanhmonanthemquanli;
    Button btthemmonan,bthuythemmonan;
    RadioButton radionoibat,radiobinhthuong;
    RadioGroup radioGrouptinhtrang;
    Spinner spinnerthemmonan;
    ArrayList<String> mangten;
    ArrayList<LoaiMonAn> loaiMonAnArrayList;
    String c ="Chọn loại món ăn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_an);
        Anhxa();
        Dulieuspinner();
        themmonan();
        huythem();
    }

    private void huythem() {
        bthuythemmonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TrangAdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void themmonan() {
        btthemmonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenmonan=edttenmonanthemquanli.getText().toString();
                String tinhtrang;
                if (radionoibat.isChecked()==true){
                    tinhtrang=radionoibat.getText().toString();
                }else {
                    tinhtrang=radiobinhthuong.getText().toString();
                }
                String mota=edtmotathemquanli.getText().toString();
                String linkanh=edtlinkanhmonanthemquanli.getText().toString();
                String a=edtgiamonanthemquanli.getText().toString();

                int iddanhmuc=0;
                for (int i=0;i<loaiMonAnArrayList.size();i++){
                    if (loaiMonAnArrayList.get(i).getTenloaimonan().equals(c)==true){
                        iddanhmuc=loaiMonAnArrayList.get(i).getIddanhmuc();
                    }
                }
                if (tenmonan.equals("")==false && mota.equals("")==false && linkanh.equals("")==false && a.equals("")==false){
                    if (c.equals("Chọn loại món ăn")==false){
                        int gia=Integer.parseInt(a);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Sever.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIService apiService = retrofit.create(APIService.class);
                        Call<String> call1=apiService.postThemMonAn(iddanhmuc,tenmonan,gia,tinhtrang,linkanh,mota);
                        call1.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua=response.body();
                                if (ketqua.equals("TRUNG")==true){
                                    Toast.makeText(getApplicationContext(),"Món ăn đã tồn tại",Toast.LENGTH_LONG).show();
                                }else {
                                    if (ketqua.equals("SUCCES")==true){
                                        Toast.makeText(getApplicationContext(),"Thêm món ăn thành công",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Thêm món ăn không thành công",Toast.LENGTH_LONG).show();
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(),"Hãy chọn tên loại món ăn",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Không được để trống",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Dulieuspinner() {
        mangten =new ArrayList<String>();
        loaiMonAnArrayList=new ArrayList<>();
        mangten.add("Chọn loại món ăn");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<LoaiMonAn>> call=apiService.getAllLoaimonan();
        call.enqueue(new Callback<ArrayList<LoaiMonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<LoaiMonAn>> call, Response<ArrayList<LoaiMonAn>> response) {
                ArrayList<LoaiMonAn> loaiMonAns=response.body();
                for (int i=0;i<loaiMonAns.size();i++){
                    mangten.add(loaiMonAns.get(i).getTenloaimonan());
                    loaiMonAnArrayList.add(loaiMonAns.get(i));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LoaiMonAn>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Lỗi: "+t,Toast.LENGTH_LONG).show();
            }
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,mangten);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerthemmonan.setAdapter(adapter);
        spinnerthemmonan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tenloaimonan=mangten.get(i);
                c =tenloaimonan;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"Không làm gì cả",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Anhxa() {
        edttenmonanthemquanli=findViewById(R.id.edttenmonanthemquanli);
        edtgiamonanthemquanli=findViewById(R.id.edtgiamonanthemquanli);
        edtlinkanhmonanthemquanli=findViewById(R.id.edthinhanhmonanthemquanli);
        edtmotathemquanli=findViewById(R.id.edtmotamonanthemquanli);
        radioGrouptinhtrang=findViewById(R.id.radiogrouptinhtrang);
        radionoibat=findViewById(R.id.radionoibat);
        radiobinhthuong=findViewById(R.id.radiobinhthuong);
        spinnerthemmonan=findViewById(R.id.spinnerthemmonan);
        btthemmonan=findViewById(R.id.btthemmonan);
        bthuythemmonan=findViewById(R.id.bthuythemmonan);
    }
}
