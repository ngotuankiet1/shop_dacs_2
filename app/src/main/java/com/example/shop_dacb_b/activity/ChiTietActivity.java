package com.example.shop_dacb_b.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shop_dacb_b.R;
import com.example.shop_dacb_b.adapter.DienThoaiAdapter;
import com.example.shop_dacb_b.model.Giohang;
import com.example.shop_dacb_b.model.SanPhamMoi;
import com.example.shop_dacb_b.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChiTietActivity extends AppCompatActivity {

    TextView tensp,giasp,mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionTooBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if(Utils.manggiohang.size() > 0){
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for(int i = 0;i < Utils.manggiohang.size();i++){
                if(Utils.manggiohang.get(i).getId() == sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    int gia = sanPhamMoi.getPrice() * Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setPrice(gia);
                    flag = true;
                }
            }
            if(flag == false){
                int gia = sanPhamMoi.getPrice() * soluong;
                Giohang giohang = new Giohang();
                giohang.setPrice(gia);
                giohang.setSoluong(soluong);
                giohang.setId(sanPhamMoi.getId());
                giohang.setName(sanPhamMoi.getName());
                giohang.setImage(sanPhamMoi.getImage());
                Utils.manggiohang.add(giohang);
            }

        }else{
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            int gia = sanPhamMoi.getPrice() * soluong;
            Giohang giohang = new Giohang();
            giohang.setPrice(gia);
            giohang.setSoluong(soluong);
            giohang.setId(sanPhamMoi.getId());
            giohang.setName(sanPhamMoi.getName());
            giohang.setImage(sanPhamMoi.getImage());
            Utils.manggiohang.add(giohang);
        }
        int totalItem = 0;
        for(int i =0;i<Utils.manggiohang.size();i++){
            totalItem = totalItem+ Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getName());
        mota.setText(sanPhamMoi.getDescription());

        final String encodedString = "data:image/jpg;base64,"+sanPhamMoi.getImage();
        final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
        final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
        Glide.with(getApplicationContext()).load(decodedBytes).fitCenter().into(imghinhanh);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: "+decimalFormat.format(sanPhamMoi.getPrice())+ "Đ");
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(arrayAdapter);
    }

    private void ActionTooBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        imghinhanh = findViewById(R.id.imgchitiet);
        spinner = findViewById(R.id.spinner);
        btnthem = findViewById(R.id.btnthemgiohang);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });

        if(Utils.manggiohang != null){
            int totalItem = 0;
            for(int i =0;i<Utils.manggiohang.size();i++){
                totalItem = totalItem+ Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    @Override
    protected void onResume() {
        if(Utils.manggiohang != null){
            int totalItem = 0;
            for(int i =0;i<Utils.manggiohang.size();i++){
                totalItem = totalItem+ Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
        super.onResume();
    }
}