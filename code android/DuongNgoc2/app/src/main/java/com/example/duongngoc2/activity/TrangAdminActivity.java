package com.example.duongngoc2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.utils.CheckLogin;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class TrangAdminActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView imageView;
    NavigationView navigationView;
    FrameLayout frameLayout;
    TextView tvxinchao1,tvxinchao2,tvtieudetrang;
    boolean open=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_admin2);
        AnhXa();
    }

    private void AnhXa() {
        tvxinchao1=findViewById(R.id.tvxinchao1);
        tvxinchao2=findViewById(R.id.tvxinchao2);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        imageView=(ImageView) findViewById(R.id.imgmenu);
        navigationView=findViewById(R.id.navigationview);
        frameLayout=findViewById(R.id.frame_layout);
        tvtieudetrang=findViewById(R.id.tvtieudeadmin);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (open==false) {
                    drawerLayout.openDrawer(GravityCompat.START);
                    open=false;
                }
                else {
                    drawerLayout.closeDrawers();
                    open=false;
                }
            }
        });
        navigationView.setCheckedItem(R.id.qlloaimonan);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if (id==R.id.qlloaimonan){
                    QuanLiLoaiMonAnFragment quanLiLoaiMonAnFragment=new QuanLiLoaiMonAnFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,quanLiLoaiMonAnFragment);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    tvxinchao1.setVisibility(View.INVISIBLE);
                    tvxinchao2.setVisibility(View.INVISIBLE);
                    tvtieudetrang.setText("Quản lý loại món ăn");
                }
                if (id==R.id.qlmonan){
                    QuanLiMonAnFragment quanLiMonAnFragment=new QuanLiMonAnFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,quanLiMonAnFragment);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    tvxinchao1.setVisibility(View.INVISIBLE);
                    tvxinchao2.setVisibility(View.INVISIBLE);
                    tvtieudetrang.setText("Quản lý món ăn");
                }
                if (id==R.id.qldonhang){
                    QuanLiDonHangFragment quanLiDonHangFragment=new QuanLiDonHangFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,quanLiDonHangFragment);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    tvxinchao1.setVisibility(View.INVISIBLE);
                    tvxinchao2.setVisibility(View.INVISIBLE);
                    tvtieudetrang.setText("Quản lý đơn đặt món ăn");
                }
                if (id==R.id.qltaikhoan){
                    QuanLiTaiKhoanFragment quanLiTaiKhoanFragment=new QuanLiTaiKhoanFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,quanLiTaiKhoanFragment);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    tvxinchao1.setVisibility(View.INVISIBLE);
                    tvxinchao2.setVisibility(View.INVISIBLE);
                    tvtieudetrang.setText("Quản lý tài khoản");
                }
                if (id==R.id.thongke){
                    ThongKeFragment thongKeFragment=new ThongKeFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,thongKeFragment);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    tvxinchao1.setVisibility(View.INVISIBLE);
                    tvxinchao2.setVisibility(View.INVISIBLE);
                    tvtieudetrang.setText("Thống kê");
                }
                if (id==R.id.qlbinhluan){
                    QuanLiBinhLuanFragment quanLiBinhLuanFragment=new QuanLiBinhLuanFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,quanLiBinhLuanFragment);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    tvxinchao1.setVisibility(View.INVISIBLE);
                    tvxinchao2.setVisibility(View.INVISIBLE);
                    tvtieudetrang.setText("Quản lý bình luận");
                }
                if (id==R.id.dangxuat){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    CheckLogin.setCheck(0);
                }
                return false;
            }
        });


        /*ImageView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if (id==R.id.qlloaimonan){
                    QuanLiLoaiMonAnFragment quanLiLoaiMonAnFragment=new QuanLiLoaiMonAnFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layoutadmin,quanLiLoaiMonAnFragment);
                    fragmentTransaction.commit();
                }
                if (id==R.id.qlmonan){
                    QuanLiMonAnFragment quanLiMonAnFragment=new QuanLiMonAnFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layoutadmin,quanLiMonAnFragment);
                    fragmentTransaction.commit();
                }
                if (id==R.id.qldonhang){
                    QuanLiDonHangFragment quanLiDonHangFragment=new QuanLiDonHangFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layoutadmin,quanLiDonHangFragment);
                    fragmentTransaction.commit();
                }
                if (id==R.id.qltaikhoan){
                    QuanLiTaiKhoanFragment quanLiTaiKhoanFragment=new QuanLiTaiKhoanFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layoutadmin,quanLiTaiKhoanFragment);
                    fragmentTransaction.commit();
                }
                if (id==R.id.thongke){
                    ThongKeFragment thongKeFragment=new ThongKeFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layoutadmin,thongKeFragment);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.qlloaimonan);*/

    }


}
