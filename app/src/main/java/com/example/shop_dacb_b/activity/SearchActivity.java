package com.example.shop_dacb_b.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shop_dacb_b.R;
import com.example.shop_dacb_b.adapter.DienThoaiAdapter;
import com.example.shop_dacb_b.model.SanPhamMoi;
import com.example.shop_dacb_b.retrofit.ApiBanHang;
import com.example.shop_dacb_b.retrofit.RetrofitClient;
import com.example.shop_dacb_b.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText edtsearch;
    DienThoaiAdapter adapterDt;
    List<SanPhamMoi> sanPhamMoiList;
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        ActionTooBar();
    }

    private void ActionTooBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        sanPhamMoiList = new ArrayList<>();
        edtsearch = findViewById(R.id.edtsearch);
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recyclerView_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.length()==0){
                    sanPhamMoiList.clear();
                    adapterDt = new DienThoaiAdapter(getApplicationContext(),sanPhamMoiList);
                    recyclerView.setAdapter(adapterDt);
                }
                else{
                    getDataSearch(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getDataSearch(String s) {
        String str_search = edtsearch.getText().toString().trim();
        Call<List<SanPhamMoi>> call = apiBanHang.seach(str_search);
        call.enqueue(new Callback<List<SanPhamMoi>>() {
            @Override
            public void onResponse(Call<List<SanPhamMoi>> call, Response<List<SanPhamMoi>> response) {
                if(response.isSuccessful()){
                    sanPhamMoiList = response.body();
                    adapterDt = new DienThoaiAdapter(getApplicationContext(), sanPhamMoiList);
                    recyclerView.setAdapter(adapterDt);
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamMoi>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"không kết nối được server",Toast.LENGTH_LONG).show();
                Log.e("Log","error : "+t.getMessage());
            }
        });
    }
}