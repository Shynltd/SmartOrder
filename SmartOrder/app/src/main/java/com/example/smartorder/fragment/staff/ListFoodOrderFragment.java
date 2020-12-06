package com.example.smartorder.fragment.staff;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.staff.MenuOrderAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.callback.CallbackTalble;
import com.example.smartorder.model.menu.ListMenuOrder;
import com.example.smartorder.model.menu.MenuOrder;
import com.example.smartorder.model.response.ServerResponse;
import com.example.smartorder.model.table.Table;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFoodOrderFragment extends Fragment implements CallbackTalble {
    private TextView tvTableCode;
    private RecyclerView rvListFoodOrder;
    private Button btnCancel;
    private Button btnOrder;
    private EditText edtSearch;
    private RetrofitAPI retrofitAPI;
    private List<MenuOrder> menuOrders;
    private MenuOrderAdapter menuOrderAdapter;
    private int tableCode;
    private View view;

    private List<MenuOrder> listMenuOder = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_food_order, container, false);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initView();
        init();
        return view;
    }

    private void init() {
        tvTableCode.setText("Bàn số " + tableCode);
        menuOrders = new ArrayList<>();
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        rvListFoodOrder.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        rvListFoodOrder.setHasFixedSize(true);
        menuOrderAdapter = new MenuOrderAdapter(ListFoodOrderFragment.this, menuOrders);
        rvListFoodOrder.setAdapter(menuOrderAdapter);
        retrofitAPI.getAllMenuOrder().enqueue(new Callback<List<MenuOrder>>() {
            @Override
            public void onResponse(Call<List<MenuOrder>> call, Response<List<MenuOrder>> response) {
                List<MenuOrder> orders = response.body();
                for (int i=0;i<orders.size();i++){
                    String id = orders.get(i).getId();
                    String name= orders.get(i).getName();
                    Integer price= orders.get(i).getPrice();
                    String image= orders.get(i).getImage();
                    String type= orders.get(i).getType();
                    boolean status = orders.get(i).getStatus();

                        menuOrders.add(new MenuOrder(id,name,price,image,type,status));
                        menuOrderAdapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onFailure(Call<List<MenuOrder>> call, Throwable t) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.fragmentListFood);
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(0, R.anim.list_food_top_to_bottom)
                            .remove(fragment)
                            .commit();
                }
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listMenuOder.clear();
                for (int i = 0; i < menuOrders.size(); i++) {
                    if (menuOrders.get(i).isChecked()) {
                        listMenuOder.add(menuOrders.get(i));
                    }
                }
                ListMenuOrder menuOrder = new ListMenuOrder();
                menuOrder.setTableCodes(tableCode);
                menuOrder.setMenuOrders(listMenuOder);

                retrofitAPI.createBill(menuOrder).enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        if (response.code() == 200) {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.fragmentListFood);
                            if (fragment != null) {
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(0, R.anim.list_food_top_to_bottom)
                                        .remove(fragment)
                                        .commit();
                                Fragment listTableStaff = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.fragmentTableStaff);
                                if (listTableStaff != null) {
                                    getActivity().getSupportFragmentManager().beginTransaction().detach(listTableStaff).attach(listTableStaff).commit();
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        Log.e("onFailure: ", t.getMessage());
                    }
                });
            }
        });
    }


    private void filter(String toString) {
        List<MenuOrder> menuOrderFilter = new ArrayList<>();
        for (MenuOrder order : menuOrders) {
            if (order.getName().toLowerCase().contains(toString.toLowerCase())) {
                menuOrderFilter.add(order);
            }
        }
        menuOrderAdapter.filterList(menuOrderFilter, ListFoodOrderFragment.this);
        menuOrderAdapter.notifyDataSetChanged();
    }


    private void initView() {
        tvTableCode = (TextView) view.findViewById(R.id.tvTableCodes);
        rvListFoodOrder = (RecyclerView) view.findViewById(R.id.rvListFoodOrder);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnOrder = (Button) view.findViewById(R.id.btnOrder);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
    }

    @Override
    public void getTable(Table table) {
        this.tableCode = table.getTableCode();
    }
}