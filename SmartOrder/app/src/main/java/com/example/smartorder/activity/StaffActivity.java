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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.adapter.staff.StaffTableAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.ProfileFragment;
import com.example.smartorder.fragment.admin.MainFragment;
import com.example.smartorder.fragment.staff.ListFoodOrderFragment;
import com.example.smartorder.fragment.staff.StaffFragment;
import com.example.smartorder.model.callback.CallbackTalble;
import com.example.smartorder.model.table.Table;
import com.example.smartorder.support.Support;
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
        Support.replaceFragment(getSupportFragmentManager(), R.id.frq, new StaffFragment(), false, 0, 0);

    }



    @Override
    public void onBackPressed() {
    }
}