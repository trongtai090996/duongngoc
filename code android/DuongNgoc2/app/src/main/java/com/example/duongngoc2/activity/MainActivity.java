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
import com.example.duongngoc2.utils.CheckLogin;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<ChiTietGioHang> chiTietGioHangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();


        if (chiTietGioHangList==null){
            chiTietGioHangList=new ArrayList<>();
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
        switch (item.getItemId())
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
