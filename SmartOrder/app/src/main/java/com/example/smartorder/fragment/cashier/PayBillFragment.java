package com.example.smartorder.fragment.cashier;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.cashier.BillOneAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.admin.TableFragment;
import com.example.smartorder.model.bill.BillOne;
import com.example.smartorder.model.response.ServerResponse;
import com.example.smartorder.support.Support;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayBillFragment extends Fragment {

    private List<BillOne> billOneList;
    private BillOneAdapter billOneAdapter;
    private TextView tvBillCode;
    private TextView tvTableCode;
    private RecyclerView rvListBillOne;
    private TextView tvTongtien;
    private Button btnCancel;
    private Button btnPay;
    private RetrofitAPI retrofitAPI;
    private String billCode = "";
    private Integer tableCode;
    private Integer totalMoney;

    public PayBillFragment(String billCode, Integer tableCode, Integer totalMoney) {
        this.billCode = billCode;
        this.tableCode = tableCode;
        this.totalMoney = totalMoney;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_bill, container, false);
        initView(view);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        initRecycleView();
        getBillOne();
        tvBillCode.setText("Mã hóa đơn: " + billCode);
        tvTableCode.setText("Bàn số " + tableCode);
        tvTongtien.setText("Total: " + Support.decimalFormat(totalMoney) + " VNĐ");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelPay();
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay();
            }
        });
        return view;
    }

    private void pay() {
        retrofitAPI.payBill(billCode, Constants.NameUser).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Fragment payBillFrament = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.fragmentPayBill);
                    if (payBillFrament != null) {
                        getActivity().getSupportFragmentManager().beginTransaction().remove(payBillFrament).commit();
                    }
                    Fragment cashierFragment = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.cashierFragmet);
                    if (cashierFragment != null) {
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.detach(cashierFragment).attach(cashierFragment).commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }
        });
    }

    private void cancelPay() {
        Fragment payBillFrament = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.fragmentPayBill);
        if (payBillFrament != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(payBillFrament).commit();
        }
    }

    private void initRecycleView() {
        billOneList = new ArrayList<>();
        billOneAdapter = new BillOneAdapter(getActivity(), billOneList);
        rvListBillOne.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListBillOne.setHasFixedSize(true);
        rvListBillOne.setAdapter(billOneAdapter);
    }

    private void getBillOne() {
        retrofitAPI.getDetailBill(billCode).enqueue(new Callback<List<BillOne>>() {
            @Override
            public void onResponse(Call<List<BillOne>> call, Response<List<BillOne>> response) {
                List<BillOne> billOnes = response.body();
                for (int i = 0; i < billOnes.size(); i++) {
                    String billCode = billOnes.get(i).getBillCode();
                    String image = billOnes.get(i).getImage();
                    Integer sl = billOnes.get(i).getSl();
                    String name = billOnes.get(i).getName();
                    Integer price = billOnes.get(i).getPrice();
                    String type = billOnes.get(i).getType();
                    Integer totalMoney = billOnes.get(i).getTotalMoney();
                    billOneList.add(new BillOne(billCode, image, sl, name, price, type, totalMoney));
                    billOneAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BillOne>> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View view) {
        tvBillCode = (TextView) view.findViewById(R.id.tvBillCode);
        tvTableCode = (TextView) view.findViewById(R.id.tvTableCode);
        rvListBillOne = (RecyclerView) view.findViewById(R.id.rvListBillOne);
        tvTongtien = (TextView) view.findViewById(R.id.tvTongtien);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnPay = (Button) view.findViewById(R.id.btnPay);

    }
}