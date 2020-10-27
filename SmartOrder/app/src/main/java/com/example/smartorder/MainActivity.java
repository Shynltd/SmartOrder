package com.example.smartorder;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.smartorder.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText edtPhone;
    private EditText edtPassword;
    private CheckBox chkRemember;
    private ImageButton btnLogin;
    private String link = "http://192.168.1.3:2000/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitAPI.checkLogin(edtPhone.getText().toString().trim(),edtPassword.getText().toString().trim()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body().getStatus().equals("OK")){
                            if (response.body().getUser().getRole().equals("Admin")) {
                                Toast.makeText(MainActivity.this, response.body().getUser().getRole(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, response.body().getUser().getRole(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("onFailure", t.getMessage());
                    }
                });
            }
        });


    }

    private void initView() {
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        chkRemember = (CheckBox) findViewById(R.id.chkRemember);
        btnLogin = (ImageButton) findViewById(R.id.btnLogin);
    }
}