package com.example.duongngoc2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duongngoc2.R;
import com.example.duongngoc2.model.MonAn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MonAnNoiBatAdapter extends RecyclerView.Adapter<MonAnNoiBatAdapter.ItemHolder> {
    Context context;
    ArrayList<MonAn> monannoibatArrayList;
    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(MonAnNoiBatAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public MonAnNoiBatAdapter(Context context, ArrayList<MonAn> monannoibatArrayList) {
        this.context = context;
        this.monannoibatArrayList = monannoibatArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_recycleview_monannoibat,null);
        ItemHolder itemHolder=new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        MonAn monannoibat=monannoibatArrayList.get(position);
        holder.tvtenmonannb.setText(monannoibat.getTenmonan());
        //DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.tvgiamonannb.setText("Giá : "+ monannoibat.getGia()+" Đ ");
        Picasso.with(context).load(monannoibat.getHinhanhmonan())
                .placeholder(R.drawable.loading)
                .error(R.drawable.cancel)
                .into(holder.imghinhanhmonannb);

    }

    @Override
    public int getItemCount() {
        return monannoibatArrayList.size();
    }

    public class  ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhanhmonannb;
        public TextView tvtenmonannb,tvgiamonannb;

        public ItemHolder(@NonNull final View itemView) {
            super(itemView);
            imghinhanhmonannb=(ImageView) itemView.findViewById(R.id.imgviewmonannoibat);
            tvtenmonannb=(TextView)itemView.findViewById(R.id.tvtenmonannb);
            tvgiamonannb=(TextView)itemView.findViewById(R.id.tvgiamonannb);
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
