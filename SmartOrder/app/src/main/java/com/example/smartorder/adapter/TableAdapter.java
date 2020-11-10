package com.example.smartorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.model.Table;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableHolder> {
    private Context context;
    private List<Table> tableList;

    public TableAdapter(Context context, List<Table> tableList) {
        this.context = context;
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public TableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_table, parent, false);
        return new TableHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, int position) {
        holder.tvTableCode.setText(tableList.get(position).getTableCode() + "");
    }

    @Override
    public int getItemCount() {
        if (tableList == null) return 0;
        return tableList.size();
    }

    public static class TableHolder extends RecyclerView.ViewHolder {
        private TextView tvTableCode;

        public TableHolder(@NonNull View itemView) {
            super(itemView);
            tvTableCode = itemView.findViewById(R.id.tvTableCode);
        }
    }
}
