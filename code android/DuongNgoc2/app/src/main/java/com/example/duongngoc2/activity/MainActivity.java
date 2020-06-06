package com.example.duongngoc2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.ChiTietGioHang;
import com.example.duongngoc2.model.GioHang;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Sever;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<ChiTietGioHang> chiTietGioHangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if (chiTietGioHangList==null){
            chiTietGioHangList=new ArrayList<>();
        }else {
            for (int i=0;i<chiTietGioHangList.size();i++){
                final int vitri=i;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Sever.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                Call<ArrayList<MonAn>> call=apiService.postMonAnTheoId(chiTietGioHangList.get(i).getIdmonan());
                call.enqueue(new Callback<ArrayList<MonAn>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MonAn>> call, Response<ArrayList<MonAn>> response) {
                        ArrayList<MonAn> monAns=response.body();
                        chiTietGioHangList.get(vitri).setTenmonan(monAns.get(0).getTenmonan());
                        chiTietGioHangList.get(vitri).setGia(monAns.get(0).getGia());
                        chiTietGioHangList.get(vitri).setHinhanh(monAns.get(0).getHinhanhmonan());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {

                    }
                });
                i++;
            }

        };
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id= menuItem.getItemId();
                if (id==R.id.trangchu){
                    TrangChuFragment trangChuFragment=new TrangChuFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,trangChuFragment);
                    fragmentTransaction.commit();
                }
                if (id==R.id.food){
                    FoodFragment foodFragment=new FoodFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,foodFragment);
                    fragmentTransaction.commit();
                }
                if (id==R.id.infor){
                    if (CheckLogin.getCheck()==0){
                        Toast.makeText(getApplicationContext(),"Vui lòng đăng nhập để xem các đơn đặt đồ ăn",Toast.LENGTH_LONG).show();
                        Intent intent =new Intent(getApplicationContext(),DangNhapActivity.class);
                        startActivity(intent);
                    }else {
                        InforFragment inforFragment=new InforFragment();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout,inforFragment);
                        fragmentTransaction.commit();
                    }


                }
                if (id==R.id.account){
                    if (CheckLogin.getCheck()==0){
                        Intent intent =new Intent(getApplicationContext(),DangNhapActivity.class);
                        startActivity(intent);
                    }else {
                        AccountFragment accountFragment=new AccountFragment();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout,accountFragment);
                        fragmentTransaction.commit();
                    }

                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.trangchu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int iditem=item.getItemId();
        switch (iditem)
        {
            case  R.id.giohangmenu:
                if (CheckLogin.getCheck()==0){
                    Intent intent=new Intent(getApplicationContext(),DangNhapActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(getApplicationContext(),GioHangActivity.class);
                    startActivity(intent);
                }
            case R.id.timkiemmenu:
                Intent intent=new Intent(getApplicationContext(),TimKiemActivity.class);
                startActivity(intent);

            default:break;
        }

        return super.onOptionsItemSelected(item);

    }
}
