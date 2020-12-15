package com.example.smartorder.adapter.admin;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.smartorder.fragment.admin.menu.MenuDrinkFragment;
import com.example.smartorder.fragment.admin.menu.MenuFoodFragment;
import com.example.smartorder.fragment.admin.menu.MenuOtherFragment;
import com.example.smartorder.model.menu.Menu;

import java.util.List;

public class TabMenuAdapter extends FragmentPagerAdapter {
    private String listTab[] = {"Đồ ăn", "Đồ uống", "Khác"};
    private MenuFoodFragment menuFoodFragment ;
    private MenuDrinkFragment menuDrinkFragment ;
    private MenuOtherFragment menuOtherFragment ;

    public TabMenuAdapter(@NonNull FragmentManager fm) {
        super(fm);
        menuFoodFragment = new MenuFoodFragment();
        menuDrinkFragment = new MenuDrinkFragment();
        menuOtherFragment = new MenuOtherFragment();

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return menuFoodFragment;
        } else if (position == 1) {
            return menuDrinkFragment;
        } else if (position == 2) {
            return menuOtherFragment;
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
