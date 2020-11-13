package com.example.smartorder.api;

import com.example.smartorder.model.Auth;
import com.example.smartorder.model.ServerResponse;
import com.example.smartorder.model.Table;
import com.example.smartorder.model.User;
import com.example.smartorder.model.menu.Menu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface RetrofitAPI {
    //Login
    @FormUrlEncoded
    @POST("login")
    Call<Auth> checkLogin (@Field("phone") String phone,
                           @Field("password") String passWord);

    //Menu
    @GET("menu")
    Call<Menu> getAllMenu();

    //User
    @GET("user")
    Call<List<User>> getAllUser();

    //Table
    @GET("table")
    Call<List<Table>> getAllTable();

    @FormUrlEncoded
    @POST("table/create")
    Call<ServerResponse> createTable (@Field("tableCode") int tableCode,
                                      @Field("tableSeats") int tableSeats);

    @PUT("table/update/{id}")
    Call<ServerResponse> updateTable (@Part("_id") String id,
                                      @Body Table table);
    @DELETE("table/delete/{id}")
    Call<ServerResponse> deleteTable (@Part("_id") String id);

    //Bill
    @GET("bill")
    Call<List<Menu>> getAllBill();


}
