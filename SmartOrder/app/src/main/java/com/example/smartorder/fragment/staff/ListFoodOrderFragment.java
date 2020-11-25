package com.example.smartorder.fragment.staff;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.staff.MenuOrderAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.callback.CallbackTalble;
import com.example.smartorder.model.menu.MenuOrder;
import com.example.smartorder.model.table.Table;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFoodOrderFragment extends Fragment implements CallbackTalble {


    private TextView tvTableCodes;
    private RecyclerView rvListFoodOrder;
    private LinearLayout lnButton;
    private Button btnCancel;
    private Button btnOrder;
    private EditText edtSearch;
    private RetrofitAPI retrofitAPI;
    private List<MenuOrder> menuOrders;
    private MenuOrderAdapter menuOrderAdapter;
    private int tabldeCodes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_food_order, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvTableCodes.setText("Bàn số " + tabldeCodes);
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
    }

    private void filter(String toString) {
        List<MenuOrder> menuOrderFilter = new ArrayList<>();
        for (MenuOrder order : menuOrders){
            if (order.getName().toLowerCase().contains(toString.toLowerCase())){
                menuOrderFilter.add(order);
            }
        }
        menuOrderAdapter.filterList(menuOrderFilter, getContext());
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
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
    }

    @Override
    public void getTable(Table table) {
        this.tabldeCodes = table.getTableCode();
    }

}