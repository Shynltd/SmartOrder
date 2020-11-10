package com.example.smartorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableHolder> {
    private Context context;

    @NonNull
    @Override
    public TableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_table, parent, false);
        return new TableHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TableHolder extends RecyclerView.ViewHolder {
        public TableHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
