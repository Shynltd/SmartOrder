package com.example.smartorder.fragment.staff;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.staff.BillPreviewAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.bill.BillOne;
import com.example.smartorder.model.callback.CallbackTalble;
import com.example.smartorder.model.table.Table;
import com.example.smartorder.support.Support;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillPreviewFragment extends Fragment implements CallbackTalble {


    private TextView tvTableCode, tvTotal;
    private Button btnSave;
    private Integer tableCode;
    private RetrofitAPI retrofitAPI;
    private RecyclerView rvList;
    private int total = 0;
    private BillPreviewAdapter billPreviewAdapter;
    private List<BillOne> billOneList ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        initView(view);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        tvTableCode.setText("Bàn số " + tableCode);
        initRecycleview();
        getListBillOneFromServer();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = 0;
                for (int i = 0; i < billOneList.size(); i++) {
                    String id = billOneList.get(i).getId();
                    Integer sl = billOneList.get(i).getSl();
                    Integer totalMoney = billOneList.get(i).getTotalMoney();
                    Log.e("sl: ", String.valueOf(sl));
                    Log.e("totalMoney: ", String.valueOf(id));
                    total += totalMoney;
                }
                tvTotal.setText("Tổng: " + Support.decimalFormat(total) + " VNĐ");
            }
        });
        return view;
    }

    private void getListBillOneFromServer() {
        retrofitAPI.getBillPreview(tableCode).enqueue(new Callback<List<BillOne>>() {
            @Override
            public void onResponse(Call<List<BillOne>> call, Response<List<BillOne>> response) {
                if (response.code() == 200) {
                    List<BillOne> billOnes = response.body();
                    for (int i = 0; i < billOnes.size(); i++) {
                        String billCode = billOnes.get(i).getBillCode();
                        String id = billOnes.get(i).getId();
                        String image = billOnes.get(i).getImage();
                        Integer sl = billOnes.get(i).getSl();
                        String name = billOnes.get(i).getName();
                        Integer price = billOnes.get(i).getPrice();
                        String type = billOnes.get(i).getType();
                        Integer totalMoney = billOnes.get(i).getTotalMoney();
                        billOneList.add(new BillOne(id,billCode, image, sl, name, price, type, totalMoney));
                        billPreviewAdapter.notifyDataSetChanged();
                        total += totalMoney;
                    }
                    tvTotal.setText("Tổng: " + Support.decimalFormat(total) + " VNĐ");
                } else {
                    Toast.makeText(getActivity(), "Không lấy được thông tin", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<BillOne>> call, Throwable t) {
                Log.e("onFailureBillPreview: ", t.getMessage());
            }
        });
    }

    private void initRecycleview() {
        billOneList = new ArrayList<>();
        billPreviewAdapter = new BillPreviewAdapter(getActivity(), billOneList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setHasFixedSize(true);
        rvList.setAdapter(billPreviewAdapter);
    }

    private void initView(View view) {
        tvTableCode = (TextView) view.findViewById(R.id.tvTableCode);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        rvList = (RecyclerView) view.findViewById(R.id.rvList);

    }

    @Override
    public void getTable(Table table) {
        this.tableCode = table.getTableCode();
    }
}