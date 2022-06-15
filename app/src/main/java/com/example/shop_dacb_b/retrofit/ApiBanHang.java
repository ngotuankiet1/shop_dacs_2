package com.example.shop_dacb_b.retrofit;

import com.example.shop_dacb_b.model.SanPhamMoi;
import com.example.shop_dacb_b.model.SanPhamMoiModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiBanHang {

    @GET("api/Products/GetAll")
    Call<List<SanPhamMoi>> getProducts();

    @GET("api/Products/SearchByCategory/{categoryId}")
    Call<List<SanPhamMoi>> getProductByCate(@Path("categoryId") int loai);
//
//    @POST("dangki.php")
//    @FormUrlEncoded
//    Observable<UserModel> dangki(
//            @Field("email") String email,
//            @Field("pass") String pass,
//            @Field("username") String username,
//            @Field("mobile") String mobile
//    );
//
//    @POST("dangnhap.php")
//    @FormUrlEncoded
//    Observable<UserModel> dangnhap(
//            @Field("email") String email,
//            @Field("pass") String pass
//    );
//
//    @POST("reset.php")
//    @FormUrlEncoded
//    Observable<UserModel> resetPass(
//            @Field("email") String email
//    );

}
