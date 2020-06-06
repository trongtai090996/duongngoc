package com.example.duongngoc2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.ChiTietGioHang;
import com.example.duongngoc2.model.GioHang;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Save;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietMonAn extends AppCompatActivity {
    ImageView imageViewhinhanhchitietmonan;
    TextView tvtenmonanchitiet,tvgiamonanchitiet,tvmotachitiet,tvsoluongmua,abc;
    Button btdatngay,btaddgiohang,btyeuthich;
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        Anhxa();
        laythongtinmonan();
        nutbutton();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }



    public void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MoTaFragment(), "Mô tả");
        adapter.addFragment(new BinhLuanFragment(), "Bình luận");
        viewPager.setAdapter(adapter);
    }


    private void nutbutton() {
        btdatngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckLogin.getCheck()==0){
                    Intent intent=new Intent(ChiTietMonAn.this,DangNhapActivity.class);
                    startActivity(intent);
                }else {
                    Bundle bundlenhan =getIntent().getExtras();
                    int idmonan=bundlenhan.getInt("id_mon_an");
                    String tenmonan=bundlenhan.getString("ten_mon_an");
                    int gia=bundlenhan.getInt("gia");
                    String hinhanh=bundlenhan.getString("hinhanh_monan");
                    Intent intent =new Intent(getApplicationContext(),DatMonAnActivity.class);
                    Bundle bundletruyen=new Bundle();
                    bundletruyen.putInt("id_mon_an",idmonan);
                    bundletruyen.putString("ten_mon_an",tenmonan);
                    bundletruyen.putInt("gia",gia);
                    bundletruyen.putString("hinhanh_monan",hinhanh);
                    intent.putExtras(bundletruyen);
                    startActivity(intent);
                }
            }
        });
        btaddgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckLogin.getCheck()==0){
                    Intent intent=new Intent(getApplicationContext(),DangNhapActivity.class);
                    startActivity(intent);
                }else {
                    Bundle bundle =getIntent().getExtras();
                    int idmonan=bundle.getInt("id_mon_an");
                    String tenmonan=bundle.getString("ten_mon_an");
                    int giamonan=bundle.getInt("gia");
                    String hinhanhmonan=bundle.getString("hinhanh_monan");
                    int idtaikhoan=CheckLogin.getIdtaikhoan();
                    if (MainActivity.chiTietGioHangList.size()>0){
                        Log.d("thong tin 3:",""+MainActivity.chiTietGioHangList.size());
                        boolean exists=false;
                        for (int i=0;i<MainActivity.chiTietGioHangList.size();i++){
                            if (MainActivity.chiTietGioHangList.get(i).getIdtaikhoan()==idtaikhoan && MainActivity.chiTietGioHangList.get(i).getIdmonan()==idmonan){
                                int soluongmoi=MainActivity.chiTietGioHangList.get(i).getSoluong()+1;
                                MainActivity.chiTietGioHangList.get(i).setSoluong(soluongmoi);
                                Log.d("thong tin 4:",""+soluongmoi);
                                exists=true;
                                Log.d("thong tin 2:",""+MainActivity.chiTietGioHangList.get(i).getTenmonan());
                            }
                        }
                        Log.d("thong tin 3:",""+exists);
                        if (exists==false){
                            MainActivity.chiTietGioHangList.add(new ChiTietGioHang(idtaikhoan,idmonan,tenmonan,giamonan,1,hinhanhmonan));
                        }
                    }else{
                        MainActivity.chiTietGioHangList.add(new ChiTietGioHang(idtaikhoan,idmonan,tenmonan,giamonan,1,hinhanhmonan));

                    }
                    Intent intent=new Intent(getApplicationContext(),GioHangActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    private void laythongtinmonan() {
        Bundle bundle =getIntent().getExtras();
        int idmonan=bundle.getInt("id_mon_an");
        int iddanhmuc=bundle.getInt("id_danh_muc");
        String tenmonan=bundle.getString("ten_mon_an");
        String mota=bundle.getString("mo_ta");
        int gia=bundle.getInt("gia");
        String tinhtrang=bundle.getString("tinh_trang");
        String hinhanh=bundle.getString("hinhanh_monan");
        tvtenmonanchitiet.setText(tenmonan);
        Log.e("ttmota", mota );
        String pattern = "###,###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        tvgiamonanchitiet.setText(decimalFormat.format(gia)+" đ");

        Picasso.with(getApplicationContext()).load(hinhanh)
                .placeholder(R.drawable.loading)
                .error(R.drawable.cancel)
                .into(imageViewhinhanhchitietmonan);
        Save.setIdmonan(idmonan);

    }

    private void Anhxa() {
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        imageViewhinhanhchitietmonan=(ImageView) findViewById(R.id.imghinhanhctsp);
        tvtenmonanchitiet=(TextView) findViewById(R.id.tvtenmonanctsp);
        tvgiamonanchitiet=(TextView) findViewById(R.id.tvgiactsp);
        btdatngay=(Button) findViewById(R.id.btdatngay);
        btaddgiohang=(Button) findViewById(R.id.btaddgiohang);
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
            case android.R.id.home:
                onBackPressed();
                return true;
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
