package com.example.shop_dacb_b.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shop_dacb_b.R;
//import com.example.shop_dacb_b.adapter.DienThoaiAdapter;
import com.example.shop_dacb_b.adapter.DienThoaiAdapter;
import com.example.shop_dacb_b.adapter.SanPhamMoiAdapter;
import com.example.shop_dacb_b.model.SanPhamMoi;
import com.example.shop_dacb_b.retrofit.ApiBanHang;
import com.example.shop_dacb_b.retrofit.RetrofitClient;
import com.example.shop_dacb_b.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    int loai;
    DienThoaiAdapter adapterDt;
    List<SanPhamMoi> sanPhamMoiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        loai = getIntent().getIntExtra("loai",2);
        Anhxa();
        ActionTooBar();
        getData();
    }

    private void getData() {
        Call<List<SanPhamMoi>> call = apiBanHang.getProductByCate(loai);
        call.enqueue(new Callback<List<SanPhamMoi>>() {
            @Override
            public void onResponse(Call<List<SanPhamMoi>> call, Response<List<SanPhamMoi>> response) {
                sanPhamMoiList = response.body();
//                Log.e("products",sanPhamMoiList.size()+"");
                adapterDt = new DienThoaiAdapter(getApplicationContext(), sanPhamMoiList);
                recyclerView.setAdapter(adapterDt);
            }

            @Override
            public void onFailure(Call<List<SanPhamMoi>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"không kết nối được server",Toast.LENGTH_LONG).show();
                Log.e("Log","error : "+t.getMessage());
            }
        });
    }

    private void ActionTooBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recyclerView_dt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }
}