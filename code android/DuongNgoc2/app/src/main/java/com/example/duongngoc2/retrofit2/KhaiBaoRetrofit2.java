package com.example.duongngoc2.retrofit2;

import com.example.duongngoc2.utils.Sever;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KhaiBaoRetrofit2 {
    public static void KhaiBao(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
    }
}
