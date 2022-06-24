package com.example.shop_dacb_b.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shop_dacb_b.R;

public class LienHeActivity extends AppCompatActivity {
    TextView txtLienheApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        txtLienheApp = findViewById(R.id.textviewlienhe);

        String lienhe = "Địa chỉ: Khu công nghệ cao, quận 9, TP.HCM\n" +
                "\n" +
                "Điện thoại: 012345678\n" +
                "\n" +
                "Email: linhkiendienthoai@gamil.com";

        txtLienheApp.setText(lienhe);
    }
}