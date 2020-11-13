package com.example.smartorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.model.menu.ListDrink;

import java.util.List;

public class MenuDrinksAdapter extends RecyclerView.Adapter<MenuDrinksAdapter.MenuHolder> {
    private List<ListDrink> listDrinks;
    private Context context;


    public MenuDrinksAdapter(List<ListDrink> listDrinks, Context context) {
        this.listDrinks = listDrinks;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_menu_drinks, parent, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        Glide.with(context).load(listDrinks.get(position).getImage()).into(holder.imgLogo);
        holder.tvNameMenu.setText(listDrinks.get(position).getName());
        holder.tvPriceMenu.setText(listDrinks.get(position).getPrice()+"");
        holder.tvAmountMenu.setText(listDrinks.get(position).getAmount()+"");
    }

    @Override
    public int getItemCount() {
        if (listDrinks == null) return 0;
        return listDrinks.size();
    }



    public static class MenuHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView tvNameMenu;
        private TextView tvPriceMenu;
        private TextView tvAmountMenu;

        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = (ImageView) itemView.findViewById(R.id.imgLogo);
            tvNameMenu = (TextView) itemView.findViewById(R.id.tvNameMenu);
            tvPriceMenu = (TextView) itemView.findViewById(R.id.tvPriceMenu);
            tvAmountMenu = (TextView) itemView.findViewById(R.id.tvAmountMenu);
        }
    }

}
