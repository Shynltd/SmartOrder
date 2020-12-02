package com.example.smartorder.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.BillAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.admin.MainFragment;
import com.example.smartorder.fragment.cashier.CashierFragment;
import com.example.smartorder.model.bill.Bill;
import com.example.smartorder.support.Support;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        getSupportFragmentManager().beginTransaction().replace(R.id.frq, new CashierFragment(), Constants.cashierFragmet).commit();
    }

    @Override
    public void onBackPressed() {

    }
}