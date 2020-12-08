package com.example.smartorder.fragment.admin.menu;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.MenuDrinksAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.menu.Menu;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuOtherFragment extends Fragment {
    private List<Menu> menuListOther;
    private MenuDrinksAdapter menuDrinksAdapter;
    private RecyclerView rvList;
    private EditText edtSearch;
    private RetrofitAPI retrofitAPI;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_other, container, false);
        initView(view);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        menuListOther = new ArrayList<>();
        initRecycle();
        getAllMenuFromServer();
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

    private void initView(View view) {
        rvList = view.findViewById(R.id.rvList);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
    }

    private void getAllMenuFromServer() {
        retrofitAPI.getAllMenu().enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                List<Menu> menus = response.body();
                for (int i = 0; i < menus.size(); i++) {
                    String id = menus.get(i).getId();
                    String name = menus.get(i).getName();
                    Integer price = menus.get(i).getPrice();
                    String image = menus.get(i).getImage();
                    String type = menus.get(i).getType();
                    boolean status = menus.get(i).getStatus();
                    if (type.equals("Other")) {
                        menuListOther.add(new Menu(id, name, price, image, type, status));
                        menuDrinksAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                Log.e("onFailureMenuFragment", t.getMessage());
            }
        });
    }
    private void filter(String s) {
        List<Menu> menuDrinkFilter = new ArrayList<>();
        for (Menu menu : menuListOther) {
            if (menu.getName().toLowerCase().contains(s.toLowerCase())) {
                menuDrinkFilter.add(menu);
            }
        }
        menuDrinksAdapter.filterList(menuDrinkFilter, getActivity());
        menuDrinksAdapter.notifyDataSetChanged();
    }
    private void initRecycle() {
        menuDrinksAdapter = new MenuDrinksAdapter(menuListOther, getActivity(), new MenuDrinksAdapter.OnClickListener() {
            @Override
            public void deleteDrink(int position, String id) {

            }

            @Override
            public void updateDrink(int position) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setHasFixedSize(true);
        rvList.setAdapter(menuDrinksAdapter);
        menuDrinksAdapter.notifyDataSetChanged();
    }
}