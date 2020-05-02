package com.example.duongngoc2.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.DoAnTheoLoaiAdapter;
import com.example.duongngoc2.adapter.LoaiMonAnAdapter;
import com.example.duongngoc2.adapter.MonAnNoiBatAdapter;
import com.example.duongngoc2.model.LoaiMonAn;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {
    RecyclerView recyclerViewloaimonan;
    ListView listViewmonantheoloai;
    ArrayList<LoaiMonAn> loaiMonAnArrayList;
    LoaiMonAnAdapter loaiMonAnAdapter;
    public static int loaimonan=1;
    DoAnTheoLoaiAdapter doAnTheoLoaiAdapter;
    ArrayList<MonAn> monAnArrayListtheoloai;

    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewloaimonan=view.findViewById(R.id.recyclerviewloaimonan);
        listViewmonantheoloai=view.findViewById(R.id.listviewmonantheoloai);
        getAllLoaimonan();
        chonloaimonan();
        getALLmonan();
        chonmonan();
    }

    private void chonmonan() {
        listViewmonantheoloai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getContext(),ChiTietMonAn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle=new Bundle();
                bundle.putInt("id_mon_an",monAnArrayListtheoloai.get(i).getIdmonan());
                bundle.putInt("id_danh_muc",monAnArrayListtheoloai.get(i).getIddanhmuc());
                bundle.putString("ten_mon_an",monAnArrayListtheoloai.get(i).getTenmonan());
                bundle.putString("mo_ta",monAnArrayListtheoloai.get(i).getMota());
                bundle.putInt("gia",monAnArrayListtheoloai.get(i).getGia());
                bundle.putString("tinh_trang",monAnArrayListtheoloai.get(i).getMota());
                bundle.putInt("so_luong",monAnArrayListtheoloai.get(i).getSoluong());
                bundle.putString("hinhanh_monan",monAnArrayListtheoloai.get(i).getHinhanhmonan());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }


    private void getALLmonan() {
        monAnArrayListtheoloai=new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<MonAn>> call = apiService.getAllMonantheoloai(loaimonan);
        call.enqueue(new Callback<ArrayList<MonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<MonAn>> call, retrofit2.Response<ArrayList<MonAn>> response) {
                ArrayList<MonAn> monantheoloai=response.body();
                //Toast.makeText(MainActivity.this,""+a,Toast.LENGTH_LONG).show();
                for (int i=0;i<monantheoloai.size();i++){
                    monAnArrayListtheoloai.add(monantheoloai.get(i));
                    Log.d("vi tri i",monAnArrayListtheoloai.get(i).getTenmonan());
                }
                doAnTheoLoaiAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {
                Toast.makeText(getContext(),"ko the lay du lieu",Toast.LENGTH_LONG).show();
            }
        });
        doAnTheoLoaiAdapter=new DoAnTheoLoaiAdapter(monAnArrayListtheoloai,getContext());
        listViewmonantheoloai.setAdapter(doAnTheoLoaiAdapter);
    }

    private void chonloaimonan() {
        loaiMonAnAdapter.setOnItemClickListener(new LoaiMonAnAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                loaimonan=position+1;
                getALLmonan();
            }
        });
    }

    private void getAllLoaimonan() {
        loaiMonAnArrayList=new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<LoaiMonAn>> call = apiService.getAllLoaimonan();
        call.enqueue(new Callback<ArrayList<LoaiMonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<LoaiMonAn>> call, retrofit2.Response<ArrayList<LoaiMonAn>> response) {
                ArrayList<LoaiMonAn> loaiMonAns=response.body();
                //Toast.makeText(MainActivity.this,""+a,Toast.LENGTH_LONG).show();
                for (int i=0;i<loaiMonAns.size();i++){
                    loaiMonAnArrayList.add(loaiMonAns.get(i));
                    Log.d("vi tri i",loaiMonAnArrayList.get(i).getTenloaimonan());
                }
                loaiMonAnAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ArrayList<LoaiMonAn>> call, Throwable t) {
                Toast.makeText(getContext(),"ko the lay du lieu",Toast.LENGTH_LONG).show();
            }
        });
        loaiMonAnAdapter=new LoaiMonAnAdapter(getContext(),loaiMonAnArrayList);
        recyclerViewloaimonan.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewloaimonan.setLayoutManager(linearLayoutManager);
        recyclerViewloaimonan.setAdapter(loaiMonAnAdapter);
    }
}
