package com.example.smartorder.fragment.admin.menu;

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
import com.example.smartorder.adapter.admin.MenuFoodAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.menu.ListFood;
import com.example.smartorder.model.menu.Menu;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFoodFragment extends Fragment {

    private RecyclerView rvListMenuFood;
    private MenuFoodAdapter menuFoodAdapter;
    private RetrofitAPI retrofitAPI;
    private List<ListFood> listFoods;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_food, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvListFood();
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        retrofitAPI.getAllMenu().enqueue(new Callback<Menu>() {
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {
                if (response.body() != null) {
                    List<ListFood> foods = response.body().getListFood();
                    getListFoodFromServer(foods);

                }
            }

            @Override
            public void onFailure(Call<Menu> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }



    private void initView(View view) {
        rvListMenuFood = (RecyclerView) view.findViewById(R.id.rvListMenuFood);
    }

    private void rvListFood() {
        listFoods = new ArrayList<>();
        menuFoodAdapter = new MenuFoodAdapter(listFoods, getContext());
        rvListMenuFood.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListMenuFood.setHasFixedSize(true);
        rvListMenuFood.setAdapter(menuFoodAdapter);
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