package com.example.smartorder.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartorder.R;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.cashier.CashierFragment;
import com.example.smartorder.support.Support;

public class CashierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        Support.hideSoftKeyboard(CashierActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.frq, new CashierFragment(), Constants.fragmentCashier).commit();
    }

    @Override
    public void onBackPressed() {

    }
}