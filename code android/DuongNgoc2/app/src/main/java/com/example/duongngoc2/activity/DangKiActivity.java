package com.example.duongngoc2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangKiActivity extends AppCompatActivity {
    TextView tvthongbao,tvquaylai;
    EditText edthoten,edtsodienthoai,edtuser,edtpass,edtnhaplaipass;
    Button btdangki,btquaylai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        Anhxa();
        dangki();
        quaylai();
    }

    private void quaylai() {
        tvquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DangNhapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void dangki() {

        btdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten=edthoten.getText().toString();
                String username=edtuser.getText().toString();
                String password=edtpass.getText().toString();
                String sdt=edtsodienthoai.getText().toString();
                String nhaplaipass=edtnhaplaipass.getText().toString();
                if (hoten.equals("")==false && username.equals("")==false && password.equals("")==false && sdt.equals("")==false && nhaplaipass.equals("")==false){
                    if (edtpass.length()>5){
                        if (sdt.length()==10){
                            if (password.equals(nhaplaipass)==true){
                                int sodienthoai=Integer.parseInt(edtsodienthoai.getText().toString());
                                int level=2;
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(Sever.BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                APIService apiService = retrofit.create(APIService.class);
                                Call<String> call=apiService.getDangKi(hoten,sodienthoai,username,password,level);
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String ketqua=response.body();
                                        if (ketqua.equals("FAIL")==true){
                                            tvthongbao.setText("Tài khoản đã tồn tại");
                                        }else {
                                            tvthongbao.setText("Đăng kí thành công");
                                            Intent intent=new Intent(getApplicationContext(),DangNhapActivity.class);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.d("Lỗi : ",t.toString());
                                    }
                                });
                            }else {
                                tvthongbao.setText("Nhập lại mật khẩu không trùng với mật khẩu");
                            }
                        }else {
                            tvthongbao.setText("Số điện thoại phải đủ 10 chữ số");
                        }
                    }else {
                        tvthongbao.setText("Mật khẩu phải lơn hơn 5 ký tự");
                    }
                }else {
                    tvthongbao.setText("Vui lòng nhập dầy đủ thông tin");
                }




            }
        });
    }

    private void Anhxa() {
        tvthongbao=findViewById(R.id.tvthongbao);
        edthoten=(EditText) findViewById(R.id.edt_hoten);
        edtsodienthoai=(EditText) findViewById(R.id.edt_sdt);
        edtuser=(EditText) findViewById(R.id.edt_username);
        edtpass=(EditText) findViewById(R.id.edt_password);
        btdangki=(Button) findViewById(R.id.btn_register);
        tvquaylai= findViewById(R.id.btn_back);
        edtnhaplaipass=(EditText) findViewById(R.id.edt_passwordnhaplai);
    }
}
