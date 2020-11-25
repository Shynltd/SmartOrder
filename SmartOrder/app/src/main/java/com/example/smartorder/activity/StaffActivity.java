package com.example.smartorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.adapter.staff.StaffTableAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.staff.ListFoodOrderFragment;
import com.example.smartorder.model.callback.CallbackTalble;
import com.example.smartorder.model.table.Table;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffActivity extends AppCompatActivity {


    private androidx.appcompat.widget.Toolbar toolbar;
    private RecyclerView rvListTableStaff;
    private FloatingActionButton fabAddTable;
    private RetrofitAPI retrofitAPI;
    private StaffTableAdapter staffTableAdapter;
    private List<Table> tableList;
    private CircleImageView imgProfile;
    private LinearLayout lnlTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        initView();
        Glide.with(StaffActivity.this).load(Constants.LINK + Constants.AvatarUser).into(imgProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order");
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomPopupMenu(view);
            }
        });
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        tableList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(StaffActivity.this, 2, RecyclerView.VERTICAL, false);
        rvListTableStaff.setLayoutManager(gridLayoutManager);
        staffTableAdapter = new StaffTableAdapter(StaffActivity.this, tableList, new StaffTableAdapter.OnClickListener() {
            @Override
            public void order(int position) {
                ListFoodOrderFragment listFoodOrderFragment = new ListFoodOrderFragment();
                CallbackTalble callbackTalble = listFoodOrderFragment;
                callbackTalble.getTable(tableList.get(position));
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.list_food_bottom_to_top,0).add(R.id.frmTest,listFoodOrderFragment, Constants.fragmentListFood).commit();
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
                    tableList.add(new Table(id, tableCode, tableSeats));
                    staffTableAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }
        });
    }

    private void initView() {
        rvListTableStaff = (RecyclerView) findViewById(R.id.rvListTableStaff);
        toolbar = findViewById(R.id.toolbar);
        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
    }

    private void showCustomPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(StaffActivity.this, view);
        popupMenu.inflate(R.menu.my_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:

                        break;
                    case R.id.logOut:
                        Intent intent = new Intent(StaffActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        ActivityCompat.finishAffinity(StaffActivity.this);
                        finish();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onBackPressed() {
    }
}