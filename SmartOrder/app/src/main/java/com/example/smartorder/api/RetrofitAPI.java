package com.example.smartorder.api;

import com.example.smartorder.model.Auth;
import com.example.smartorder.model.Table;
import com.example.smartorder.model.User;
import com.example.smartorder.model.menu.Menu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @FormUrlEncoded
    @POST("login")
    Call<Auth> checkLogin (@Field("phone") String phone,
                           @Field("password") String passWord);

    @GET("menu")
    Call<List<Menu>> getAllMenu();

    @GET("user")
    Call<List<User>> getAllUser();

    @GET("table")
    Call<List<Table>> getAllTable();

    @GET("bill")
    Call<List<Menu>> getAllBill();

}
