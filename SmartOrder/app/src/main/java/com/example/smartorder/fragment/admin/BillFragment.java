package com.example.smartorder.fragment.admin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.BillAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.bill.Bill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillFragment extends Fragment {

    private RecyclerView rvListBill;
    private List<Bill> billList;
    private BillAdapter billAdapter;
    private RetrofitAPI retrofitAPI;
    private EditText edtSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        initView(view);
        initRecycleView();
        callResponse();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;
    }

    private void filter(String s) {
        List<Bill> billFilter = new ArrayList<>();
        for(Bill bill : billList){
            if(bill.getBillCode().toLowerCase().contains(s.toLowerCase())){
                billFilter.add(bill);
            }
        }
        billAdapter.filterList(billFilter,getActivity());
        billAdapter.notifyDataSetChanged();
    }

    private void callResponse() {
        retrofitAPI.getListPaid().enqueue(new Callback<List<Bill>>() {
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
                    String nameOrder = bills.get(i).getNameOrder();
                    Integer discount = bills.get(i).getDiscount();
                    billList.add(new Bill(dateBill, id, billCode, nameCashier, tableCode, totalPrice, status,nameOrder,discount));
                    billAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi hệ thống" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecycleView() {
        billList = new ArrayList<>();
        billAdapter = new BillAdapter(getContext(), billList, new BillAdapter.onItemClick() {
            @Override
            public void onClick(int pos) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.admin_fragment_view_bill_scale_enter,0).add(R.id.frq, new ViewBillFragment(billList.get(pos)), Constants.fragmentViewBill).commit();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        rvListBill.setLayoutManager(gridLayoutManager);
        rvListBill.setHasFixedSize(true);
        rvListBill.setAdapter(billAdapter);
    }

    private void initView(View view) {
        rvListBill = (RecyclerView) view.findViewById(R.id.rvListBill);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        edtSearch = view.findViewById(R.id.edtSearch);
    }
}