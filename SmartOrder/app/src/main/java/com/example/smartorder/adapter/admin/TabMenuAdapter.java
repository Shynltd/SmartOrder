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


    public TabMenuAdapter(@NonNull FragmentManager fm) {
        super(fm);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.e( "getItem1: ", String.valueOf(getCount()));
        if (position == 0) {
            return new MenuFoodFragment();
        } else if (position == 1) {
            return new MenuDrinkFragment();
        } else if (position == 2) {
            return new MenuOtherFragment();
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
