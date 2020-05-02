package com.example.duongngoc2.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.MonAnAdapterQuanLi;
import com.example.duongngoc2.model.LoaiMonAn;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuanLiMonAnFragment extends Fragment {
    ArrayList<MonAn> monAnArrayListquanli;
    MonAnAdapterQuanLi monAnAdapterQuanLi;
    ListView lvmonanquanli;
    TextView tvthemmonan;
    EditText edttenmonanthemquanli,edtmotathemquanli,edtgiamonanthemquanli,edtsoluongmonanthemquanli,edtlinkanhmonanthemquanli;
    Button btthemmonan,bthuythemmonan;
    RadioButton radionoibat,radiobinhthuong;
    RadioGroup radioGrouptinhtrang;
    Spinner spinnerthemmonan;
    ArrayList<String> mangten;
    ArrayList<LoaiMonAn> loaiMonAnArrayList;
    String c ="Chọn loại món ăn";

    public QuanLiMonAnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_li_mon_an, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvthemmonan=view.findViewById(R.id.tvthemmonan);
        tvthemmonan.setPaintFlags(tvthemmonan.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        lvmonanquanli=view.findViewById(R.id.lvmonanquanli);
        laydulieulistview();
        themmonan();
        xoamonan();
        suamonan();

    }

    private void suamonan() {
        lvmonanquanli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getContext(),SuaMonAnActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id_mon_an",monAnArrayListquanli.get(i).getIdmonan());
                bundle.putInt("id_danh_muc",monAnArrayListquanli.get(i).getIddanhmuc());
                bundle.putString("ten_mon_an",monAnArrayListquanli.get(i).getTenmonan());
                bundle.putString("mo_ta",monAnArrayListquanli.get(i).getMota());
                bundle.putInt("gia",monAnArrayListquanli.get(i).getGia());
                bundle.putString("tinh_trang",monAnArrayListquanli.get(i).getMota());
                bundle.putInt("so_luong",monAnArrayListquanli.get(i).getSoluong());
                bundle.putString("hinhanh_monan",monAnArrayListquanli.get(i).getHinhanhmonan());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void Anhxasua(Dialog dialog,int i) {


    }

    private void xoamonan() {
        lvmonanquanli.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int idmonan=monAnArrayListquanli.get(i).getIdmonan();
                final AlertDialog.Builder alertDialog =new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Thông báo");
                alertDialog.setMessage("Bạn có muốn xóa loại món ăn: "+monAnArrayListquanli.get(i).getTenmonan()+" không?");
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Sever.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIService apiService = retrofit.create(APIService.class);
                        Call<String> call=apiService.postXoaMonAn(idmonan);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua=response.body();
                                if (ketqua.equals("SUCCES")==true){
                                    Toast.makeText(getContext(),"Xóa thành công",Toast.LENGTH_LONG).show();
                                    monAnAdapterQuanLi.notifyDataSetChanged();
                                    laydulieulistview();
                                }else {
                                    Toast.makeText(getContext(),"Xóa không thành công",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(getContext(),"Lỗi "+t,Toast.LENGTH_LONG).show();
                            }
                        });
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
                return false;
            }
        });
    }

    private void themmonan() {
        tvthemmonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog= new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_themmonan);
                dialog.show();
                Anhxa(dialog);
                mangten =new ArrayList<String>();
                loaiMonAnArrayList=new ArrayList<>();
                mangten.add("Chọn loại món ăn");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Sever.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                Call<ArrayList<LoaiMonAn>> call=apiService.getAllLoaimonan();
                call.enqueue(new Callback<ArrayList<LoaiMonAn>>() {
                    @Override
                    public void onResponse(Call<ArrayList<LoaiMonAn>> call, Response<ArrayList<LoaiMonAn>> response) {
                        ArrayList<LoaiMonAn> loaiMonAns=response.body();
                        for (int i=0;i<loaiMonAns.size();i++){
                            mangten.add(loaiMonAns.get(i).getTenloaimonan());
                            loaiMonAnArrayList.add(loaiMonAns.get(i));
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<LoaiMonAn>> call, Throwable t) {
                        Toast.makeText(getContext(),"Lỗi: "+t,Toast.LENGTH_LONG).show();
                    }
                });
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,mangten);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerthemmonan.setAdapter(adapter);
                spinnerthemmonan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String tenloaimonan=mangten.get(i);
                        c =tenloaimonan;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Toast.makeText(getContext(),"Không làm gì cả",Toast.LENGTH_LONG).show();
                    }
                });
                huydialog(dialog);
                btthemmonan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tenmonan=edttenmonanthemquanli.getText().toString();
                        String tinhtrang;
                        if (radionoibat.isChecked()==true){
                            tinhtrang=radionoibat.getText().toString();
                        }else {
                            tinhtrang=radiobinhthuong.getText().toString();
                        }
                        String mota=edtmotathemquanli.getText().toString();
                        String linkanh=edtlinkanhmonanthemquanli.getText().toString();
                        String a=edtgiamonanthemquanli.getText().toString();
                        String b=edtsoluongmonanthemquanli.getText().toString();

                        int iddanhmuc=0;
                        for (int i=0;i<loaiMonAnArrayList.size();i++){
                            if (loaiMonAnArrayList.get(i).getTenloaimonan().equals(c)==true){
                                iddanhmuc=loaiMonAnArrayList.get(i).getIddanhmuc();
                            }
                        }
                        if (tenmonan.equals("")==false && mota.equals("")==false && linkanh.equals("")==false && a.equals("")==false && b.equals("")==false){
                            if (c.equals("Chọn loại món ăn")==false){
                                int gia=Integer.parseInt(a);
                                int soluong=Integer.parseInt(b);
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(Sever.BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                APIService apiService = retrofit.create(APIService.class);
                                Call<String> call1=apiService.postThemMonAn(iddanhmuc,tenmonan,gia,tinhtrang,soluong,linkanh,mota);
                                call1.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String ketqua=response.body();
                                        if (ketqua.equals("SUCCES")){
                                            Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                                            monAnAdapterQuanLi.notifyDataSetChanged();
                                            laydulieulistview();
                                            dialog.dismiss();
                                        }else {
                                            Toast.makeText(getContext(),"Thêm không thành công",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            }else {
                                Toast.makeText(getContext(),"Hãy chọn tên loại món ăn",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getContext(),"Không được để trống",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    private void huydialog(final Dialog dialog) {
        bthuythemmonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void Anhxa(Dialog dialog) {
        edttenmonanthemquanli=dialog.findViewById(R.id.edttenmonanthemquanli);
        edtgiamonanthemquanli=dialog.findViewById(R.id.edtgiamonanthemquanli);
        edtlinkanhmonanthemquanli=dialog.findViewById(R.id.edthinhanhmonanthemquanli);
        edtmotathemquanli=dialog.findViewById(R.id.edtmotamonanthemquanli);
        edtsoluongmonanthemquanli=dialog.findViewById(R.id.edtsoluongmonanthemquanli);
        radioGrouptinhtrang=dialog.findViewById(R.id.radiogrouptinhtrang);
        radionoibat=dialog.findViewById(R.id.radionoibat);
        radiobinhthuong=dialog.findViewById(R.id.radiobinhthuong);
        spinnerthemmonan=dialog.findViewById(R.id.spinnerthemmonan);
        btthemmonan=dialog.findViewById(R.id.btthemmonan);
        bthuythemmonan=dialog.findViewById(R.id.bthuythemmonan);
    }

    private void laydulieulistview() {
        monAnArrayListquanli=new ArrayList<>();
        monAnAdapterQuanLi=new MonAnAdapterQuanLi(monAnArrayListquanli,getContext());
        lvmonanquanli.setAdapter(monAnAdapterQuanLi);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<MonAn>> call=apiService.AllMonAn();
        call.enqueue(new Callback<ArrayList<MonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<MonAn>> call, Response<ArrayList<MonAn>> response) {
                ArrayList<MonAn> monAns=response.body();
                for (int i=0;i<monAns.size();i++){
                    monAnArrayListquanli.add(monAns.get(i));
                }
                monAnAdapterQuanLi.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {
                Toast.makeText(getContext(),"Lỗi: "+t,Toast.LENGTH_LONG).show();
            }
        });
    }
}
