package com.example.smartorder.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.smartorder.R;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.login.Auth;
import com.example.smartorder.support.Support;

import java.net.URISyntaxException;

//import io.socket.client.IO;
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtPhone;
    private EditText edtPassword;
    private CheckBox chkRemember;
    private ImageButton btnLogin;
    private RetrofitAPI retrofitAPI;
    private ImageView imgLogo;
    private ConstraintLayout form;
    private boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initPermission();
        setAnimation();


        getSharedPreferences(getSharedPreferences("dataLogin", MODE_PRIVATE));
        validateForm();
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPhone.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập SDT", Toast.LENGTH_SHORT).show();
                    edtPhone.setError("Không bỏ trống");
                } else if(edtPassword.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
                    edtPassword.setError("Không bỏ trống");
                }else {
                    retrofitAPI.checkLogin(edtPhone.getText().toString().trim(), edtPassword.getText().toString().trim()).enqueue(new Callback<Auth>() {
                        @Override
                        public void onResponse(Call<Auth> call, Response<Auth> response) {
                            Auth auth = response.body();
                            checkLogin(auth);
                        }

                        @Override
                        public void onFailure(Call<Auth> call, Throwable t) {
                            Log.e("onFailure", t.getMessage());
                        }
                    });

                }
            }
        });


    }

    private void validateForm() {

    }

    private class Login extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private void checkLogin(Auth auth) {
        if (auth.getStatus().equalsIgnoreCase("OK")) {
            setSharedPreferences(getSharedPreferences("dataLogin", MODE_PRIVATE));
            Constants.TOKEN = auth.getToken();
            Constants.NameUser = auth.getName();
            Constants.AvatarUser = auth.getAvatar();
            Constants.idLogin = auth.getId();
            if (auth.getRole().equals("Admin")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else if (auth.getRole().equals("Cashier")) {
                startActivity(new Intent(LoginActivity.this, CashierActivity.class));
            } else if (auth.getRole().equals("Staff")) {
                startActivity(new Intent(LoginActivity.this, StaffActivity.class));
            }
        } else {
            Toast.makeText(LoginActivity.this, auth.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        chkRemember = (CheckBox) findViewById(R.id.chkRemember);
        btnLogin = (ImageButton) findViewById(R.id.btnLogin);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        form = (ConstraintLayout) findViewById(R.id.form);
    }

    private void setSharedPreferences(SharedPreferences sharedPreferences) {
        if (chkRemember.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("phone", edtPhone.getText().toString().trim());
            editor.putString("password", edtPassword.getText().toString().trim());
            editor.putBoolean("checked", true);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("phone");
            editor.remove("password");
            editor.remove("checked");
            editor.commit();
        }

    }

    private void getSharedPreferences(SharedPreferences sharedPreferences) {
        edtPhone.setText(sharedPreferences.getString("phone", ""));
        edtPhone.setSelection(sharedPreferences.getString("phone", "").length());
        edtPassword.setText(sharedPreferences.getString("password", ""));
        chkRemember.setChecked(sharedPreferences.getBoolean("checked", false));

    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setAnimation() {
        form.setAnimation(Support.setAnimation(LoginActivity.this, R.anim.form_login_translate_bottom_to_top, 1000, 0));
        imgLogo.setAnimation(Support.setAnimation(LoginActivity.this, R.anim.logo_translate_top_to_bottom, 1000, 0));
        btnLogin.setAnimation(Support.setAnimation(LoginActivity.this, R.anim.btn_login_alpha_hide_to_show, 1000, 1100));
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
            return;
        }
        exit = true;
        Toast.makeText(this, "Ấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exit = false;
            }
        },2000);
    }
}