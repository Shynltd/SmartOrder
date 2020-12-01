package com.example.smartorder.fragment.admin.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.MenuFoodAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.menu.ListFood;
import com.example.smartorder.model.menu.Menu;
import com.example.smartorder.model.response.ServerResponse;

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
        menuFoodAdapter = new MenuFoodAdapter(listFoods, getContext(), new MenuFoodAdapter.OnClickListener() {
            @Override
            public void deleteFood(int position, String id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn xóa món ăn"+listFoods.get(position).getName()+" không?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                retrofitAPI.deleteDrink(id).enqueue(new Callback<ServerResponse>() {
                                    @Override
                                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.detach(MenuFoodFragment.this).attach(MenuFoodFragment.this).commit();
                                    }

                                    @Override
                                    public void onFailure(Call<ServerResponse> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .create().show();
            }

            @Override
            public void updateFood(int position, List<ListFood> listFoods) {

                dialogUpdateFoods();
            }
        });
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
    private void dialogUpdateFoods(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        View alert = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_menu, null);
        alertDialog.setView(alert);
        alertDialog.setTitle("Chỉnh sửa thông tin Món ăn");
        alertDialog.setCancelable(false);

        Button btnCancel = alert.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}