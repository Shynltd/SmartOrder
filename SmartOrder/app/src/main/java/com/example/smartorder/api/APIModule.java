package com.example.smartorder.api;

import com.example.smartorder.constants.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIModule {
    public static Retrofit retrofit;

    static OkHttpClient okHttpClient() {
        OkHttpClient client =new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                    Request request =chain.request().newBuilder().addHeader("Authorization", Constants.TOKEN).build();
                return chain.proceed(request);
            }
        }).build();

        return client;
    }

    public static Retrofit getInstance() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .client(okHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
