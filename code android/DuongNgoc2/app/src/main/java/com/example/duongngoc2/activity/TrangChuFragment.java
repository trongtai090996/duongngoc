package com.example.duongngoc2.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.LoaiMonAnAdapter;
import com.example.duongngoc2.adapter.MonAnNoiBatAdapter;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrangChuFragment extends Fragment {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    int iddanhmuc=0;
    String tenloaimonan="";
    String linkanh="";
    ArrayList<MonAn> monannoibatArrayList;
    MonAnNoiBatAdapter monannoibatAdapter;
    public static int numbermenu=0;

    public TrangChuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trangchu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewmanhinhchinh=view.findViewById(R.id.recyclerviewmanhinhchinh);
        viewFlipper=view.findViewById(R.id.viewflipper);
        getALLmonannoibat();
        QuangCao();
        bamvaomonan();
    }

    private void bamvaomonan() {
        monannoibatAdapter.setOnItemClickListener(new MonAnNoiBatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent =new Intent(getContext(),ChiTietMonAn.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id_mon_an",monannoibatArrayList.get(position).getIdmonan());
                bundle.putInt("id_danh_muc",monannoibatArrayList.get(position).getIddanhmuc());
                bundle.putString("ten_mon_an",monannoibatArrayList.get(position).getTenmonan());
                bundle.putString("mo_ta",monannoibatArrayList.get(position).getMota());
                bundle.putInt("gia",monannoibatArrayList.get(position).getGia());
                bundle.putString("tinh_trang",monannoibatArrayList.get(position).getMota());
                bundle.putString("hinhanh_monan",monannoibatArrayList.get(position).getHinhanhmonan());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void QuangCao() {
        ArrayList<String> mangquangcao=new ArrayList<>();
        mangquangcao.add("https://cdn.24h.com.vn/upload/4-2018/images/2018-10-28/Goi-y-nhung-mon-an-sang-2-1540723904-750-width554height364.jpg");
        mangquangcao.add("https://cdn.24h.com.vn/upload/4-2018/images/2018-10-28/Goi-y-nhung-mon-an-sang-3-1540723904-43-width800height500.jpg");
        mangquangcao.add("https://ttol.vietnamnetjsc.vn/images/2018/04/18/15/51/canh-dau-phu-gia-do.jpg");
        for (int i=0;i<mangquangcao.size();i++){
            ImageView imageView=new ImageView(getContext());
            Picasso.with(getContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(5000);
            viewFlipper.setAutoStart(true);
        }
    }

    private void getALLmonannoibat() {
        monannoibatArrayList=new ArrayList<>();
        monannoibatAdapter=new MonAnNoiBatAdapter(getContext(),monannoibatArrayList);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewmanhinhchinh.setAdapter(monannoibatAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<MonAn>> call = apiService.getAllMonannoibat();
        call.enqueue(new Callback<ArrayList<MonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<MonAn>> call, retrofit2.Response<ArrayList<MonAn>> response) {
                ArrayList<MonAn> monannoibats=response.body();
                //Toast.makeText(MainActivity.this,""+a,Toast.LENGTH_LONG).show();
                for (int i=0;i<monannoibats.size();i++){
                    monannoibatArrayList.add(monannoibats.get(i));
                    Log.d("vi tri i",monannoibatArrayList.get(i).getTenmonan());
                }
                monannoibatAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {
                Toast.makeText(getContext(),"ko the lay du lieu",Toast.LENGTH_LONG).show();
            }
        });

    }
}
