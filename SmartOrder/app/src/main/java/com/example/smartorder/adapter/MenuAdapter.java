package com.example.smartorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.model.menu.Menu;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {
    private List<Menu> menuList;
    private Context context;

    public MenuAdapter(List<Menu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_menu, parent, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class MenuHolder extends RecyclerView.ViewHolder {
        public MenuHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
