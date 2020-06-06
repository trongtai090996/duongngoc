package com.example.duongngoc2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.LoaiMonAn;
import com.example.duongngoc2.model.MonAn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiMonAnAdapter extends RecyclerView.Adapter<LoaiMonAnAdapter.ItemHolder> {
    Context context;
    ArrayList<LoaiMonAn> loaiMonAnArrayList;
    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public LoaiMonAnAdapter(Context context, ArrayList<LoaiMonAn> loaiMonAnArrayList) {
        this.context = context;
        this.loaiMonAnArrayList = loaiMonAnArrayList;
    }

    @NonNull
    @Override
    public LoaiMonAnAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_recycleview_loaimonan,null);
        LoaiMonAnAdapter.ItemHolder itemHolder=new LoaiMonAnAdapter.ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        LoaiMonAn loaiMonAn=loaiMonAnArrayList.get(position);
        holder.tvtenloaimonan.setText(loaiMonAn.getTenloaimonan());
        //DecimalFormat decimalFormat=new DecimalFormat("###,###,###");

        Picasso.with(context).load(loaiMonAn.getLinkanh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.cancel)
                .into(holder.imghinhanhloaimonan);
    }


    @Override
    public int getItemCount() {
        return loaiMonAnArrayList.size();
    }
    public class  ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhanhloaimonan;
        public TextView tvtenloaimonan;

        public ItemHolder(@NonNull final View itemView) {
            super(itemView);
            imghinhanhloaimonan=(ImageView) itemView.findViewById(R.id.imgviewloaimonan);
            tvtenloaimonan=(TextView)itemView.findViewById(R.id.tvloaimonan);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }

}
