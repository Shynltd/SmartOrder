package com.example.smartorder.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartorder.R;
import com.example.smartorder.fragment.admin.MainFragment;
import com.example.smartorder.support.Support;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Support.replaceFragment(getSupportFragmentManager(), R.id.frq, new MainFragment(), false, 0, 0);
    }

    @Override
    public void onBackPressed() {

    }
}