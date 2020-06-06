package com.example.duongngoc2.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.TaiKhoanAdapter;
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
public class QuanLiTaiKhoanFragment extends Fragment {
    ArrayList<TaiKhoan> taiKhoanArrayList;
    TaiKhoanAdapter taiKhoanAdapter;
    ListView lvtaikhoan;
    TextView tvthemtaikhoan;
    EditText edthotenthem,edtsdtthem,edtuserthem,edtpassthem,edtpassnhaplaithem;
    Button btokthem,bthuythem;
    EditText edthotensua,edtsdtsua,edtpasssua,edtpassnhaplaisua;
    TextView tvusersua;
    Button btoksuataikhoan,bthuysuataikhoan;

    public QuanLiTaiKhoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_li_tai_khoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        Laydulieulistview();
        themtaikhoan();
        suataikhoan();
        xoataikhoan();
    }

    private void xoataikhoan() {
        lvtaikhoan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int idtaikhoan=taiKhoanArrayList.get(i).getIdtaikhoan();
                if (taiKhoanArrayList.get(i).getLevel()==1){
                    if (taiKhoanArrayList.get(i).getIdtaikhoan()== CheckLogin.getIdtaikhoan()){
                        Toast.makeText(getContext(),"Không thể xóa tài khoản đang đăng nhập",Toast.LENGTH_LONG).show();
                    }else {
                        if (taiKhoanArrayList.get(i).getIdtaikhoan()==1){
                            Toast.makeText(getContext(),"Không thể xóa tài khoản này",Toast.LENGTH_LONG).show();
                        }else {

                            final AlertDialog.Builder alertDialog =new AlertDialog.Builder(getContext());
                            alertDialog.setTitle("Thông báo");
                            alertDialog.setMessage("Bạn có muốn xóa tài khoản: "+taiKhoanArrayList.get(i).getUsername()+" không?");
                            alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(Sever.BASE_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    APIService apiService = retrofit.create(APIService.class);
                                    Call<String> call=apiService.postXoaTaiKhoan(idtaikhoan);
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String ketqua=response.body();
                                            if (ketqua.equals("SUCCES")==true){
                                                Toast.makeText(getContext(),"Xóa thành công",Toast.LENGTH_LONG).show();
                                                taiKhoanAdapter.notifyDataSetChanged();
                                                Laydulieulistview();
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
                    }

                }else {
                    Toast.makeText(getContext(),"Không thể xóa tài khoản bên phía khách hàng",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }

    private void suataikhoan() {
        lvtaikhoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (taiKhoanArrayList.get(i).getLevel()==1){
                    final Dialog dialog= new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_suataikhoan);
                    dialog.show();
                    Anhxasuatk(dialog);
                    edthotensua.setText(taiKhoanArrayList.get(i).getHoten());
                    edtsdtsua.setText("0"+taiKhoanArrayList.get(i).getSodienthoai());
                    tvusersua.setText("Username: "+taiKhoanArrayList.get(i).getUsername());
                    edtpasssua.setText(taiKhoanArrayList.get(i).getPassword());
                    final int idtaikhoan=taiKhoanArrayList.get(i).getIdtaikhoan();
                    btoksuataikhoan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String hoten=edthotensua.getText().toString();
                            String sdt=edtsdtsua.getText().toString();
                            String pass=edtpasssua.getText().toString();
                            String passnhaplai=edtpassnhaplaisua.getText().toString();

                            if (hoten.equals("")==false && sdt.equals("")==false && pass.equals("")==false && passnhaplai.equals("")==false){
                                if (sdt.length()==10){
                                    if (pass.length()>5){
                                        if (pass.equals(passnhaplai)==true){
                                            int sodienthoai=Integer.parseInt(edtsdtsua.getText().toString());
                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(Sever.BASE_URL)
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();
                                            APIService apiService = retrofit.create(APIService.class);
                                            Call<String> call=apiService.postSuaTaiKhoan(idtaikhoan,hoten,sodienthoai,pass);
                                            call.enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {
                                                    String ketqua=response.body();
                                                    if (ketqua.equals("SUCCES")==true){
                                                        Toast.makeText(getContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
                                                        taiKhoanAdapter.notifyDataSetChanged();
                                                        Laydulieulistview();
                                                        dialog.dismiss();
                                                    }else {
                                                        Toast.makeText(getContext(),"Sửa ko thành công",Toast.LENGTH_LONG).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<String> call, Throwable t) {
                                                    Toast.makeText(getContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }else {
                                            Toast.makeText(getContext(),"Nhập lại mật khẩu phải trùng với mât",Toast.LENGTH_LONG).show();
                                        }
                                    }else {
                                        Toast.makeText(getContext(),"Mật khẩu phải lớn hơn 5 ký tự",Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    Toast.makeText(getContext(),"Số điện thoại phải đủ 10 ký tự",Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getContext(),"Không được để trống",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(getContext(),"Không thể sửa tài khoản bên phía khách hàng",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void Anhxasuatk(final Dialog dialog) {
        edthotensua=dialog.findViewById(R.id.edthotensuataikhoan);
        edtsdtsua=dialog.findViewById(R.id.edtsdtsuataikhoan);
        tvusersua=dialog.findViewById(R.id.tvusersuataikhoan);
        edtpasssua=dialog.findViewById(R.id.edtpasssuataikhoan);
        edtpassnhaplaisua=dialog.findViewById(R.id.edtpassnhaplaisuataikhoan);
        btoksuataikhoan=dialog.findViewById(R.id.btoksuataikhoan);
        bthuysuataikhoan=dialog.findViewById(R.id.bthuysuataikhoan);

        bthuysuataikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void themtaikhoan() {
        tvthemtaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog= new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_themtaikhoan);
                dialog.show();
                Anhxathemtk(dialog);
                lenhthem(dialog);
            }
        });
    }

    private void lenhthem(Dialog dialog) {
        btokthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten=edthotenthem.getText().toString();
                String sdt=edtsdtthem.getText().toString();
                String user=edtuserthem.getText().toString();
                String pass=edtpassthem.getText().toString();
                String passnhaplai=edtpassnhaplaithem.getText().toString();
                if (hoten.equals("")==false && sdt.equals("")==false && user.equals("")==false && pass.equals("")==false && passnhaplai.equals("")==false){
                    if (sdt.length()==10){
                        if (pass.length()>5){
                            if (pass.equals(passnhaplai)==true){
                                int sodienthoai=Integer.parseInt(edtsdtthem.getText().toString());
                                int level=1;
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(Sever.BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                APIService apiService = retrofit.create(APIService.class);
                                Call<String> call=apiService.getDangKi(hoten,sodienthoai,user,pass,level);
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String ketqua=response.body();
                                        if (ketqua.equals("FAIL")==true){
                                            Toast.makeText(getContext(),"Tài khoản đã tồn tại",Toast.LENGTH_LONG).show();
                                        }else {
                                            Toast.makeText(getContext(),"Thêm tài khoản thành công",Toast.LENGTH_LONG).show();
                                            taiKhoanAdapter.notifyDataSetChanged();
                                            Laydulieulistview();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.d("Lỗi : ",t.toString());
                                    }
                                });
                            }else {
                                Toast.makeText(getContext(),"Nhập lại mật khẩu phải trùng với mât",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getContext(),"Mật khẩu phải lớn hơn 5 ký tự",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getContext(),"Số điện thoại phải đủ 10 ký tự",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getContext(),"Không được để trống",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Anhxathemtk(final Dialog dialog) {
        edthotenthem=dialog.findViewById(R.id.edthotenthemtaikhoan);
        edtsdtthem=dialog.findViewById(R.id.edtsdtthemtaikhoan);
        edtuserthem=dialog.findViewById(R.id.edtuserthemtaikhoan);
        edtpassthem=dialog.findViewById(R.id.edtpassthemtaikhoan);
        edtpassnhaplaithem=dialog.findViewById(R.id.edtpassnhaplaithemtaikhoan);
        btokthem=dialog.findViewById(R.id.btoktaikhoan);
        bthuythem=dialog.findViewById(R.id.bthuythemtaikhoan);
        bthuythem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void Laydulieulistview() {
        taiKhoanArrayList=new ArrayList<>();
        taiKhoanAdapter=new TaiKhoanAdapter(taiKhoanArrayList,getContext());
        lvtaikhoan.setAdapter(taiKhoanAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<TaiKhoan>> call=apiService.AllTaiKhoan();
        call.enqueue(new Callback<ArrayList<TaiKhoan>>() {
            @Override
            public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                ArrayList<TaiKhoan> taiKhoans=response.body();
                for (int i=0;i<taiKhoans.size();i++){
                    taiKhoanArrayList.add(taiKhoans.get(i));
                }
                taiKhoanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {

            }
        });
    }

    private void Anhxa(View view) {
        tvthemtaikhoan=view.findViewById(R.id.tvthemtaikhoan);
        tvthemtaikhoan.setPaintFlags(tvthemtaikhoan.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        lvtaikhoan=view.findViewById(R.id.lvtaikhoani);
    }
}
