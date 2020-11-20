package com.example.smartorder.adapter.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.smartorder.fragment.admin.menu.MenuDrinkFragment;
import com.example.smartorder.fragment.admin.menu.MenuFoodFragment;

public class TabMenuAdapter extends FragmentStatePagerAdapter {
    private String listTab[] = {"Đồ ăn", "Đồ uống"};
    private MenuDrinkFragment menuDrinkFragment;
    private MenuFoodFragment menuFoodFragment;

    public TabMenuAdapter(@NonNull FragmentManager fm) {
        super(fm);
        menuDrinkFragment = new MenuDrinkFragment();
        menuFoodFragment = new MenuFoodFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return menuFoodFragment;
        } else if (position == 1){
            return menuDrinkFragment;
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
