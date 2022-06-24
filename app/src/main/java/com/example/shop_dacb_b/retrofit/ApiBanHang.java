package com.example.shop_dacb_b.retrofit;

import com.example.shop_dacb_b.model.Donhang;
import com.example.shop_dacb_b.model.Item;
import com.example.shop_dacb_b.model.Order;
import com.example.shop_dacb_b.model.SanPhamDonHang;
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
    Call<String> LoginUser(@Body User user);

//    @POST("reset.php")
//    @FormUrlEncoded
//    Observable<UserModel> resetPass(
//            @Field("email") String email
//    );

    // Create order
    @POST("api/Orders/Create")
    Call<Boolean> createOrder(@Body Order order);

    //Get order
    @GET("api/Orders/GetByUserId/{userId}")
    Call<List<Donhang>> getOrder(@Path("userId") String groupId, @Header("Authorization") String authheader);

    //Get desc order
    @GET("api/Orders/GetById/{id}")
    Call<Donhang> getOrderDetails(@Path("id") int id, @Header("Authorization") String authheader);

//    Search products
    @GET("api/Products/SearchByName/{name}")
    Call<List<SanPhamMoi>> seach(@Path("name") String keySearch);

}
