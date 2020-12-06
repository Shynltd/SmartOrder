package com.example.smartorder.fragment.staff;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.activity.LoginActivity;
import com.example.smartorder.adapter.staff.StaffTableAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.ProfileFragment;
import com.example.smartorder.model.callback.CallbackTalble;
import com.example.smartorder.model.table.Table;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StaffFragment extends Fragment {

    private androidx.appcompat.widget.Toolbar toolbar;
    private RecyclerView rvListTableStaff;
    private FloatingActionButton fabAddTable;
    private CircleImageView imgProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        initView(view);
        Glide.with(getActivity()).load(Constants.LINK + Constants.AvatarUser).into(imgProfile);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Order");
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomPopupMenu(view);
            }
        });
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frmTest, new ListTableOrderFragment(), Constants.fragmentListTableOrder).commit();

        return view;
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        imgProfile = (CircleImageView) view.findViewById(R.id.imgProfile);
    }

    private void showCustomPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.my_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        ProfileFragment profileFragment = new ProfileFragment();
                        transaction.setCustomAnimations(R.anim.list_food_bottom_to_top, 0).add(R.id.frq, profileFragment, Constants.fragmentProfile).commit();
                        break;
                    case R.id.logOut:
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        ActivityCompat.finishAffinity(getActivity());
                        getActivity().finish();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }




}