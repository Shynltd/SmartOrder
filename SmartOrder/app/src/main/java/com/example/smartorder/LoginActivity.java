package com.example.smartorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartorder.activity.MainActivity;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.Auth;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtPhone;
    private EditText edtPassword;
    private CheckBox chkRemember;
    private ImageButton btnLogin;
    private RetrofitAPI retrofitAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        getSharedPreferences(getSharedPreferences("dataLogin", MODE_PRIVATE));
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                retrofitAPI.checkLogin(edtPhone.getText().toString().trim(), edtPassword.getText().toString().trim()).enqueue(new Callback<Auth>() {
                    @Override
                    public void onResponse(Call<Auth> call, Response<Auth> response) {
                        Auth auth = response.body();
                        if (auth.getStatus().equalsIgnoreCase("OK")) {
                            setSharedPreferences(getSharedPreferences("dataLogin", MODE_PRIVATE));
                            Constants.TOKEN = auth.getToken();
                            Constants.NameUser = auth.getName();
                            if (auth.getAvatar() != null) {
                                Constants.AvatarUser = auth.getAvatar();
                            }
                            if (auth.getRole().equals("Admin")) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else if (auth.getRole().equals("Cashier")) {
                                Toast.makeText(LoginActivity.this, "Cashier", Toast.LENGTH_SHORT).show();
                            } else if (auth.getRole().equals("Staff")) {
                                Toast.makeText(LoginActivity.this, "Staff", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, auth.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Auth> call, Throwable t) {
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
        edtPassword.setText(sharedPreferences.getString("password", ""));
        chkRemember.setChecked(sharedPreferences.getBoolean("checked", false));

    }
}