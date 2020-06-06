package com.example.duongngoc2.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Save;
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
public class MoTaFragment extends Fragment {
    TextView tvmotatab;



    public MoTaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mo_ta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvmotatab=view.findViewById(R.id.tvmotab);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<MonAn>> call=apiService.postMonAnTheoId(Save.getIdmonan());
        call.enqueue(new Callback<ArrayList<MonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<MonAn>> call, Response<ArrayList<MonAn>> response) {
                ArrayList<MonAn> monAns=response.body();
                tvmotatab.setText(monAns.get(0).getMota());
            }

            @Override
            public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {

            }
        });
    }
}
