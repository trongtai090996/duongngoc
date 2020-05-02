package com.example.duongngoc2.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.TaiKhoan;
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
public class AccountFragment extends Fragment {
    TextView textViewhoten,textViewsdt,textViewcapnhat1,textViewcapnhat2;
    Button buttondangxuat;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewhoten=(TextView) view.findViewById(R.id.tvhotentk);
        textViewsdt=(TextView) view.findViewById(R.id.tvsodienthoaitk);
        textViewcapnhat1=(TextView) view.findViewById(R.id.capnhat1);
        textViewcapnhat2=(TextView) view.findViewById(R.id.capnhat2);
        textViewcapnhat1.setPaintFlags(textViewcapnhat1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        textViewcapnhat2.setPaintFlags(textViewcapnhat2.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        buttondangxuat=(Button) view.findViewById(R.id.btdangxuat);
        if (CheckLogin.getCheck()==1){
            textViewhoten.setText("Họ tên: "+CheckLogin.getHoten());
            textViewsdt.setText("Số điện thoại: 0"+CheckLogin.getSodienthoai());
        }
        buttondangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckLogin.setCheck(0);
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        capnhatthongtin();
        doimatkhau();
    }

    private void doimatkhau() {
        textViewcapnhat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog= new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_capnhat2);
                dialog.show();
                final EditText edtmkcu,edtmkmoi,edtmkmoilai;
                Button btokdoimk,bthuydoimk;
                edtmkcu=dialog.findViewById(R.id.edtmkcu);
                edtmkmoi=dialog.findViewById(R.id.edtmkmoi);
                edtmkmoilai=dialog.findViewById(R.id.edtmkmoilai);
                btokdoimk=dialog.findViewById(R.id.btokdoimk);
                bthuydoimk=dialog.findViewById(R.id.bthuydoimk);
                btokdoimk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mkcu=edtmkcu.getText().toString();
                        final String mkmoi=edtmkmoi.getText().toString();
                        String mkmoilai=edtmkmoilai.getText().toString();
                        if (mkcu.equals("")==false && mkmoi.equals("")==false && mkmoilai.equals("")==false){
                            if (mkcu.equals(CheckLogin.getPassword())==true){
                                if (mkmoi.length()>=6){
                                    if (mkmoi.equals(mkmoilai)==true){
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(Sever.BASE_URL)
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                                        APIService apiService = retrofit.create(APIService.class);
                                        Call<String> call =apiService.postDoiMatKhau(CheckLogin.getIdtaikhoan(),mkmoi);
                                        call.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                String ketqua=response.body();
                                                if (ketqua.equals("SUCCES")==true){
                                                    Toast.makeText(getContext(),"Đổi mật khẩu thành công",Toast.LENGTH_LONG).show();
                                                    CheckLogin.setPassword(mkmoi);
                                                    dialog.dismiss();

                                                }else {
                                                    Toast.makeText(getContext(),"Đổi mật khẩu không thành công",Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                Toast.makeText(getContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }else {
                                        Toast.makeText(getContext(),"Nhập lại mật khẩu mới không trùng với mật khẩu mới",Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    Toast.makeText(getContext(),"Mật khẩu phải lớn hơn 5 ký tự",Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getContext(),"Mật khẩu cũ không đúng",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(getContext(),"Không được để trống",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                bthuydoimk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void capnhatthongtin() {
        textViewcapnhat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog= new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_capnhat1);
                final EditText edthotencapnhat,edtsdtcapnhat;
                edthotencapnhat=dialog.findViewById(R.id.edthotencapnhat);
                edtsdtcapnhat=dialog.findViewById(R.id.edtsdtcapnhate);
                edthotencapnhat.setText(CheckLogin.getHoten());
                edtsdtcapnhat.setText(String.valueOf(CheckLogin.getSodienthoai()));
                Button btokcapnhat, bthuycapnhat;
                btokcapnhat=dialog.findViewById(R.id.btokcapnhat);
                bthuycapnhat=dialog.findViewById(R.id.bthuycapnhat);
                btokcapnhat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String hoten=edthotencapnhat.getText().toString();
                        final String ttsodienthoai=edtsdtcapnhat.getText().toString();
                        Log.d("hoten + sdt: ",hoten +ttsodienthoai);
                        if (hoten.equals("")==false && ttsodienthoai.equals("")==false){
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            final int sodienthoai=Integer.parseInt(ttsodienthoai);
                            Call<String> call= apiService.getCapNhatTaiKhoan(CheckLogin.getIdtaikhoan(),hoten,sodienthoai);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("SUCCES")==true){
                                        Toast.makeText(getContext(),"Cập nhật thông tin thành công",Toast.LENGTH_LONG).show();
                                        CheckLogin.setHoten(hoten);
                                        CheckLogin.setSodienthoai(sodienthoai);
                                        textViewhoten.setText("Họ tên: "+CheckLogin.getHoten());
                                        textViewsdt.setText("Số điện thoại: 0"+CheckLogin.getSodienthoai());
                                    }else {
                                        Toast.makeText(getContext(),"Cập nhật thông tin không thành công",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.d("Lỗi: ",t.toString());
                                    Toast.makeText(getContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                                }
                            });
                            dialog.dismiss();
                        }else {
                            Toast.makeText(getContext(),"Không được để trống họ tên và số điện thoại",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                bthuycapnhat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }


}
