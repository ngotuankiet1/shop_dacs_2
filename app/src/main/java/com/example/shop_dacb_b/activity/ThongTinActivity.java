package com.example.shop_dacb_b.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shop_dacb_b.R;

public class ThongTinActivity extends AppCompatActivity {
    TextView txtThongtinapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);

        txtThongtinapp = findViewById(R.id.textviewthongtin);

        String thongtin = "Ứng dụng bán linh kiện điện tử nhóm DK\n"+"Thành viên gồm:\n- Ngô Tuấn Kiệt\n- Lê Huỳnh Hoàng Khang\n- Đỗ Linh Huệ";

        txtThongtinapp.setText(thongtin);
    }
}