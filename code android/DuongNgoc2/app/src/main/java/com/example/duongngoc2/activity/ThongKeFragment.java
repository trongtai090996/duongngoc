package com.example.duongngoc2.activity;

import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.MonAnDatNhieuNhatAdapter;
import com.example.duongngoc2.model.SoLuongDaBan;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongKeFragment extends Fragment {
    TextView tvtongdoanhthu;
    TextView tvchonngaybatdau,tvchonngayketthuc,tvngaybatdau,tvngayketthuc,tvxemdoanhthu,tvgiatridt;
    String ngaybatdau="ko",ngayketthuc="ko";
    ArrayList<SoLuongDaBan> soLuongDaBanArrayList;
    MonAnDatNhieuNhatAdapter monAnDatNhieuNhatAdapter;
    ListView lvmonannhieunhat;

    public ThongKeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        laydulieutongdoanhthu();

        chonngaybatdau();
        chonngayketthuc();
        thongkedoanhthu();
        laydulieulistview();
    }

    private void laydulieulistview() {
        soLuongDaBanArrayList=new ArrayList<>();
        monAnDatNhieuNhatAdapter=new MonAnDatNhieuNhatAdapter(soLuongDaBanArrayList,getContext());
        lvmonannhieunhat.setAdapter(monAnDatNhieuNhatAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        final Call<ArrayList<SoLuongDaBan>> soluongdaban=apiService.postSoLuongDaBan();
        soluongdaban.enqueue(new Callback<ArrayList<SoLuongDaBan>>() {
            @Override
            public void onResponse(Call<ArrayList<SoLuongDaBan>> call, Response<ArrayList<SoLuongDaBan>> response) {
                ArrayList<SoLuongDaBan> soLuongDaBans=response.body();
                for (int i=0;i<soLuongDaBans.size();i++){
                    soLuongDaBanArrayList.add(soLuongDaBans.get(i));
                }
                monAnDatNhieuNhatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<SoLuongDaBan>> call, Throwable t) {

            }
        });
    }

    private void thongkedoanhthu() {
        tvxemdoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ngaybatdau.equals("ko")==false && ngayketthuc.equals("ko")==false){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Sever.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIService apiService = retrofit.create(APIService.class);
                    Call<Integer> call=apiService.postThongKe(ngaybatdau,ngayketthuc);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            int doanhthu=response.body();
                            tvgiatridt.setVisibility(View.VISIBLE);
                            tvgiatridt.setText(doanhthu+" VND");
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });
                }else {
                    Toast.makeText(getContext(),"Hãy chọn ngày bắt đầu và ngày kết thúc",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void chonngayketthuc() {
        tvchonngayketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int ngay=calendar.get(Calendar.DATE);
                int thang=calendar.get(Calendar.MONTH);
                int nam=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i,i1,i2);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                        tvngayketthuc.setText(simpleDateFormat.format(calendar.getTime()));
                        tvngayketthuc.setVisibility(View.VISIBLE);
                        ngayketthuc=simpleDateFormat.format(calendar.getTime());
                    }
                },nam,thang,ngay);
                datePickerDialog.show();
            }
        });
    }

    private void chonngaybatdau() {
        tvchonngaybatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int ngay=calendar.get(Calendar.DATE);
                int thang=calendar.get(Calendar.MONTH);
                int nam=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i,i1,i2);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                        tvngaybatdau.setText(simpleDateFormat.format(calendar.getTime()));
                        tvngaybatdau.setVisibility(View.VISIBLE);
                        ngaybatdau=simpleDateFormat.format(calendar.getTime());
                    }
                },nam,thang,ngay);
                datePickerDialog.show();
            }
        });

    }



    private void Anhxa(View view) {
        tvtongdoanhthu=view.findViewById(R.id.tvtongdoanhthu);
        tvchonngaybatdau=view.findViewById(R.id.tvchonngaybatdau);
        tvchonngayketthuc=view.findViewById(R.id.tvchonngayketthuc);
        tvngaybatdau=view.findViewById(R.id.tvngaybatdau);
        tvngayketthuc=view.findViewById(R.id.tvngayketthuc);
        tvxemdoanhthu=view.findViewById(R.id.tvxemdoanhthu);
        tvgiatridt=view.findViewById(R.id.tvgiatridt);
        tvchonngaybatdau.setPaintFlags(tvchonngaybatdau.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvchonngayketthuc.setPaintFlags(tvchonngayketthuc.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvxemdoanhthu.setPaintFlags(tvxemdoanhthu.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        lvmonannhieunhat=view.findViewById(R.id.lvmonanchaynhat);
    }

    private void laydulieutongdoanhthu() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<Integer> call=apiService.TongDoanhThu();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int tongdoanhthu=response.body();
                tvtongdoanhthu.setText(tongdoanhthu+" VND");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
}
