package com.example.duongngoc2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.DoAnTheoLoaiAdapter;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Sever;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimKiemActivity extends AppCompatActivity {
    EditText edttukhoa;
    Button bttimkiem;
    ArrayList<MonAn> monAnArrayListtimkiem;
    DoAnTheoLoaiAdapter doAnTheoLoaiAdaptertimkiem;
    ListView lvtimkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        AnhXa();
        bamtimkiem();
        bamvaolistview();
    }

    private void bamvaolistview() {
            lvtimkiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent =new Intent(getApplicationContext(),ChiTietMonAn.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("id_mon_an",monAnArrayListtimkiem.get(i).getIdmonan());
                    bundle.putInt("id_danh_muc",monAnArrayListtimkiem.get(i).getIddanhmuc());
                    bundle.putString("ten_mon_an",monAnArrayListtimkiem.get(i).getTenmonan());
                    bundle.putString("mo_ta",monAnArrayListtimkiem.get(i).getMota());
                    bundle.putInt("gia",monAnArrayListtimkiem.get(i).getGia());
                    bundle.putString("tinh_trang",monAnArrayListtimkiem.get(i).getMota());
                    bundle.putInt("so_luong",monAnArrayListtimkiem.get(i).getSoluong());
                    bundle.putString("hinhanh_monan",monAnArrayListtimkiem.get(i).getHinhanhmonan());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

    }

    private void bamtimkiem() {
        bttimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edttukhoa.getText().toString().equals("")==false){
                    xulytimkiem();
                }else {
                    Toast.makeText(getApplicationContext(),"Hãy nhập từ khóa cần tìm kiếm",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void xulytimkiem() {
        monAnArrayListtimkiem=new ArrayList<>();
        doAnTheoLoaiAdaptertimkiem=new DoAnTheoLoaiAdapter(monAnArrayListtimkiem,getApplicationContext());
        lvtimkiem.setAdapter(doAnTheoLoaiAdaptertimkiem);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<MonAn>> call=apiService.postTimKiem(edttukhoa.getText().toString());
        call.enqueue(new Callback<ArrayList<MonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<MonAn>> call, Response<ArrayList<MonAn>> response) {
                ArrayList<MonAn> monAns=response.body();
                for (int i=0;i<monAns.size();i++){
                    monAnArrayListtimkiem.add(monAns.get(i));
                }
                doAnTheoLoaiAdaptertimkiem.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {

            }
        });

    }


    private void AnhXa() {
        edttukhoa=findViewById(R.id.edttukhoa);
        bttimkiem=findViewById(R.id.bttimkiem);
        lvtimkiem=findViewById(R.id.lvtimkiem);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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

            default:break;
        }

        return super.onOptionsItemSelected(item);

    }
}
