package com.example.shop_dacb_b.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_dacb_b.R;
import com.example.shop_dacb_b.adapter.GioHangAdapter;
import com.example.shop_dacb_b.model.EventBus.TinhTongEvent;
import com.example.shop_dacb_b.model.Giohang;
import com.example.shop_dacb_b.model.Order;
import com.example.shop_dacb_b.model.Product;
import com.example.shop_dacb_b.retrofit.ApiBanHang;
import com.example.shop_dacb_b.retrofit.RetrofitClient;
import com.example.shop_dacb_b.utils.GlobalVar;
import com.example.shop_dacb_b.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangActivity extends AppCompatActivity {

    TextView giohangtrong,tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangAdapter adapter;
    List<Giohang> giohangList;
    long tongtiensp;
//    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
//        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
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
        tongtien.setText(decimalFormat.format(tongtiensp) + "Đ" );
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

        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Test create order

                // Thêm danh sách sản phẩm và số lượng vào đây
//                ArrayList<Product> products = new ArrayList<>();
//                products.add(new Product(6, 1));

                // Tạo một object Order mới
//                Order order = new Order(
//                        GlobalVar.getInstance().name,
//                        "0123456789",
//                        "Linh Trung, Thu Duc, TP Ho Chi Minh",
//                        GlobalVar.getInstance().uid,
//                        products
//                );

//                Call<Boolean> call = apiBanHang.createOrder(order);

//                call.enqueue(new Callback<Boolean>() {
//                    @Override
//                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(GioHangActivity.this, "Successful, body: " + response.body().toString(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(GioHangActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Boolean> call, Throwable t) {
//                        Toast.makeText(GioHangActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                        Log.e("tag", "onFailure: " + t);
//                    }
//                });


                Intent intent = new Intent(getApplicationContext(),ThanhToanActivity.class);
                intent.putExtra("tongtien",tongtiensp );
                startActivity(intent);
            }
        });
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