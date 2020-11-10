package com.example.smartorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillHolder> {
    private  Context context;
    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_bill, parent, false);
        return new BillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BillHolder extends RecyclerView.ViewHolder {
        public BillHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
