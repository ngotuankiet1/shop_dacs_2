package com.example.shop_dacb_b.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shop_dacb_b.Interface.ImageClickListenner;
import com.example.shop_dacb_b.R;
//import com.example.shop_dacb_b.model.EventBus.TinhTongEvent;
import com.example.shop_dacb_b.model.EventBus.TinhTongEvent;
import com.example.shop_dacb_b.model.Giohang;
import com.example.shop_dacb_b.utils.Utils;
import com.example.shop_dacb_b.model.Giohang;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<Giohang> giohangList;

    public GioHangAdapter(Context context, List<Giohang> giohangList) {
        this.context = context;
        this.giohangList = giohangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.MyViewHolder holder, int position) {
        Giohang giohang = giohangList.get(position);
        holder.item_giohang_tensp.setText(giohang.getName());
        holder.item_giohang_soluong.setText(giohang.getSoluong() +"");
        final String encodedString = "data:image/jpg;base64,"+giohang.getImage();
        final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
        final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
        Glide.with(context).load(decodedBytes).fitCenter().into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format((giohang.getPrice()))+ "Đ" );
        long gia = giohang.getSoluong()*giohang.getPrice();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia)+ "Đ");

        holder.setListenner(new ImageClickListenner() {
            @Override
            public void OnImageClick(View view, int pos, int giatri) {
                Log.d("TAG", "OnImageClick: "+ pos + "..."+giatri);
                if(giatri == 1){
                    if(giohangList.get(pos).getSoluong() > 1){
                        int soluongmoi = giohangList.get(pos).getSoluong()-1;
                        giohangList.get(pos).setSoluong(soluongmoi);

                        holder.item_giohang_soluong.setText(giohangList.get(pos).getSoluong()+ " ");
                        long gia = giohangList.get(pos).getSoluong() * giohangList.get(pos).getPrice();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else if(giohangList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này không");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                }else if (giatri == 2){
                    if(giohangList.get(pos).getSoluong() < 10){
                        int soluongmoi = giohangList.get(pos).getSoluong()+1;
                        giohangList.get(pos).setSoluong(soluongmoi);
                    }
                    holder.item_giohang_soluong.setText(giohangList.get(pos).getSoluong()+ " ");
                    long gia = giohangList.get(pos).getSoluong() * giohangList.get(pos).getPrice();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return giohangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image,imgtru,imgcong;
        TextView item_giohang_tensp,item_giohang_gia,item_giohang_soluong,item_giohang_giasp2;
        ImageClickListenner listenner;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);

            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }

        public void  setListenner(ImageClickListenner listenner){
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if(view== imgtru){
                listenner.OnImageClick(view,getAdapterPosition(),1);
            }else if (view == imgcong){
                listenner.OnImageClick(view,getAdapterPosition(),2);
            }
        }
    }
}
