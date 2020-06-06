package com.example.duongngoc2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.TaiKhoan;

import java.util.ArrayList;

public class TaiKhoanAdapter extends BaseAdapter {
    ArrayList<TaiKhoan> taiKhoanArrayList;
    Context context;

    public TaiKhoanAdapter(ArrayList<TaiKhoan> taiKhoanArrayList, Context context) {
        this.taiKhoanArrayList = taiKhoanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return taiKhoanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return taiKhoanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview_taikhoan,null);
        TextView tvid=view.findViewById(R.id.tvidtaikhoanqli);
        TextView tvhoten=view.findViewById(R.id.tvhotentkqli);
        TextView tvsdt=view.findViewById(R.id.tvsdttkqli);
        TextView tvuser=view.findViewById(R.id.tvusertkqli);
        TextView tvpass=view.findViewById(R.id.tvpasstkqli);
        TextView tvphanloai=view.findViewById(R.id.tvphanloai);
        TaiKhoan taiKhoan= (TaiKhoan) getItem(i);
        tvid.setText("ID tài khoản: "+taiKhoan.getIdtaikhoan());
        tvhoten.setText("Họ tên: "+taiKhoan.getHoten());
        tvsdt.setText("Số điện thoai: 0"+taiKhoan.getSodienthoai());
        tvuser.setText("Username: "+taiKhoan.getUsername());
        if (taiKhoan.getLevel()==1){
            tvpass.setText("Password: "+taiKhoan.getPassword());
            tvphanloai.setText("Phân loại: Phía nhà hàng");
        }else {
            tvpass.setText("Password: ******");
            tvphanloai.setText("Phân loại: Phía khách hàng");
        }
        return view;

    }
}
