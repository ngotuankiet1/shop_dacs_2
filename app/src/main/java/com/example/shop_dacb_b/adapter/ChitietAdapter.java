package com.example.shop_dacb_b.adapter;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shop_dacb_b.R;
import com.example.shop_dacb_b.model.Product;
import com.example.shop_dacb_b.model.SanPhamDonHang;

import java.util.List;

public class ChitietAdapter extends RecyclerView.Adapter<ChitietAdapter.MyviewHolder> {
    Context context;
    List<SanPhamDonHang> Listproduct;

    public ChitietAdapter(Context context, List<SanPhamDonHang> listproduct) {
        this.context = context;
        Listproduct = listproduct;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        SanPhamDonHang product = Listproduct.get(position);
        holder.txtten.setText(product.getName()+"");
        holder.txtsoluong.setText("Số lượng: "+ product.getQuantity()+"");
        final String encodedString = "data:image/jpg;base64,"+product.getImage();
        final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
        final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
        Glide.with(context).load(decodedBytes).fitCenter().into(holder.imgchitiet);
    }

    @Override
    public int getItemCount() {
        return Listproduct.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        ImageView imgchitiet;
        TextView txtten,txtsoluong;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            imgchitiet = itemView.findViewById(R.id.item_imgchitiet);
            txtten = itemView.findViewById(R.id.item_tenchitiet);
            txtsoluong = itemView.findViewById(R.id.item_soluongchitiet);
        }
    }
}
