package com.example.smartorder.api;

import com.example.smartorder.model.login.Auth;
import com.example.smartorder.model.menu.MenuOrder;
import com.example.smartorder.model.response.ServerResponse;
import com.example.smartorder.model.table.Table;
import com.example.smartorder.model.user.User;
import com.example.smartorder.model.menu.Menu;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitAPI {
    //Login
    @FormUrlEncoded
    @POST("login")
    Call<Auth> checkLogin(@Field("phone") String phone,
                          @Field("password") String passWord);

    //Menu
    @GET("menu")
    Call<Menu> getAllMenu();

    @Multipart
    @POST("menu/create")
    Call<ServerResponse> createFood(
            @Part("name") String name,
            @Part("price") Integer price,
            @Part("amount") Integer amount,
            @Part("type") String type,
            @Part MultipartBody.Part file
    );
    @DELETE("menu/delete/{id}")
    Call<ServerResponse> deleteDrink(@Path("id") String id);

    @DELETE("menu/delete/{id}")
    Call<ServerResponse> deleteFood(@Path("id") String id);
    //User
    @GET("user")
    Call<List<User>> getAllUser();

    @Multipart
    @POST("user/create")
    Call<ServerResponse> createUser(
            @Part("fullName") String fullName,
            @Part("phone") String phone,
            @Part("soCMND") Integer cmnd,
            @Part("age") Integer age,
            @Part("address") String address,
            @Part("role") String role,
            @Part MultipartBody.Part file
    );
    @PUT("user/update/{id}")
    Call<ServerResponse> updateUser(@Path("id") String id,
                                     @Body User user);
    @DELETE("user/delete/{id}")
    Call<ServerResponse> deleteUser(@Path("id") String id);


    //Table
    @GET("table")
    Call<List<Table>> getAllTable();

    @FormUrlEncoded
    @POST("table/create")
    Call<ServerResponse> createTable(@Field("tableCode") int tableCode,
                                     @Field("tableSeats") int tableSeats);

    @PUT("table/update/{id}")
    Call<ServerResponse> updateTable(@Path("id") String id,
                                     @Body Table table);

    @DELETE("table/delete/{id}")
    Call<ServerResponse> deleteTable(@Path("id") String id);

    //Bill
    @GET("bill")
    Call<List<Menu>> getAllBill();

    //staff
    @GET("menus")
    Call<List<MenuOrder>> getAllMenuOrder();
    @FormUrlEncoded
    @POST("bill/create")
    Call<ServerResponse> order(@Field("tableCodes") String tableCodes);

}
