package com.example.smartorder.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.TableAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.Table;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableFragment extends Fragment {

    private FloatingActionButton fabAddTable;
    private RecyclerView rvListTable;
    private RetrofitAPI retrofitAPI;
    private TableAdapter tableAdapter;
    private List<Table> tableList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tableList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rvListTable.setLayoutManager(gridLayoutManager);
        tableAdapter = new TableAdapter(getContext(), tableList);
        rvListTable.setHasFixedSize(true);
        rvListTable.setAdapter(tableAdapter);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        retrofitAPI.getAllTable().enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                List<Table> table = response.body();
                for (int i = 0; i < table.size(); i++) {
                    String id = table.get(i).getId();
                    Integer tableCode = table.get(i).getTableCode();
                    Integer tableSeats = table.get(i).getTableSeats();
                    tableList.add(new Table(id, tableCode, tableSeats));
                    tableAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }
        });

        fabAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddTable();
            }
        });

    }

    private void initView(View view) {
        rvListTable = (RecyclerView) view.findViewById(R.id.rvListTable);
        fabAddTable = (FloatingActionButton) view.findViewById(R.id.fabAddTable);
    }

    private void dialogAddTable(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        View alert= LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_table,null);
        alertDialog.setTitle("Tạo mới bàn");
        alertDialog.setView(alert);
        alertDialog.setCancelable(false);
        EditText edtTableCode = alert.findViewById(R.id.edtTableCode);
        EditText edtTableSeats = alert.findViewById(R.id.edtTableSeats);
        Button btnAddTable = alert.findViewById(R.id.btnAddTable);
        Button btnCancel = alert.findViewById(R.id.btnCancel);

        btnAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTableCode.getText().toString().isEmpty()){
                    edtTableCode.setError("Table Code Empty");
                } else if (edtTableSeats.getText().toString().isEmpty()){
                    edtTableSeats.setError("Table Seats Empty");
                } else {
                    int tableCode = Integer.parseInt(edtTableCode.getText().toString().trim());
                    int tableSeats = Integer.parseInt(edtTableSeats.getText().toString().trim());
                    Log.e( "onClick: ",tableCode + " " + tableSeats );
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}