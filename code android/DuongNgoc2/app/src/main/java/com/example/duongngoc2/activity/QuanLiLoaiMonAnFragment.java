package com.example.duongngoc2.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.LoaiMonAnAdapter;
import com.example.duongngoc2.adapter.LoaiMonAnAdapterAdmin;
import com.example.duongngoc2.model.LoaiMonAn;
import com.example.duongngoc2.model.MonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuanLiLoaiMonAnFragment extends Fragment {
    ListView lvloaimonan;
    ArrayList<LoaiMonAn> loaiMonAnArrayListadmin;
    LoaiMonAnAdapterAdmin loaiMonAnAdapterAdmin;
    TextView tvthem;

    public QuanLiLoaiMonAnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_li_loai_mon_an, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvthem=view.findViewById(R.id.tvthemloaimonan);
        tvthem.setPaintFlags(tvthem.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        lvloaimonan=view.findViewById(R.id.lvloaimonan);

        laydulieulistview();
        them();
        update();
        xoa();

    }

    private void xoa() {
        lvloaimonan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final int iddanhmuc=loaiMonAnArrayListadmin.get(i).getIddanhmuc();
                final AlertDialog.Builder alertDialog =new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Thông báo");
                alertDialog.setMessage("Bạn có muốn xóa loại món ăn: "+loaiMonAnArrayListadmin.get(i).getTenloaimonan()+" không?");
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Sever.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIService apiService = retrofit.create(APIService.class);
                        Call<String> call=apiService.postXoaLoaiMonAn(iddanhmuc);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua=response.body();
                                if (ketqua.equals("SUCCES")==true){
                                    Toast.makeText(getContext(),"Xóa thành công",Toast.LENGTH_LONG).show();
                                    loaiMonAnAdapterAdmin.notifyDataSetChanged();
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


    private void them() {
        tvthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog= new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_themloaimonan);
                dialog.show();
                final EditText edttenloaimonanthem,edthinhanhloaimonanthem;
                Button btthemloaimonan,bthuythemloaimonan;
                edttenloaimonanthem=dialog.findViewById(R.id.edttenloaimonanthem);
                edthinhanhloaimonanthem=dialog.findViewById(R.id.edtlinkanhloaimonanthem);
                btthemloaimonan=dialog.findViewById(R.id.btthemloaimonan);
                bthuythemloaimonan=dialog.findViewById(R.id.bthuythemloaimonan);
                btthemloaimonan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tenloaimonan=edttenloaimonanthem.getText().toString();
                        String linkanh=edthinhanhloaimonanthem.getText().toString();
                        if (tenloaimonan.equals("")==false && linkanh.equals("")==false){
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            Call<String> call=apiService.postThemLoaiMonAn(tenloaimonan,linkanh);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("TRUNG")){
                                        Toast.makeText(getContext(),"Loại món ăn đã tồn tại",Toast.LENGTH_LONG).show();
                                    }else {
                                        if (ketqua.equals("SUCCES")){
                                            Toast.makeText(getContext(),"Thêm loại món ăn thành công",Toast.LENGTH_LONG).show();
                                            loaiMonAnAdapterAdmin.notifyDataSetChanged();
                                            laydulieulistview();
                                            dialog.dismiss();
                                        }else {
                                            Toast.makeText(getContext(),"Thêm loại món ăn không thành công",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getContext(),"Lỗi"+t,Toast.LENGTH_LONG).show();
                                }
                            });
                        }else {
                            Toast.makeText(getContext(),"Không được để trống",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                bthuythemloaimonan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    private void update() {
        lvloaimonan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog= new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_sualoaimonan);
                Button btcapnhatloaimonan,bthuyloaimonan;
                final EditText edttenloaimonansua,edthinhanhloaimonansua;
                edttenloaimonansua=dialog.findViewById(R.id.edttenloaimonansua);
                edthinhanhloaimonansua=dialog.findViewById(R.id.edtlinkanhloaimonansua);
                edttenloaimonansua.setText(loaiMonAnArrayListadmin.get(i).getTenloaimonan());
                edthinhanhloaimonansua.setText(loaiMonAnArrayListadmin.get(i).getLinkanh());
                final int iddanhmuc=loaiMonAnArrayListadmin.get(i).getIddanhmuc();
                btcapnhatloaimonan=dialog.findViewById(R.id.btcapnhatloaimonan);
                bthuyloaimonan=dialog.findViewById(R.id.bthuyloaimonan);
                btcapnhatloaimonan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tenmoi=edttenloaimonansua.getText().toString();
                        String anhmoi=edthinhanhloaimonansua.getText().toString();
                        if (tenmoi.equals("")==false && anhmoi.equals("")==false){
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            Call<String> call=apiService.postSuaLoaiMonAn(iddanhmuc,tenmoi,anhmoi);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("SUCCES")==true){
                                        Toast.makeText(getContext(),"Cập nhật thành công",Toast.LENGTH_LONG).show();
                                        loaiMonAnAdapterAdmin.notifyDataSetChanged();
                                        laydulieulistview();
                                        dialog.dismiss();
                                    }else {
                                        Toast.makeText(getContext(),"Cập nhật không thành công",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getContext(),"Lỗi"+t,Toast.LENGTH_LONG).show();
                                }
                            });

                        }else {
                            Toast.makeText(getContext(),"Không được để trống",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                bthuyloaimonan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void laydulieulistview() {
        loaiMonAnArrayListadmin=new ArrayList<>();
        loaiMonAnAdapterAdmin=new LoaiMonAnAdapterAdmin(getContext(),loaiMonAnArrayListadmin);
        lvloaimonan.setAdapter(loaiMonAnAdapterAdmin);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<LoaiMonAn>> call = apiService.getAllLoaimonan();
        call.enqueue(new Callback<ArrayList<LoaiMonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<LoaiMonAn>> call, Response<ArrayList<LoaiMonAn>> response) {
                ArrayList<LoaiMonAn> loaiMonAns=response.body();
                for (int i=0;i<loaiMonAns.size();i++){
                    loaiMonAnArrayListadmin.add(loaiMonAns.get(i));
                }
                loaiMonAnAdapterAdmin.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<LoaiMonAn>> call, Throwable t) {

            }
        });
    }
}
