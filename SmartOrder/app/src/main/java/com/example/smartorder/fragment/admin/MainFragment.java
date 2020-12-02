package com.example.smartorder.fragment.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.activity.LoginActivity;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.ProfileFragment;
import com.example.smartorder.fragment.admin.BillFragment;
import com.example.smartorder.fragment.admin.MenuFragment;
import com.example.smartorder.fragment.admin.TableFragment;
import com.example.smartorder.fragment.admin.UserFragment;
import com.example.smartorder.support.Support;

import de.hdodenhof.circleimageview.CircleImageView;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainFragment extends Fragment {

    private androidx.appcompat.widget.Toolbar toolbar;
    private CircleImageView imgProfile;
    private SmoothBottomBar nbBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        Glide.with(getActivity()).load(Constants.LINK + Constants.AvatarUser).into(imgProfile);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Quản lý món ăn");
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomPopupMenu(view);
            }
        });
        Support.replaceFragment(getFragmentManager(), R.id.frm, new MenuFragment(), false, 0, 0);
        getFragmentItem();
        return view;

    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        imgProfile = (CircleImageView) view.findViewById(R.id.imgProfile);
        nbBar = (SmoothBottomBar) view.findViewById(R.id.nbBar);
    }
    private void showCustomPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.my_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
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
    public void getFragmentItem() {
        nbBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        Support.replaceFragment(
                                getFragmentManager(),
                                R.id.frm, new MenuFragment(),
                                false,
                                R.anim.admin_fragment_main_translate_enter_right_to_left,
                                R.anim.admin_fragment_main_translate_exit_right_to_left
                        );
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Quản lý món ăn");
                        break;
                    case 1:
                        Support.replaceFragment(
                                getFragmentManager(),
                                R.id.frm, new TableFragment(),
                                false,
                                R.anim.admin_fragment_main_translate_enter_right_to_left,
                                R.anim.admin_fragment_main_translate_exit_right_to_left
                        );
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Quản lý bàn");

                        break;
                    case 2:
                        Support.replaceFragment(
                                getFragmentManager(),
                                R.id.frm, new UserFragment(),
                                false,
                                R.anim.admin_fragment_main_translate_enter_right_to_left,
                                R.anim.admin_fragment_main_translate_exit_right_to_left
                        );
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Quản lý nhân viên");
                        break;
                    case 3:
                        Support.replaceFragment(
                                getFragmentManager(),
                                R.id.frm, new BillFragment(),
                                false,
                                R.anim.admin_fragment_main_translate_enter_right_to_left,
                                R.anim.admin_fragment_main_translate_exit_right_to_left
                        );
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Quản lý hóa đơn");
                        break;

                }
                return false;
            }
        });
    }
}