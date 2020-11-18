package com.example.smartorder.fragment.menu;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartorder.R;
import com.example.smartorder.adapter.MenuDrinksAdapter;
import com.example.smartorder.adapter.MenuFoodAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.menu.ListDrink;
import com.example.smartorder.model.menu.ListFood;
import com.example.smartorder.model.menu.Menu;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuDrinkFragment extends Fragment {

    private RecyclerView rvListMenuDrink;
    private MenuDrinksAdapter menuDrinksAdapter;
    private RetrofitAPI retrofitAPI;
    private List<ListDrink> listDrinks;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_drink, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvListDrinks();
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        retrofitAPI.getAllMenu().enqueue(new Callback<Menu>() {
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {
                if (response.body() != null) {
                    List<ListDrink> drinks = response.body().getListDrink();
                    getListDrinksFromServer(drinks);

                }
            }

            @Override
            public void onFailure(Call<Menu> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

    }




    private void initView(View view) {
        rvListMenuDrink = (RecyclerView) view.findViewById(R.id.rvListMenuDrink);
    }


    private void rvListDrinks() {
        listDrinks = new ArrayList<>();
        menuDrinksAdapter = new MenuDrinksAdapter(listDrinks, getContext());
        rvListMenuDrink.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListMenuDrink.setHasFixedSize(true);
        rvListMenuDrink.setAdapter(menuDrinksAdapter);
    }

    private void getListDrinksFromServer(List<ListDrink> drinks) {
        for (int i = 0; i < drinks.size(); i++) {
            String id = drinks.get(i).getId();
            String name = drinks.get(i).getName();
            Integer price = drinks.get(i).getPrice();
            String image = drinks.get(i).getImage();
            String type = drinks.get(i).getType();
            Integer amount = drinks.get(i).getAmount();
            listDrinks.add(new ListDrink(id, name, price, image, type, amount));
            menuDrinksAdapter.notifyDataSetChanged();
        }
    }
}