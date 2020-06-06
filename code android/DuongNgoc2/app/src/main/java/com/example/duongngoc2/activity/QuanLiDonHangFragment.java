package com.example.duongngoc2.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duongngoc2.R;
import com.example.duongngoc2.adapter.ChiTietDonHangAdapter;
import com.example.duongngoc2.adapter.DonHangAdapterQuanLi;
import com.example.duongngoc2.model.ChiTietDonHang;
import com.example.duongngoc2.model.DonHang;
import com.example.duongngoc2.model.LoaiMonAn;
import com.example.duongngoc2.retrofit2.APIService;
import com.example.duongngoc2.utils.Sever;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuanLiDonHangFragment extends Fragment {
    ArrayList<DonHang> donHangArrayListquanli;
    DonHangAdapterQuanLi donHangAdapterQuanLi;
    ListView lvdonhangquanli;
    ArrayList<ChiTietDonHang> chiTietDonHangArrayList;
    ChiTietDonHangAdapter chiTietDonHangAdapter;
    String tinhtrangmoi;

    public QuanLiDonHangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_li_don_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvdonhangquanli=view.findViewById(R.id.lvdonhangquanli);
        laydulieulistview();
        xemchitiet();
        chuyentrangthai();

    }

    private void chuyentrangthai() {
        lvdonhangquanli.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int vitri=i;
                if (donHangArrayListquanli.get(vitri).getTinhtrangdonhang().equals("Khách đã nhận hàng")==true || donHangArrayListquanli.get(i).getTinhtrangdonhang()
                .equals("Khách không nhận hàng")==true){
                    Toast.makeText(getContext(),"Không thể chuyển tình trạng đơn hàng",Toast.LENGTH_LONG).show();
                }
                //nếu cho xu ly
                if (donHangArrayListquanli.get(vitri).getTinhtrangdonhang().equals("Chờ xử lý")==true){
                    AlertDialog.Builder alertDialog =new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Thông báo");
                    alertDialog.setMessage("Bạn có muốn chuyển đơn hàng này sang tình trạng tiếp theo không?");
                    alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tinhtrangmoi="Chờ giao hàng";
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            Call<String> call=apiService.postChuyenTinhTrang(donHangArrayListquanli.get(vitri).getIddonhang(),tinhtrangmoi);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("SUCCES")==true){
                                        Toast.makeText(getContext(),"Chuyển tình trạng thành công",Toast.LENGTH_LONG).show();
                                        donHangAdapterQuanLi.notifyDataSetChanged();
                                        laydulieulistview();
                                    }else {
                                        Toast.makeText(getContext(),"Chuyển tình trạng không thành công",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getContext(),"Lỗi "+t,Toast.LENGTH_LONG).show();
                                }
                            });
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
                //neu cho giao hang
                if (donHangArrayListquanli.get(vitri).getTinhtrangdonhang().equals("Chờ giao hàng")==true){
                    AlertDialog.Builder alertDialog =new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Thông báo");
                    alertDialog.setMessage("Bạn có muốn chuyển đơn hàng này sang tình trạng tiếp theo không?");
                    alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tinhtrangmoi="Đang giao hàng";
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            Call<String> call=apiService.postChuyenTinhTrang(donHangArrayListquanli.get(vitri).getIddonhang(),tinhtrangmoi);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("SUCCES")==true){
                                        Toast.makeText(getContext(),"Chuyển tình trạng thành công",Toast.LENGTH_LONG).show();
                                        donHangAdapterQuanLi.notifyDataSetChanged();
                                        laydulieulistview();
                                    }else {
                                        Toast.makeText(getContext(),"Chuyển tình trạng không thành công",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getContext(),"Lỗi "+t,Toast.LENGTH_LONG).show();
                                }
                            });
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
                //neu dang giao hang
                if (donHangArrayListquanli.get(vitri).getTinhtrangdonhang().equals("Đang giao hàng")==true){
                    AlertDialog.Builder alertDialog =new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Thông báo");
                    alertDialog.setMessage("Bạn có muốn chuyển đơn hàng này sang tình trạng tiếp theo không?");
                    alertDialog.setPositiveButton("Khách đã nhận hàng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tinhtrangmoi="Khách đã nhận hàng";
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            Call<String> call=apiService.postChuyenTinhTrang(donHangArrayListquanli.get(vitri).getIddonhang(),tinhtrangmoi);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("SUCCES")==true){
                                        Toast.makeText(getContext(),"Chuyển tình trạng thành công",Toast.LENGTH_LONG).show();
                                        donHangAdapterQuanLi.notifyDataSetChanged();
                                        laydulieulistview();
                                    }else {
                                        Toast.makeText(getContext(),"Chuyển tình trạng không thành công",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getContext(),"Lỗi "+t,Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                    alertDialog.setNegativeButton("Khách không nhận hàng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tinhtrangmoi="Khách không nhận hàng";
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Sever.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            APIService apiService = retrofit.create(APIService.class);
                            Call<String> call=apiService.postChuyenTinhTrang(donHangArrayListquanli.get(vitri).getIddonhang(),tinhtrangmoi);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua=response.body();
                                    if (ketqua.equals("SUCCES")==true){
                                        Toast.makeText(getContext(),"Chuyển tình trạng thành công",Toast.LENGTH_LONG).show();
                                        donHangAdapterQuanLi.notifyDataSetChanged();
                                        laydulieulistview();
                                    }else {
                                        Toast.makeText(getContext(),"Chuyển tình trạng không thành công",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getContext(),"Lỗi "+t,Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });

                    alertDialog.setNeutralButton("Thoát", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }
                return false;
            }
        });
    }

    private void xemchitiet() {
        lvdonhangquanli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_chitietdonhang);
                ListView lv;
                Button btok=dialog.findViewById(R.id.btok);
                lv=dialog.findViewById(R.id.lvtest);
                btok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                chiTietDonHangArrayList =new ArrayList<>();
                chiTietDonHangAdapter=new ChiTietDonHangAdapter(chiTietDonHangArrayList,getContext());
                lv.setAdapter(chiTietDonHangAdapter);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Sever.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                Call<ArrayList<ChiTietDonHang>> call=apiService.getChiTietDonHang(donHangArrayListquanli.get(i).getIddonhang());
                call.enqueue(new Callback<ArrayList<ChiTietDonHang>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ChiTietDonHang>> call, Response<ArrayList<ChiTietDonHang>> response) {
                        ArrayList<ChiTietDonHang> chiTietDonHangs=response.body();
                        for (int i=0;i<chiTietDonHangs.size();i++){
                            chiTietDonHangArrayList.add(chiTietDonHangs.get(i));
                        }
                        chiTietDonHangAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ChiTietDonHang>> call, Throwable t) {
                        Toast.makeText(getContext(),"Lỗi: "+t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();
            }
        });
    }

    private void laydulieulistview() {
        donHangArrayListquanli=new ArrayList<>();
        donHangAdapterQuanLi=new DonHangAdapterQuanLi(donHangArrayListquanli,getContext());
        lvdonhangquanli.setAdapter(donHangAdapterQuanLi);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Sever.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<ArrayList<DonHang>> call=apiService.AllDonHang();
        call.enqueue(new Callback<ArrayList<DonHang>>() {
            @Override
            public void onResponse(Call<ArrayList<DonHang>> call, Response<ArrayList<DonHang>> response) {
                ArrayList<DonHang> donHangs=response.body();
                for (int i=0;i<donHangs.size();i++){
                    donHangArrayListquanli.add(donHangs.get(i));
                }
                donHangAdapterQuanLi.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<DonHang>> call, Throwable t) {
                Toast.makeText(getContext(),"Lỗi",Toast.LENGTH_LONG).show();
            }
        });
    }
}
