package com.example.shop_dacb_b.retrofit;

import com.example.shop_dacb_b.model.SanPhamMoi;
import com.example.shop_dacb_b.model.SanPhamMoiModel;
import com.example.shop_dacb_b.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiBanHang {

    @GET("api/Products/GetAll")
    Call<List<SanPhamMoi>> getProducts();

    @GET("api/Products/SearchByCategory/{categoryId}")
    Call<List<SanPhamMoi>> getProductByCate(@Path("categoryId") int loai);

    @POST("api/Accounts/Register")
    Call<Void> registerUser(@Body User user);

//    @FormUrlEncoded
//    @POST("api/Accounts/Register")
//    Call<Void> registerUser(
//            @Field("email") String email,
//            @Field("username") String username,
//            @Field("password") String password
//                            );

//    @FormUrlEncoded
    @POST("api/Accounts/Login")
    Call<Void> LoginUser(@Body User user);

//    @POST("reset.php")
//    @FormUrlEncoded
//    Observable<UserModel> resetPass(
//            @Field("email") String email
//    );

}
