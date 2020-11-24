package com.example.smartorder.fragment.staff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.staff.MenuOrderAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.menu.MenuOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFoodOrderFragment extends Fragment {


    private TextView tvTableCodes;
    private RecyclerView rvListFoodOrder;
    private LinearLayout lnButton;
    private Button btnCancel;
    private Button btnOrder;
    private RetrofitAPI retrofitAPI;
    private List<MenuOrder> menuOrders;
    private MenuOrderAdapter menuOrderAdapter;
    private int tabldeCodes;

    public ListFoodOrderFragment(int tabldeCodes) {
        this.tabldeCodes = tabldeCodes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_food_order, container, false);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Hello",Toast.LENGTH_SHORT).show();
            }
        });
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvTableCodes.setText("Bàn số "+tabldeCodes+":");
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        menuOrders = new ArrayList<>();
        menuOrderAdapter = new MenuOrderAdapter(menuOrders, getContext());
        rvListFoodOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListFoodOrder.setHasFixedSize(true);
        rvListFoodOrder.setAdapter(menuOrderAdapter);
        retrofitAPI.getAllMenuOrder().enqueue(new Callback<List<MenuOrder>>() {
            @Override
            public void onResponse(Call<List<MenuOrder>> call, Response<List<MenuOrder>> response) {
                List<MenuOrder> orders = response.body();
                getListMenuOrder(orders);

            }

            @Override
            public void onFailure(Call<List<MenuOrder>> call, Throwable t) {

            }
        });

    }

    private void getListMenuOrder(List<MenuOrder> orders) {
        for (int i = 0; i < orders.size(); i++) {
            String id = orders.get(i).getId();
            String name = orders.get(i).getName();
            Integer price = orders.get(i).getPrice();
            String image = orders.get(i).getImage();
            String type = orders.get(i).getType();
            Integer amount = orders.get(i).getAmount();
            menuOrders.add(new MenuOrder(id, name, price, image, type, amount));
            menuOrderAdapter.notifyDataSetChanged();
        }
    }

    private void initView(View view) {
        tvTableCodes = (TextView) view.findViewById(R.id.tvTableCodes);
        rvListFoodOrder = (RecyclerView) view.findViewById(R.id.rvListFoodOrder);
        lnButton = (LinearLayout) view.findViewById(R.id.lnButton);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnOrder = (Button) view.findViewById(R.id.btnOrder);
    }
}