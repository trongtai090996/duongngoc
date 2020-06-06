package com.example.duongngoc2.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.BinhLuanAdapter;
import com.example.duongngoc2.model.BinhLuan;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
import com.example.duongngoc2.utils.Save;
import com.example.duongngoc2.utils.Sever;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class BinhLuanFragment extends Fragment {
    ArrayList<BinhLuan> binhLuanArrayList;
    BinhLuanAdapter binhLuanAdapter;
    ListView lvbinhluan;
    EditText edtnhapbinhluan;
    Button btguibinhluan;

    public BinhLuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_binh_luan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        laydulieu();
        bambinhluan();
    }

    private void bambinhluan() {
        btguibinhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckLogin.getCheck()==0){
                    Intent intent=new Intent(getContext(),DangNhapActivity.class);
                    startActivity(intent);
                }else {
                    thembinhluan();
                }
            }
        });
    }
    private  void thembinhluan(){
        if (edtnhapbinhluan.getText().toString().equals("")==false){
            String noidung=edtnhapbinhluan.getText().toString();
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            Date date=new Date();
            String ngaybinhluan=dateFormat.format(date);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Sever.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIService apiService = retrofit.create(APIService.class);
            Call<String> call=apiService.postThemBinhLuan(Save.getIdmonan(),CheckLogin.getIdtaikhoan(),noidung,ngaybinhluan);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String ketqua=response.body();
                    if (ketqua.equals("SUCCES")==true){
                        Toast.makeText(getContext(),"Gửi bình luận thành công",Toast.LENGTH_LONG).show();
                        laydulieu();
                    }else {
                        Toast.makeText(getContext(),"Gửi bình luận không thành công",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(getContext(),"Vui lòng nhập nội dung bình luận",Toast.LENGTH_LONG).show();
        }
    }
    private void laydulieu() {
        binhLuanArrayList=new ArrayList<>();
        binhLuanAdapter=new BinhLuanAdapter(binhLuanArrayList,getContext());
        lvbinhluan.setAdapter(binhLuanAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<BinhLuan>> call=apiService.postBinhLuan(Save.getIdmonan());
        call.enqueue(new Callback<ArrayList<BinhLuan>>() {
            @Override
            public void onResponse(Call<ArrayList<BinhLuan>> call, Response<ArrayList<BinhLuan>> response) {
                ArrayList<BinhLuan> binhLuans=response.body();
                for (int i=0;i<binhLuans.size();i++){
                    binhLuanArrayList.add(binhLuans.get(i));
                }
                binhLuanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<BinhLuan>> call, Throwable t) {

            }
        });
    }

    private void Anhxa(View view) {
        lvbinhluan=view.findViewById(R.id.lvbinhluan);
        edtnhapbinhluan=view.findViewById(R.id.edtnhapbinhluan);
        btguibinhluan=view.findViewById(R.id.btguibinhluan);
    }
}
