package com.example.duongngoc2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class SuaMonAnActivity extends AppCompatActivity {
    EditText edtsuatenmonanquanli,edtsuamotamonanquanli,edtsuagiamonanquanli,edtsualinkanhmonanquanli;
    Button btsuamonan,bthuysuamonan;
    RadioButton radionoibatsua,radiobinhthuongsua,radiodoi,radiokhongdoi;
    RadioGroup radioGroupdoitinhtrang,radioGroupdoidanhmuc;
    TextView tvdanhmucgoc;
    Spinner spinnersuamonan;
    ArrayList<String> mangten;
    ArrayList<LoaiMonAn> loaiMonAnArrayList;
    String goc="Chọn danh mục mới";
    int iddanhmucmoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_mon_an);
        Anhxa();
        Bundle bundle =getIntent().getExtras();
        final int idmonan=bundle.getInt("id_mon_an");
        final int iddanhmuc=bundle.getInt("id_danh_muc");
        String tenmonan=bundle.getString("ten_mon_an");
        String mota=bundle.getString("mo_ta");
        int gia=bundle.getInt("gia");
        String tinhtrang=bundle.getString("tinh_trang");
        String hinhanh=bundle.getString("hinhanh_monan");
        edtsuagiamonanquanli.setText(""+gia);
        edtsualinkanhmonanquanli.setText(""+hinhanh);
        edtsuamotamonanquanli.setText(mota);
        edtsuatenmonanquanli.setText(tenmonan);

        if (tinhtrang.equals("nổi bật")==true){
            radionoibatsua.setChecked(true);
        }else {
            radiobinhthuongsua.setChecked(true);
        }
        Log.d("id d",""+iddanhmuc);
        iddanhmucmoi=iddanhmuc;
        mangten =new ArrayList<String>();
        loaiMonAnArrayList=new ArrayList<>();
        mangten.add("Chọn danh mục mới");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        final Call<ArrayList<LoaiMonAn>> call=apiService.getAllLoaimonan();
        call.enqueue(new Callback<ArrayList<LoaiMonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<LoaiMonAn>> call, Response<ArrayList<LoaiMonAn>> response) {
                ArrayList<LoaiMonAn> loaiMonAns=response.body();
                for (int i=0;i<loaiMonAns.size();i++){
                    mangten.add(loaiMonAns.get(i).getTenloaimonan());
                    loaiMonAnArrayList.add(loaiMonAns.get(i));
                    if (loaiMonAns.get(i).getIddanhmuc()==iddanhmuc){
                        tvdanhmucgoc.setText("Tên danh mục: "+loaiMonAns.get(i).getTenloaimonan());
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<LoaiMonAn>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Lỗi: "+t,Toast.LENGTH_LONG).show();
            }
        });

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,mangten);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersuamonan.setAdapter(adapter);
        spinnersuamonan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j=0;j<loaiMonAnArrayList.size();j++){
                    if (loaiMonAnArrayList.get(j).getTenloaimonan().equals(mangten.get(i))==true){
                        iddanhmucmoi=loaiMonAnArrayList.get(j).getIddanhmuc();
                    }
                }
                String tenloaimonanmoi=mangten.get(i);
                goc =tenloaimonanmoi;
                Log.d("id danh muc moi",""+iddanhmucmoi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                iddanhmucmoi=iddanhmuc;
            }
        });



        btsuamonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenmoi=edtsuatenmonanquanli.getText().toString();
                String giamoi=edtsuagiamonanquanli.getText().toString();
                String anhmoi=edtsualinkanhmonanquanli.getText().toString();
                String motamoi=edtsuamotamonanquanli.getText().toString();
                String tinhtrangmoi;
                int iddanhmucnew;
                if (radiobinhthuongsua.isChecked()==true){
                    tinhtrangmoi=radiobinhthuongsua.getText().toString();
                }else {
                    tinhtrangmoi=radionoibatsua.getText().toString();
                }
                if (radiokhongdoi.isChecked()==true){
                    iddanhmucnew=iddanhmuc;
                }else {
                    iddanhmucnew=iddanhmucmoi;
                }
                if (tenmoi.equals("")==false && giamoi.equals("")==false && anhmoi.equals("")==false &&  motamoi.equals("")==false){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Sever.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIService apiService = retrofit.create(APIService.class);
                    int gianew=Integer.parseInt(giamoi);
                    Call<String> call1=apiService.postSuaMonAn(idmonan,iddanhmucnew,tenmoi,tinhtrangmoi,anhmoi,motamoi,gianew);
                    call1.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua=response.body();
                            if (ketqua.equals("SUCCES")==true){
                                Toast.makeText(getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),"Sửa không thành công",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Lôi",Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(),"Khồng được để trống",Toast.LENGTH_LONG).show();
                }
            }
        });
        bthuysuamonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TrangAdminActivity.class);
                startActivity(intent);
            }
        });

    }


    private void Anhxa() {
        edtsuatenmonanquanli=findViewById(R.id.edtsuatenmonanquanli);
        edtsuagiamonanquanli=findViewById(R.id.edtsuagiamonanquanli);
        edtsualinkanhmonanquanli=findViewById(R.id.edtsualinkanhmonanquanli);
        edtsuamotamonanquanli=findViewById(R.id.edtsuamotamonanquanli);
        tvdanhmucgoc=findViewById(R.id.tvdanhmucgoc);
        spinnersuamonan=findViewById(R.id.spinnersuamonan);
        radioGroupdoidanhmuc=findViewById(R.id.radiogroupdoidanhmuc);
        radioGroupdoitinhtrang=findViewById(R.id.radiogroupdoitinhtrang);
        radiodoi=findViewById(R.id.radiodoi);
        radiokhongdoi=findViewById(R.id.radiokodoi);
        radionoibatsua=findViewById(R.id.radionoibatsua);
        radiobinhthuongsua=findViewById(R.id.radiobinhthuongsua);
        btsuamonan=findViewById(R.id.btsuamonan);
        bthuysuamonan=findViewById(R.id.bthuysuamonan);
    }
}
