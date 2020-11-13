package com.example.smartorder.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.menu.ListDrink;
import com.example.smartorder.model.menu.ListFood;

import java.util.List;

public class MenuFoodAdapter extends RecyclerView.Adapter<MenuFoodAdapter.MenuHolder> {
    private List<ListFood> listFoods;
    private Context context;


    public MenuFoodAdapter(List<ListFood> listFoods, Context context) {
        this.listFoods = listFoods;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_menu_food, parent, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        Glide.with(context).load(Constants.LINK+listFoods.get(position).getImage()).into(holder.imgLogo);

        holder.tvNameMenu.setText(listFoods.get(position).getName());
        holder.tvPriceMenu.setText(listFoods.get(position).getPrice()+"");
    }

    @Override
    public int getItemCount() {
        if (listFoods == null) return 0;
        return listFoods.size();
    }



    public static class MenuHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView tvNameMenu;
        private TextView tvPriceMenu;

        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = (ImageView) itemView.findViewById(R.id.imgLogo);
            tvNameMenu = (TextView) itemView.findViewById(R.id.tvNameMenu);
            tvPriceMenu = (TextView) itemView.findViewById(R.id.tvPriceMenu);
        }
    }

}
