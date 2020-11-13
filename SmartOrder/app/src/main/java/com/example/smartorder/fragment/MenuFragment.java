package com.example.smartorder.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.MenuDrinksAdapter;
import com.example.smartorder.adapter.MenuFoodAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.menu.ListDrink;
import com.example.smartorder.model.menu.ListFood;
import com.example.smartorder.model.menu.Menu;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {

    private RecyclerView rvListMenuFood;
    private RecyclerView rvListMenuDrink;
    private MenuDrinksAdapter menuDrinksAdapter;
    private MenuFoodAdapter menuFoodAdapter;
    private RetrofitAPI retrofitAPI;
    private FloatingActionButton fabAddMenu;
    private List<ListDrink> listDrinks;
    private List<ListFood> listFoods;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvListFood(linearLayoutManager);
        rvListDrinks(linearLayoutManager);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        retrofitAPI.getAllMenu().enqueue(new Callback<Menu>() {
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {
                if (response.body() != null) {
                    List<ListDrink> drinks = response.body().getListDrink();
                    List<ListFood> foods = response.body().getListFood();
                    getListDrinksFromServer(drinks);
                    getListFoodFromServer(foods);
                }
            }

            @Override
            public void onFailure(Call<Menu> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
        fabAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddMenu();

            }
        });
    }

    private void dialogAddMenu() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        View alert = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_menu, null);
        alertDialog.setView(alert);
        alertDialog.setTitle("Thêm mới món ăn");
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    private void initView(View view) {
        rvListMenuFood = (RecyclerView) view.findViewById(R.id.rvListMenuFood);
        rvListMenuDrink = (RecyclerView) view.findViewById(R.id.rvListMenuDrink);
        fabAddMenu = (FloatingActionButton) view.findViewById(R.id.fabAddMenu);
    }

    private void rvListFood(LinearLayoutManager linearLayoutManager) {
        listFoods = new ArrayList<>();
        menuFoodAdapter = new MenuFoodAdapter(listFoods, getContext());
        rvListMenuFood.setLayoutManager(linearLayoutManager);
        rvListMenuFood.setHasFixedSize(true);
        rvListMenuFood.setAdapter(menuFoodAdapter);
    }

    private void rvListDrinks(LinearLayoutManager linearLayoutManager) {
        listDrinks = new ArrayList<>();
        menuDrinksAdapter = new MenuDrinksAdapter(listDrinks, getContext());
        rvListMenuDrink.setLayoutManager(linearLayoutManager);
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

    private void getListFoodFromServer(List<ListFood> foods) {
        for (int j = 0; j < foods.size(); j++) {
            String id = foods.get(j).getId();
            String name = foods.get(j).getName();
            Integer price = foods.get(j).getPrice();
            String image = foods.get(j).getImage();
            String type = foods.get(j).getType();
            listFoods.add(new ListFood(id, name, price, image, type));
            menuFoodAdapter.notifyDataSetChanged();
        }
    }

}