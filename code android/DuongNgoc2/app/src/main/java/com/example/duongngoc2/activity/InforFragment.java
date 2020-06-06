package com.example.duongngoc2.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.DonHangAdapter;
import com.example.duongngoc2.model.DonHang;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.CheckLogin;
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
public class InforFragment extends Fragment {
    public static ListView lvdonhang;
    public static ArrayList<DonHang> donHangArrayList;
    public static DonHangAdapter donHangAdapter;

    public InforFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_infor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvdonhang=(ListView) view.findViewById(R.id.lvdonhang);


        laydulieudonhang();
        lvdonhang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"bấm vào",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void laydulieudonhang() {
        donHangArrayList=new ArrayList<>();
        donHangAdapter=new DonHangAdapter(donHangArrayList,getContext());
        lvdonhang.setAdapter(donHangAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<DonHang>> call=apiService.getAllDonHangtheotaikhoan(CheckLogin.getIdtaikhoan());
        call.enqueue(new Callback<ArrayList<DonHang>>() {
            @Override
            public void onResponse(Call<ArrayList<DonHang>> call, Response<ArrayList<DonHang>> response) {
                ArrayList<DonHang> donHangtheotk=response.body();
                for (int i=0;i<donHangtheotk.size();i++){
                    donHangArrayList.add(donHangtheotk.get(i));
                }
                donHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<DonHang>> call, Throwable t) {
                Toast.makeText(getContext(),"ko the lay du lieu",Toast.LENGTH_LONG).show();
            }
        });
    }
}
