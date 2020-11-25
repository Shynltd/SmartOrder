package com.example.smartorder.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.support.Support;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.admin.BillFragment;
import com.example.smartorder.fragment.admin.MenuFragment;
import com.example.smartorder.fragment.admin.UserFragment;
import com.example.smartorder.fragment.admin.TableFragment;

import de.hdodenhof.circleimageview.CircleImageView;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {


    private androidx.appcompat.widget.Toolbar toolbar;
    private CircleImageView imgProfile;
    private SmoothBottomBar nbBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Glide.with(MainActivity.this).load(Constants.LINK + Constants.AvatarUser).into(imgProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý món ăn");
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomPopupMenu(view);
            }
        });
//        Support.replaceFragment(getSupportFragmentManager(), R.id.frm, new MenuFragment());
        getFragmentItem();


    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        nbBar = (SmoothBottomBar) findViewById(R.id.nbBar);
    }

    private void showCustomPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        popupMenu.inflate(R.menu.my_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.profile:
                    break;
                case R.id.logOut:
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
//                        Support.replaceFragment(getSupportFragmentManager(), R.id.frm, new MenuFragment(), false, );
                        getSupportActionBar().setTitle("Quản lý món ăn");
                        break;
                    case 1:
//                        Support.replaceFragment(getSupportFragmentManager(), R.id.frm, new TableFragment());
                        getSupportActionBar().setTitle("Quản lý bàn");
                        break;
                    case 2:
//                        Support.replaceFragment(getSupportFragmentManager(), R.id.frm, new UserFragment());
                        getSupportActionBar().setTitle("Quản lý nhân viên");
                        break;
                    case 3:
//                        Support.replaceFragment(getSupportFragmentManager(), R.id.frm, new BillFragment());
                        getSupportActionBar().setTitle("Quản lý hóa đơn");
                        break;

                }
                return true;
            }
        });
    }
}