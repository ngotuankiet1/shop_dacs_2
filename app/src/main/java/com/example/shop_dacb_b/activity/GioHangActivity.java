package com.example.shop_dacb_b.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shop_dacb_b.R;
import com.example.shop_dacb_b.adapter.GioHangAdapter;
import com.example.shop_dacb_b.model.EventBus.TinhTongEvent;
import com.example.shop_dacb_b.model.Giohang;
import com.example.shop_dacb_b.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {

    TextView giohangtrong,tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangAdapter adapter;
    List<Giohang> giohangList;
    long tongtiensp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        tinhtongtien();
    }

    private void tinhtongtien() {
        tongtiensp = 0;
        for(int i = 0;i<Utils.manggiohang.size();i++){
            tongtiensp = tongtiensp + (Utils.manggiohang.get(i).getPrice() * Utils.manggiohang.get(i).getSoluong());
        };
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongtiensp));
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(Utils.manggiohang.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
        }else{
            adapter = new GioHangAdapter(getApplicationContext(),Utils.manggiohang );
            recyclerView.setAdapter(adapter);
        }

//        btnmuahang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),ThanhToanActivity.class);
//                intent.putExtra("tongtien",tongtiensp );
//                startActivity(intent);
//            }
//        });
    }

    private void initView() {
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recyclerViewgiohang);
        btnmuahang = findViewById(R.id.btnmuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void EventTinhTien(TinhTongEvent event){
        if(event != null){
            tinhtongtien();
        }
    }
}