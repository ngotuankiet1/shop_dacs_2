package com.example.shop_dacb_b.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shop_dacb_b.R;
//import com.example.shop_dacb_b.adapter.DonHangAdapter;
import com.example.shop_dacb_b.adapter.ChitietAdapter;
import com.example.shop_dacb_b.adapter.DonHangAdapter;
import com.example.shop_dacb_b.model.Donhang;
import com.example.shop_dacb_b.model.Item;
import com.example.shop_dacb_b.model.SanPhamDonHang;
import com.example.shop_dacb_b.retrofit.ApiBanHang;
import com.example.shop_dacb_b.retrofit.RetrofitClient;
import com.example.shop_dacb_b.utils.GlobalVar;
import com.example.shop_dacb_b.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XemDonActivity extends AppCompatActivity {
    ApiBanHang apiBanHang;
    List<Donhang> manghoadon;
    List<SanPhamDonHang> mangchitetdonhang;
    DonHangAdapter donHangAdapter;
    RecyclerView redonhang;
    Toolbar toolbar;
    int idOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        initToobar();
        GetOrder();
    }

    private void GetData(int id, int pos) {
            Call<Donhang> call = apiBanHang.getOrderDetails(id,"Bearer "+GlobalVar.getInstance().token);
            call.enqueue(new Callback<Donhang>() {
                @Override
                public void onResponse(Call<Donhang> call, Response<Donhang> response) {
//                    mangchitetdonhang = response.body().getProducts();
//                    Log.e("content",mangchitetdonhang.size()+" ");
                    Log.e("tag", "onResponse: " + response.body().getProducts().size() );
                    manghoadon.get(pos).setProducts(response.body().getProducts());
                    donHangAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Donhang> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"không kết nối được server",Toast.LENGTH_LONG).show();
                    Log.e("Log","error : "+t.getMessage());
                }
            });

    }

    private void GetOrder() {
        Call<List<Donhang>> call = apiBanHang.getOrder(GlobalVar.getInstance().uid,"Bearer "+GlobalVar.getInstance().token);
        call.enqueue(new Callback<List<Donhang>>() {
            @Override
            public void onResponse(Call<List<Donhang>> call, Response<List<Donhang>> response) {
                manghoadon.addAll(response.body());
                for (int i = manghoadon.size() - 1; i >= 0; i--) {
                    Donhang donhang = manghoadon.get(i);
                    donhang.initProducts();
                    GetData(donhang.getId(), i);
                }
                Log.e("size : ",manghoadon.size()+ " ");
            }
            @Override
            public void onFailure(Call<List<Donhang>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"không kết nối được server",Toast.LENGTH_LONG).show();
                Log.e("Log","error : "+t.getMessage());
            }
        });
    }

    private void initToobar() {
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
        manghoadon = new ArrayList<>();
        mangchitetdonhang = new ArrayList<>();
        redonhang = findViewById(R.id.recyclerview_donhang);
        toolbar = findViewById(R.id.toobar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(redonhang.getContext(),
                layoutManager.getOrientation());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        redonhang.addItemDecoration(dividerItemDecoration);
        redonhang.setLayoutManager(layoutManager);
        donHangAdapter = new DonHangAdapter(this, manghoadon);
        redonhang.setAdapter(donHangAdapter);
    }

}