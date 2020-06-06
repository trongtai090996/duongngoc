package com.example.duongngoc2.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.QuanLiBinhLuanAdapter;
import com.example.duongngoc2.model.AllBinhLuan;
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
public class QuanLiBinhLuanFragment extends Fragment {
    ArrayList<AllBinhLuan> allBinhLuanArrayList;
    QuanLiBinhLuanAdapter quanLiBinhLuanAdapter;
    ListView lvquanlibinhluan;

    public QuanLiBinhLuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_li_binh_luan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvquanlibinhluan=view.findViewById(R.id.lvquanlibinhluan);
        laydulieu();
        xoabinhluan();
    }

    private void xoabinhluan() {
        lvquanlibinhluan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int idbinhluan=allBinhLuanArrayList.get(i).getIdbinhluan();
                AlertDialog.Builder alertDialog =new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Thông báo");
                alertDialog.setMessage("Bạn có muốn xóa bình luận này không?");
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Sever.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIService apiService = retrofit.create(APIService.class);
                        Call<String> call=apiService.postXoaBinhLuan(idbinhluan);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua=response.body();
                                if (ketqua.equals("SUCCES")==true){
                                    Toast.makeText(getContext(),"Xóa thành công",Toast.LENGTH_LONG).show();
                                    quanLiBinhLuanAdapter.notifyDataSetChanged();
                                    laydulieu();
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
            }
        });
    }

    private void laydulieu() {
        allBinhLuanArrayList=new ArrayList<>();
        quanLiBinhLuanAdapter=new QuanLiBinhLuanAdapter(allBinhLuanArrayList,getContext());
        lvquanlibinhluan.setAdapter(quanLiBinhLuanAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<AllBinhLuan>> call=apiService.AllBinhLuan();
        call.enqueue(new Callback<ArrayList<AllBinhLuan>>() {
            @Override
            public void onResponse(Call<ArrayList<AllBinhLuan>> call, Response<ArrayList<AllBinhLuan>> response) {
                ArrayList<AllBinhLuan> allBinhLuans=response.body();
                for (int i=0;i<allBinhLuans.size();i++){
                    allBinhLuanArrayList.add(allBinhLuans.get(i));
                }
                quanLiBinhLuanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<AllBinhLuan>> call, Throwable t) {

            }
        });
    }
}
