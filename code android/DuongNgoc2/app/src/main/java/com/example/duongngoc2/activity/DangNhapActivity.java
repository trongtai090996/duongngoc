package com.example.duongngoc2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.LoaiMonAn;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.model.TaiKhoan;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Sever;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangNhapActivity extends AppCompatActivity {
    EditText editTextuser,editTextpass;
    Button btlogin;
    ImageView imageView;
    TextView tvdangki;
    ArrayList<TaiKhoan> taiKhoanArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        Anhxa();
        buttondangki();
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taiKhoanArrayList=new ArrayList<>();
                String username=editTextuser.getText().toString();
                String password=editTextpass.getText().toString();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Sever.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                Call<ArrayList<TaiKhoan>> call = apiService.getAllTaiKhoan(username,password);
                call.enqueue(new Callback<ArrayList<TaiKhoan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TaiKhoan>> call, retrofit2.Response<ArrayList<TaiKhoan>> response) {
                        taiKhoanArrayList=response.body();
                        if (taiKhoanArrayList.size()==0){
                            Toast.makeText(getApplicationContext(),"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_LONG).show();
                        }else {
                            CheckLogin.setCheck(1);
                            CheckLogin.setIdtaikhoan(taiKhoanArrayList.get(0).getIdtaikhoan());
                            CheckLogin.setHoten(taiKhoanArrayList.get(0).getHoten());
                            CheckLogin.setSodienthoai(taiKhoanArrayList.get(0).getSodienthoai());
                            CheckLogin.setUsername(taiKhoanArrayList.get(0).getUsername());
                            CheckLogin.setPassword(taiKhoanArrayList.get(0).getPassword());
                            CheckLogin.setLevel(taiKhoanArrayList.get(0).getLevel());
                            if (taiKhoanArrayList.get(0).getLevel()==1){
                                Intent intent =new Intent(getApplicationContext(),TrangAdminActivity.class);
                                startActivity(intent);
                            }else {
                                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }


                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"ko the lay du lieu"+t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void buttondangki() {
        tvdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DangKiActivity.class);
                startActivity(intent);
            }
        });
    }



    private void Anhxa() {
        editTextuser=(EditText) findViewById(R.id.edt_username);
        editTextpass=(EditText) findViewById(R.id.edt_password);
        btlogin=(Button)findViewById(R.id.btn_login);
        tvdangki=(TextView) findViewById(R.id.btn_show_register);
        imageView=findViewById(R.id.imganhtet);
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
