package com.example.smartorder.adapter.staff;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.model.menu.ListFood;
import com.example.smartorder.model.menu.MenuOrder;

import java.util.List;

import javax.security.auth.login.LoginException;

public class MenuOrderAdapter extends RecyclerView.Adapter<MenuOrderAdapter.MenuHolder> {
    private List<MenuOrder> menuOrders;
    private Context context;


    public MenuOrderAdapter(List<MenuOrder> menuOrders, Context context) {
        this.menuOrders = menuOrders;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_menu_order, parent, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        holder.chkOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {

                    final int[] slOrder = {1};
                    holder.tvSl.setVisibility(View.VISIBLE);
                    holder.tvSl.setText(String.valueOf(slOrder[0]));
                    holder.btnTang.setVisibility(View.VISIBLE);
                    holder.btnGiam.setVisibility(View.VISIBLE);
                    holder.btnTang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slOrder[0] += 1;
                            holder.tvSl.setText(String.valueOf(slOrder[0]));
                        }
                    });
                    holder.btnGiam.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (slOrder[0] > 1) {
                                slOrder[0] -= 1;
                                holder.tvSl.setText(String.valueOf(slOrder[0]));
                            } else {
                                slOrder[0] = 1;
                            }
                        }
                    });
                } else {
                    holder.tvSl.setVisibility(View.INVISIBLE);
                    holder.btnTang.setVisibility(View.INVISIBLE);
                    holder.btnGiam.setVisibility(View.INVISIBLE);
                }
            }
        });
        holder.tvNameMenu.setText(menuOrders.get(position).getName());
        holder.tvPriceMenu.setText("Giá: " + menuOrders.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        if (menuOrders == null) return 0;
        return menuOrders.size();
    }


    public static class MenuHolder extends RecyclerView.ViewHolder {
        private TextView tvNameMenu;
        private TextView tvPriceMenu;
        private CheckBox chkOrder;
        private ImageButton btnTang;
        private TextView tvSl;
        private ImageButton btnGiam;

        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            tvNameMenu = (TextView) itemView.findViewById(R.id.tvNameMenu);
            tvPriceMenu = (TextView) itemView.findViewById(R.id.tvPriceMenu);
            chkOrder = (CheckBox) itemView.findViewById(R.id.chkOrder);
            btnTang = (ImageButton) itemView.findViewById(R.id.btnTang);
            tvSl = (TextView) itemView.findViewById(R.id.tvSl);
            btnGiam = (ImageButton) itemView.findViewById(R.id.btnGiam);
        }
    }
}
