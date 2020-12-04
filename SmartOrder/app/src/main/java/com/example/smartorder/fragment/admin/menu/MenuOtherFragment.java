package com.example.smartorder.fragment.admin.menu;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.MenuDrinksAdapter;
import com.example.smartorder.model.menu.Menu;

import java.util.List;

public class MenuOtherFragment extends Fragment {
    private List<Menu> menuListOther;
    private MenuDrinksAdapter menuDrinksAdapter;
    private RecyclerView rvList;

    public MenuOtherFragment(List<Menu> menuListOther) {
        this.menuListOther = menuListOther;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_other, container, false);
        initView(view);
        initRecycle();
        Log.e( "MenuOtherFragment: ", String.valueOf(menuListOther.size()));
        return view;
    }

    private void initView(View view) {
        rvList = view.findViewById(R.id.rvList);
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