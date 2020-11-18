package com.example.smartorder.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.smartorder.R;
import com.example.smartorder.adapter.MenuDrinksAdapter;
import com.example.smartorder.adapter.MenuFoodAdapter;
import com.example.smartorder.adapter.TabMenuAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.menu.ListDrink;
import com.example.smartorder.model.menu.ListFood;
import com.google.android.material.tabs.TabLayout;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

public class MenuFragment extends Fragment {

    private RetrofitAPI retrofitAPI;
    private TabLayout tabMenu;
    private ViewPager vpMenu;
    private FloatingActionButton fabAddMenu;


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
        vpMenu.setAdapter(new TabMenuAdapter(getFragmentManager()));
        tabMenu.setupWithViewPager(vpMenu);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
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
        tabMenu = (TabLayout) view.findViewById(R.id.tabMenu);
        vpMenu = (ViewPager) view.findViewById(R.id.vpMenu);
        fabAddMenu = (FloatingActionButton) view.findViewById(R.id.fabAddMenu);
    }
}