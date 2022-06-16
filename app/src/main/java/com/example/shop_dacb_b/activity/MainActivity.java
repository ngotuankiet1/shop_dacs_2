package com.example.shop_dacb_b.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.shop_dacb_b.R;
import com.example.shop_dacb_b.adapter.LoaiSpAdapter;
import com.example.shop_dacb_b.adapter.SanPhamMoiAdapter;
import com.example.shop_dacb_b.model.LoaiSp;
import com.example.shop_dacb_b.model.SanPhamMoi;
import com.example.shop_dacb_b.model.SanPhamMoiModel;
import com.example.shop_dacb_b.retrofit.ApiBanHang;
import com.example.shop_dacb_b.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.example.shop_dacb_b.retrofit.RetrofitClient;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spAdapter;
    ApiBanHang apiBanHang;
    NotificationBadge badge;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Anhxa();
        ActionBar();
        if(isConnected(this)){
            ActionViewFlipper();
            getLoaiSanPham();
            getSpMoi();
            getEventClick();
        }else{
            Toast.makeText(getApplicationContext(),"không co internet",Toast.LENGTH_LONG).show();
        }
    }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent dienthoai = new Intent(getApplicationContext(),DienThoaiActivity.class);
                        dienthoai.putExtra("loai",2);
                        startActivity(dienthoai);
                        break;
                    case 2:
                        Intent laptop = new Intent(getApplicationContext(),DienThoaiActivity.class);
                        laptop.putExtra("loai",3);
                        startActivity(laptop);
                        break;
                    case 3:
                        Intent thongtin = new Intent(getApplicationContext(),ThongTinActivity.class);
                        startActivity(thongtin);
                        break;
                    case 4:
                        Intent lienhe = new Intent(getApplicationContext(),LienHeActivity.class);
                        startActivity(lienhe);
                        break;
                    case 5:
                        finish();
                        break;

                }
            }
        });
    }

    private void getSpMoi() {
        Call<List<SanPhamMoi>> call = apiBanHang.getProducts();
        call.enqueue(new Callback<List<SanPhamMoi>>() {
            @Override
            public void onResponse(Call<List<SanPhamMoi>> call, Response<List<SanPhamMoi>> response) {
                mangSpMoi = response.body();
//                Log.e("products",mangSpMoi.size()+"");
                spAdapter = new SanPhamMoiAdapter(getApplicationContext(),mangSpMoi);
                recyclerViewManHinhChinh.setAdapter(spAdapter);
            }

            @Override
            public void onFailure(Call<List<SanPhamMoi>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"không kết nối được server",Toast.LENGTH_LONG).show();
                Log.e("products","error : "+t.getMessage());
            }
        });
    }

    private void getLoaiSanPham() {
        mangloaisp.add(new LoaiSp("Trang chủ","https://ngochieu.name.vn/img/home.png"));
        mangloaisp.add(new LoaiSp("Điện thoại","https://ngochieu.name.vn/img/mobile.png"));
        mangloaisp.add(new LoaiSp("Laptop","https://ngochieu.name.vn/img/laptop.png"));
        mangloaisp.add(new LoaiSp("Thông tin","https://ngochieu.name.vn/img/info.png"));
        mangloaisp.add(new LoaiSp("Liên hệ","https://ngochieu.name.vn/img/contact.png"));
        mangloaisp.add(new LoaiSp("Đăng xuất","https://png.pngtree.com/png-vector/20190415/ourlarge/pngtree-vector-logout-icon-png-image_939706.jpg"));
    }


    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/Files/2020/04/28/1252401/maxresdefault_800x450.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/Files/2017/03/09/958798/son-tung-oppo-f3_800x450.jpg");
        mangquangcao.add("https://cdn.sforum.vn/sforum/wp-content/uploads/2020/08/OPPO-F17-1.jpg");
        for(int i = 0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setInAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        toolbar = findViewById(R.id.toobarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
        //==============================================không đụng
        loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(),mangloaisp);
        listViewManHinhChinh.setAdapter(loaiSpAdapter);

        if(Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else{
            int totalItem = 0;
            for(int i =0;i<Utils.manggiohang.size();i++){
                totalItem = totalItem+ Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for(int i =0;i<Utils.manggiohang.size();i++){
            totalItem = totalItem+ Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobie = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi != null && wifi.isConnected() || (mobie != null && mobie.isConnected())){
            return true;
        }else{
            return false;
        }
    }
}