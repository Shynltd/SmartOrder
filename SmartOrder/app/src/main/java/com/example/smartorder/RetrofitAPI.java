package com.example.smartorder;

import com.example.smartorder.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @FormUrlEncoded
    @POST("login")
    Call<User> checkLogin (@Field("email") String phone,
                           @Field("password") String passWord);
}
