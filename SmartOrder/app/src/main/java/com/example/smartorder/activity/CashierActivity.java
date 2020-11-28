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
import com.example.smartorder.model.bill.Bill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashierActivity extends AppCompatActivity {

    private EditText edtSearch;
    private RecyclerView rvListBillUnpaid;
    private BillAdapter billAdapter;
    private List<Bill> billList;
    private RetrofitAPI retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        initView();
        initRecycle();
        getDataFromServer();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void getDataFromServer() {
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        retrofitAPI.getListUnpaid().enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                List<Bill> bills = response.body();
                for (int i = 0; i < bills.size(); i++) {
                    String dateBill = bills.get(i).getDateBill();
                    String id = bills.get(i).getId();
                    String billCode = bills.get(i).getBillCode();
                    String nameCashier = bills.get(i).getNameCashier();
                    Integer tableCode = bills.get(i).getTableCode();
                    Integer totalPrice = bills.get(i).getTotalPrice();
                    String status = bills.get(i).getStatus();
                    billList.add(new Bill(dateBill, id, billCode, nameCashier, tableCode, totalPrice, status));
                    billAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Log.e( "CashierActivity: ", t.getMessage());
            }
        });
    }

    private void initRecycle() {
        billList = new ArrayList<>();
        billAdapter = new BillAdapter(CashierActivity.this, billList, new BillAdapter.onItemClick() {
            @Override
            public void onClick(int pos) {

            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CashierActivity.this, 2);
        rvListBillUnpaid.setLayoutManager(gridLayoutManager);
        rvListBillUnpaid.setHasFixedSize(true);
        rvListBillUnpaid.setAdapter(billAdapter);
    }

    private void initView() {
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        rvListBillUnpaid = (RecyclerView) findViewById(R.id.rvListBillUnpaid);
    }
}