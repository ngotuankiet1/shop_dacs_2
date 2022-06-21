package com.example.shop_dacb_b.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.shop_dacb_b.R;
import com.example.shop_dacb_b.model.Giohang;
import com.example.shop_dacb_b.model.Order;
import com.example.shop_dacb_b.model.Product;
import com.example.shop_dacb_b.retrofit.ApiBanHang;
import com.example.shop_dacb_b.retrofit.RetrofitClient;
import com.example.shop_dacb_b.utils.GlobalVar;
import com.example.shop_dacb_b.utils.Utils;
//import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien, txtemail;
    EditText edtname, edtphone, edtdiachi;
    AppCompatButton btndathang;
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
//        countItem();
        initControl();
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long tongtien = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongtien) + "Đ" );
        txtemail.setText(GlobalVar.getInstance().emailAddress);

        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_name = edtname.getText().toString().trim();
                String str_phone = edtphone.getText().toString().trim();
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_name)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập tên", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_phone)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_diachi)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else {
                    // Test create order
                    // Thêm danh sách sản phẩm và số lượng vào đây
                    ArrayList<Product> products = new ArrayList<>();
                    for (int i = 0; i < Utils.manggiohang.size(); i++) {
                        products.add(new Product(Utils.manggiohang.get(i).getId(), Utils.manggiohang.get(i).getSoluong()));
                    }
//              Tạo một object Order mới
                    Order order = new Order(
                            str_name,
                            str_phone,
                            str_diachi,
                            GlobalVar.getInstance().uid,
                            products
                    );
                    Call<Boolean> call = apiBanHang.createOrder(order);

                    for (Product p : order.getProducts()) {
                        Log.e("tag", "product: " + p.getId() + " - " + p.getQuantity());
                    }

                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful()) {
//                                Utils.manggiohang.removeAll(Utils.manggiohang);
                                Toast.makeText(ThanhToanActivity.this, "Successful, body: " + response.body().toString(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                // Xoa gio hang
                                Utils.manggiohang.clear();
                                startActivity(intent);
                            } else {
                                Toast.makeText(ThanhToanActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                                Log.e("tag", "onResponse: " + response.errorBody());
                                Log.e("tag", "onResponse: " + response.message());
                                Log.e("tag", "onResponse: " + response.code());

                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(ThanhToanActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            Log.e("tag", "onFailure: " + t);
                        }
                    });
                }
            }
        });
    }


    private void initView() {
        toolbar = findViewById(R.id.toobar);
        txttongtien = findViewById(R.id.txttongtien);
        txtemail = findViewById(R.id.txtemail);
        edtname = findViewById(R.id.edtname);
        edtphone = findViewById(R.id.edtphone);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndathang = findViewById(R.id.btndathang);
    }
}