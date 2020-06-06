package com.example.duongngoc2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.GioHangAdapter;
import com.example.duongngoc2.model.ChiTietGioHang;
import com.example.duongngoc2.model.GioHang;
import com.example.duongngoc2.model.Huyen;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.model.XaPhuong;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Sever;
import com.squareup.picasso.Picasso;

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

public class GioHangActivity extends AppCompatActivity {
    ListView lvgiohang;
    static TextView tvtongtien,tvphiship;
    Button btdathang,bttieptuc;
    public static GioHangAdapter gioHangAdapter;
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
    public static int tongtien;
    public static ArrayList<ChiTietGioHang> chiTietGioHangArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        Laydulieu();
        tinhtongtien();
        Dathang();
        quayve();
    }

    private void quayve() {
        bttieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Dathang() {
        btdathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int kiemtra=0;
                if (chiTietGioHangArrayList.size()==0){
                    Toast.makeText(getApplicationContext(),"Bạn không có món ăn nào trong giỏ hàng để đặt hàng",Toast.LENGTH_LONG).show();
                }else {
                    Dialogshow();
                }
            }
        });
    }
    public void Dialogshow(){

        final Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialogdathang);
        final EditText edtdiadiem=dialog.findViewById(R.id.edtdiadiem);
        Button btdatdoan=dialog.findViewById(R.id.btdatmon);
        Button btquayve=dialog.findViewById(R.id.btquayve);
        spinnerhuyen=dialog.findViewById(R.id.spinnerhuyengiohang);
        spinnerphuongxa=dialog.findViewById(R.id.spinnerxaphuonggiohang);
        tvphiship=dialog.findViewById(R.id.tvphigiaohanggiohang);
        radiodennha=dialog.findViewById(R.id.radiodennhagiohang);
        radionhahang=dialog.findViewById(R.id.radionhahanggiohang);
        dataspinner();
        btdatdoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Sever.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                String diadiem=edtdiadiem.getText().toString();
                String hinhthucnhan;
                String diadiemgiaohang;
                if (radiodennha.isChecked()==true){
                    hinhthucnhan=radiodennha.getText().toString();
                    diadiemgiaohang=diadiem+" "+xaphuong+" "+ huyen;
                }else {
                    hinhthucnhan=radionhahang.getText().toString();
                    diadiemgiaohang="Khách đến nhà hàng";
                }
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                Date date=new Date();
                String ngaydat=dateFormat.format(date);
                int idtaikhoan=CheckLogin.getIdtaikhoan();
                Call<String> call=apiService.getThemDonHang(idtaikhoan,ngaydat,phiship,hinhthucnhan,diadiemgiaohang);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String ketqua=response.body();
                        int iddonhang=Integer.parseInt(ketqua);
                        for (int i=0;i<chiTietGioHangArrayList.size();i++){
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            Call<String> call1=apiService.getThemChiTietDonHang(iddonhang,chiTietGioHangArrayList.get(i).getIdmonan(),
                                    chiTietGioHangArrayList.get(i).getSoluong());
                            call1.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("SUCCES")==true){
                                        Toast.makeText(getApplicationContext(),"thêm chi tiết thành công",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(),"thêm chi tiết ko thành công",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                for (int i=0;i<MainActivity.chiTietGioHangList.size();i++){
                    if (MainActivity.chiTietGioHangList.get(i).getIdtaikhoan()==CheckLogin.getIdtaikhoan()){
                        MainActivity.chiTietGioHangList.remove(i);
                        i--;
                    }
                }
                //MainActivity.chiTietGioHangList.clear();
                Toast.makeText(getApplicationContext(),"Đặt đồ ăn thành công",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }

        });
        btquayve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public void dataspinner(){
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
                                    if (xaPhuongArrayList.get(m).getTenxaphuong().equals(xaphuong)==true){
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
    public static void tinhtongtien() {
        tongtien=0;
        for (int i=0;i<chiTietGioHangArrayList.size();i++){
            final int soluong=chiTietGioHangArrayList.get(i).getSoluong();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Sever.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIService apiService = retrofit.create(APIService.class);
            Call<ArrayList<MonAn>> call=apiService.postMonAnTheoId(chiTietGioHangArrayList.get(i).getIdmonan());
            call.enqueue(new Callback<ArrayList<MonAn>>() {
                @Override
                public void onResponse(Call<ArrayList<MonAn>> call, Response<ArrayList<MonAn>> response) {
                    ArrayList<MonAn> monAns=response.body();
                    tongtien=tongtien+(monAns.get(0).getGia()*soluong);
                    String pattern = "###,###,###";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    tvtongtien.setText(""+decimalFormat.format(tongtien)+" đ");
                }

                @Override
                public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {

                }
            });

        }


    }

    public void Laydulieu() {
        chiTietGioHangArrayList=new ArrayList<>();
        gioHangAdapter=new GioHangAdapter(chiTietGioHangArrayList,getApplicationContext());
        lvgiohang.setAdapter(gioHangAdapter);
        if (MainActivity.chiTietGioHangList.size()<=0){
            Toast.makeText(getApplicationContext(),"Bạn chưa có món ăn nào",Toast.LENGTH_LONG).show();
        }else {
            for (int i=0;i<MainActivity.chiTietGioHangList.size();i++){
                if (MainActivity.chiTietGioHangList.get(i).getIdtaikhoan()==CheckLogin.getIdtaikhoan()){
                    chiTietGioHangArrayList.add(MainActivity.chiTietGioHangList.get(i));
                }
            }
            gioHangAdapter.notifyDataSetChanged();
        }

    }

    private void Anhxa() {

        lvgiohang=(ListView) findViewById(R.id.lvgiohang);
        tvtongtien=(TextView) findViewById(R.id.tvtongtien);
        btdathang=(Button) findViewById(R.id.btdathang);
        bttieptuc=(Button) findViewById(R.id.bttieptuc);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        bttieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
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
