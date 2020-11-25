package com.example.smartorder.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.menu.ListDrink;
import com.example.smartorder.model.table.Table;

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
        Glide.with(context).load(Constants.LINK + listDrinks.get(position).getImage()).into(holder.imgLogo);
        holder.tvNameMenu.setText(listDrinks.get(position).getName());
        holder.tvPriceMenu.setText(context.getString(R.string.text_adapter_price)+ listDrinks.get(position).getPrice());
        holder.tvAmountMenu.setText(context.getString(R.string.text_adapter_amount) + listDrinks.get(position).getAmount());
        holder.tvNameMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.imgLogo);
                popupMenu.getMenuInflater().inflate(R.menu.update_delete, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete:
//                                onClickListener.delete(position, tableList.get(position).getId());
                                break;
                            case R.id.update:
//                                onClickListener.update(position, tableList);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
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
//    public interface OnClickListener {
//        void  delete (int position ,String id);
//        void  update (int position , List<Table> tableList);
//    }
}
