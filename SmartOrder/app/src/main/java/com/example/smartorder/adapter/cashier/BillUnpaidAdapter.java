package com.example.smartorder.adapter.cashier;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BillUnpaidAdapter extends RecyclerView.Adapter<BillUnpaidAdapter.BillUnpaidHolder> {
    @NonNull
    @Override
    public BillUnpaidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BillUnpaidHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BillUnpaidHolder extends RecyclerView.ViewHolder {
        public BillUnpaidHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
