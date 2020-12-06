package com.example.smartorder.fragment.staff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.staff.StaffTableAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.callback.CallbackTalble;
import com.example.smartorder.model.table.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTableOrderFragment extends Fragment {

    private RecyclerView rvListTableStaff;
    private RetrofitAPI retrofitAPI;
    private StaffTableAdapter staffTableAdapter;
    private List<Table> tableList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_table_order, container, false);
        initView(view);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        tableList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rvListTableStaff.setLayoutManager(gridLayoutManager);
        staffTableAdapter = new StaffTableAdapter(getActivity(), tableList, new StaffTableAdapter.OnClickListener() {
            @Override
            public void order(int position, View view) {
                ListFoodOrderFragment listFoodOrderFragment = new ListFoodOrderFragment();
                CallbackTalble callbackTalble = listFoodOrderFragment;
                callbackTalble.getTable(tableList.get(position));
                BillPreviewFragment previewFragment = new BillPreviewFragment();
                CallbackTalble callbackTalble1 = previewFragment;
                callbackTalble1.getTable(tableList.get(position));
                if (tableList.get(position).getStatus()) {
                    showPopupChoicesMenu(view, listFoodOrderFragment, previewFragment);
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.list_food_bottom_to_top, 0).add(R.id.frmTest, listFoodOrderFragment, Constants.fragmentListFood).commit();
                }
            }
        });
        rvListTableStaff.setHasFixedSize(true);
        rvListTableStaff.setAdapter(staffTableAdapter);
        retrofitAPI.getAllTable().enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                List<Table> table = response.body();
                for (int i = 0; i < table.size(); i++) {
                    String id = table.get(i).getId();
                    Integer tableCode = table.get(i).getTableCode();
                    Integer tableSeats = table.get(i).getTableSeats();
                    boolean status = table.get(i).getStatus();
                    tableList.add(new Table(id, tableCode, tableSeats, status));
                    staffTableAdapter.notifyDataSetChanged();
                }
                Collections.sort(tableList, new Comparator<Table>() {
                    @Override
                    public int compare(Table table, Table t1) {
                        if (table.getTableCode() > t1.getTableCode()) {
                            return 1;
                        } else {
                            if (table.getTableCode() == t1.getTableCode()) {
                                return 0;
                            } else {
                                return -1;
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void initView(View view) {
        rvListTableStaff = (RecyclerView) view.findViewById(R.id.rvListTableStaff);
    }

    private void showPopupChoicesMenu(View view, Fragment listFoodOrderFragment, Fragment previewFragment) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.choices_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.order:
                        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.list_food_bottom_to_top, 0).add(R.id.frmTest, listFoodOrderFragment, Constants.fragmentListFood).commit();
                        break;
                    case R.id.invoicePreview:
                        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.list_food_bottom_to_top, 0).add(R.id.frmTest, previewFragment, Constants.fragmentPreviewBill).commit();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}