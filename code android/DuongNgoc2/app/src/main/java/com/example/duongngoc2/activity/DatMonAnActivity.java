package com.example.duongngoc2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.Huyen;
import com.example.duongngoc2.model.LoaiMonAn;
import com.example.duongngoc2.model.XaPhuong;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Sever;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatMonAnActivity extends AppCompatActivity {
    TextView tvtendat,tvgiadat,tvsoluongdat,tvtongtiendat,tvphiship,tvdiengiai;
    EditText edtdiadiemdat;
    Button btcongdat,bttrudat,btthuchiendat,btquayvedat;
    Spinner spinnerphuongxa,spinnerhuyen;
    RadioButton radiodennha,radionhahang;
    ArrayList<String> mangxaphuong;
    ArrayList<String> manghuyen;
    ArrayList<Huyen> huyenArrayList;
    ArrayList<XaPhuong> xaPhuongArrayList;
    String huyen="Chọn Huyện/ Thành phố";
    int idhuyen;
    String xaphuong="Chọn Xã/ Phường";
    int idxaphuong;
    float phiship;
    float ship=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_mon_an);
        AnhXa();
        gandulieu();
        dataspinnerhuyen();
        congtrusoluong();
        if (radionhahang.isChecked()==true){
            tvdiengiai.setVisibility(View.GONE);
            spinnerhuyen.setVisibility(View.GONE);
            spinnerphuongxa.setVisibility(View.GONE);
            edtdiadiemdat.setVisibility(View.GONE);
            tvphiship.setVisibility(View.GONE);
        }else{
            if (radiodennha.isChecked()==true){
                tvdiengiai.setVisibility(View.VISIBLE);
                spinnerhuyen.setVisibility(View.VISIBLE);
                spinnerphuongxa.setVisibility(View.VISIBLE);
                edtdiadiemdat.setVisibility(View.VISIBLE);
                tvphiship.setVisibility(View.VISIBLE);
            }
        }
        datmonan();
    }



    private void dataspinnerhuyen() {
        manghuyen =new ArrayList<String>();
        huyenArrayList=new ArrayList<>();
        manghuyen.add("Chọn Huyện/ Thành phố");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<Huyen>> call=apiService.AllHuyen();
        call.enqueue(new Callback<ArrayList<Huyen>>() {
            @Override
            public void onResponse(Call<ArrayList<Huyen>> call, Response<ArrayList<Huyen>> response) {
                ArrayList<Huyen> huyens=response.body();
                for (int i=0;i<huyens.size();i++){
                    manghuyen.add(huyens.get(i).getTenhuyen());
                    huyenArrayList.add(huyens.get(i));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Huyen>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Lỗi: "+t,Toast.LENGTH_LONG).show();
            }
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,manghuyen);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerhuyen.setAdapter(adapter);
        spinnerhuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                huyen=manghuyen.get(i);
                for (int j=0;j<huyenArrayList.size();j++){
                    if (huyenArrayList.get(j).getTenhuyen().equals(huyen)==true){
                        idhuyen=huyenArrayList.get(j).getIdhuyen();
                        mangxaphuong =new ArrayList<String>();
                        xaPhuongArrayList=new ArrayList<>();
                        mangxaphuong.add("Chọn Xã/ Phường");
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Sever.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIService apiService = retrofit.create(APIService.class);
                        Call<ArrayList<XaPhuong>> call=apiService.postXaPhuong(idhuyen);
                        call.enqueue(new Callback<ArrayList<XaPhuong>>() {
                            @Override
                            public void onResponse(Call<ArrayList<XaPhuong>> call, Response<ArrayList<XaPhuong>> response) {
                                ArrayList<XaPhuong> xaPhuongs=response.body();
                                for (int k=0;k<xaPhuongs.size();k++){
                                    mangxaphuong.add(xaPhuongs.get(k).getTenxaphuong());
                                    xaPhuongArrayList.add(xaPhuongs.get(k));
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<XaPhuong>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Lỗi: "+t,Toast.LENGTH_LONG).show();
                            }
                        });
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,mangxaphuong);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerphuongxa.setAdapter(adapter);
                        spinnerphuongxa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                xaphuong=mangxaphuong.get(i);
                                for (int m=0;m<xaPhuongArrayList.size();m++){
                                    if (xaPhuongArrayList.get(m).getTenxaphuong().equals(xaphuong)){
                                        idxaphuong=xaPhuongArrayList.get(m).getIdxaphuong();
                                        if (xaPhuongArrayList.get(m).getKhoangcach()<=5){
                                            phiship=0;
                                        }else {
                                            phiship=(xaPhuongArrayList.get(m).getKhoangcach()-5)*500;
                                        }
                                        tvphiship.setText("Phí giao món ăn( Chỉ tính khi giao đến nhà): "+phiship+" đ");


                                    }
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                Toast.makeText(getApplicationContext(),"Không làm gì cả",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"Không làm gì cả",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void datmonan() {
        btthuchiendat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diadiem=edtdiadiemdat.getText().toString();
                if (diadiem.equals("")==true){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập địa chỉ giao hàng",Toast.LENGTH_LONG).show();
                }else {
                    String diadiemgiaohang;
                    DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    Date date=new Date();
                    String ngaydat=dateFormat.format(date);
                    int idtaikhoan= CheckLogin.getIdtaikhoan();
                    String hinhthucnhan;
                    if (radiodennha.isChecked()==true){
                        hinhthucnhan=radiodennha.getText().toString();
                        diadiemgiaohang=edtdiadiemdat.getText().toString()+" "+xaphuong+" "+huyen;
                        ship=phiship;
                    }else {
                        hinhthucnhan=radionhahang.getText().toString();
                        diadiemgiaohang="Khách đến nhà hàng";
                        ship=0;
                    }
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Sever.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIService apiService = retrofit.create(APIService.class);
                    Call<String> call=apiService.getThemDonHang(idtaikhoan,ngaydat,ship,hinhthucnhan,diadiemgiaohang);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua=response.body();
                            int iddonhang=Integer.parseInt(ketqua);
                            Bundle bundle =getIntent().getExtras();
                            int idmonan=bundle.getInt("id_mon_an");
                            int soluong=Integer.parseInt(tvsoluongdat.getText().toString());
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            Call<String> call1=apiService.getThemChiTietDonHang(iddonhang,idmonan,soluong);
                            call1.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("SUCCES")==true){
                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Đặt món ăn không thành công",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

    }

    private void congtrusoluong() {
        btcongdat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slht=Integer.parseInt(tvsoluongdat.getText().toString());

                if (slht>=10){
                    Toast.makeText(getApplicationContext(),"Bạn không thể đặt món ăn này với số lượng lớn hơn 10",Toast.LENGTH_LONG).show();
                }else {
                    int slm=slht+1;
                    tvsoluongdat.setText(String.valueOf(slm));
                    gandulieu();
                }

            }
        });
        bttrudat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slht=Integer.parseInt(tvsoluongdat.getText().toString());
                int slm;
                if (slht>1){
                    slm=slht-1;
                }else {
                    slm=1;
                }
                tvsoluongdat.setText(String.valueOf(slm));
                gandulieu();
            }
        });
    }

    private void gandulieu() {
        Bundle bundle =getIntent().getExtras();
        int idmonan=bundle.getInt("id_mon_an");
        String tenmonan=bundle.getString("ten_mon_an");
        int gia=bundle.getInt("gia");
        tvtendat.setText("Tên món ăn: "+tenmonan);
        String pattern = "###,###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        tvgiadat.setText("Giá món ăn: "+decimalFormat.format(gia)+ " đ");
        int tt=gia*Integer.parseInt(tvsoluongdat.getText().toString());
        tvtongtiendat.setText("Thành tiền: "+tt+" đ");
    }

    private void AnhXa() {
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tvtendat=findViewById(R.id.tvtendatmon);
        tvgiadat=findViewById(R.id.tvgiadatmon);
        tvsoluongdat=findViewById(R.id.tvsoluongdat);
        tvtongtiendat=findViewById(R.id.tvtongtiendat);
        edtdiadiemdat=findViewById(R.id.edtdiachidat);
        btcongdat=findViewById(R.id.btcongdat);
        bttrudat=findViewById(R.id.bttrudat);
        btthuchiendat=findViewById(R.id.btthuchiendat);
        btquayvedat=findViewById(R.id.btquayvedat);
        spinnerphuongxa=findViewById(R.id.spinnerxaphuong);
        spinnerhuyen=findViewById(R.id.spinnerhuyen);
        radiodennha=findViewById(R.id.radiodennha);
        radionhahang=findViewById(R.id.radionhahang);
        tvphiship=findViewById(R.id.tvphigiaohang);
        tvdiengiai=(TextView)findViewById(R.id.tvdiengiai);
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

        return false;

    }
}
