package com.example.shop_dacb_b.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shop_dacb_b.R;
import com.example.shop_dacb_b.adapter.DienThoaiAdapter;
import com.example.shop_dacb_b.model.SanPhamMoi;
import com.example.shop_dacb_b.model.User;
import com.example.shop_dacb_b.retrofit.ApiBanHang;
import com.example.shop_dacb_b.retrofit.RetrofitClient;
import com.example.shop_dacb_b.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKiActivity extends AppCompatActivity {
    EditText email, pass, repass, username;
    AppCompatButton btndangki;
    ApiBanHang apiBanHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        initControll();
    }

    private void initControll() {
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangki();
            }
        });
    }

    private void dangki() {
        String str_email = email.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_reapss = repass.getText().toString().trim();
        String str_username = username.getText().toString().trim();
        if (TextUtils.isEmpty(str_email)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_pass)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập pass", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_reapss)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập repass", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_username)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập username", Toast.LENGTH_SHORT).show();
        } else {
            if (str_pass.equals(str_reapss)) {
                User user = new User(str_email, str_username, str_pass);
                Call<Void> call = apiBanHang.registerUser(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Utils.user_current.setEmail(str_email);
                            Utils.user_current.setPassword(str_pass);
                            Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "đăng kí không thành công", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "không kết nối được server", Toast.LENGTH_LONG).show();
                        Log.e("Log", "error : " + t.getMessage());
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Email hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.repass);
        username = findViewById(R.id.username);
        btndangki = findViewById(R.id.btndangki);
    }

}