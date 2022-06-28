package com.example.shop_dacb_b.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_dacb_b.R;
import com.example.shop_dacb_b.model.Donhang;
import com.example.shop_dacb_b.model.SanPhamDonHang;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private  RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<Donhang> listdonhang;
    public DonHangAdapter(Context context, List<Donhang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Donhang donhang = listdonhang.get(position);
        holder.txtdonhang.setText("Đơn hàng: "+donhang.getId() + " ");
//        if (donhang.isStatus() == true) {
//            holder.txttrangthai.setTextColor(Color.parseColor("#08d57f"));
//            holder.txttrangthai.setText("Hoàn thành");
//        }else if(donhang.isStatus() == false){
//            holder.txttrangthai.setTextColor(Color.parseColor("#ffd700"));
//            holder.txttrangthai.setText("Đang chờ duyệt");
//        } else{
//            holder.txttrangthai.setTextColor(Color.parseColor("#ff0000"));
//            holder.txttrangthai.setText("Đã hủy");
//        }
        if(donhang.getStatus() == null){
            holder.txttrangthai.setTextColor(Color.parseColor("#ff0000"));
            holder.txttrangthai.setText("Đã hủy");
        }else if (donhang.getStatus() == false){
            holder.txttrangthai.setTextColor(Color.parseColor("#ffd700"));
            holder.txttrangthai.setText("Đang chờ duyệt");
        }else{
            holder.txttrangthai.setTextColor(Color.parseColor("#08d57f"));
            holder.txttrangthai.setText("Hoàn thành");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.reChitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donhang.getProducts().size());
        ChitietAdapter chitietAdapter = new ChitietAdapter(context,donhang.getProducts());
        holder.reChitiet.setLayoutManager(layoutManager);
        holder.reChitiet.setAdapter(chitietAdapter);
        holder.reChitiet.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtdonhang;
        TextView txttrangthai;
        RecyclerView reChitiet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            txttrangthai = itemView.findViewById(R.id.trangthaidonhang);
            reChitiet = itemView.findViewById(R.id.recyclerView_chitiet);
        }
    }
}
