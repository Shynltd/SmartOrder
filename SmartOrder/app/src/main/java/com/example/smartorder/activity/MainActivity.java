package com.example.smartorder.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.Support;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.BillFragment;
import com.example.smartorder.fragment.MenuFragment;
import com.example.smartorder.fragment.StaffFragment;
import com.example.smartorder.fragment.TableFragment;

import java.net.Socket;
import java.net.URISyntaxException;

import de.hdodenhof.circleimageview.CircleImageView;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {


    private androidx.appcompat.widget.Toolbar toolbar;
    private CircleImageView imgProfile;
    private SmoothBottomBar nbBar;
    private FrameLayout frm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý món ăn");
        Glide.with(MainActivity.this).load(Constants.LINK + Constants.AvatarUser).into(imgProfile);
        Support.replaceFragment(getSupportFragmentManager(), R.id.frm, new MenuFragment());
        getFragmentItem();

    }


    private void initView() {
        toolbar =  findViewById(R.id.toolbar);
        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        nbBar = (SmoothBottomBar) findViewById(R.id.nbBar);
        frm = (FrameLayout) findViewById(R.id.frm);
    }

    public void getFragmentItem() {
        nbBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        Support.replaceFragment(getSupportFragmentManager(), R.id.frm, new MenuFragment());
                        getSupportActionBar().setTitle("Quản lý món ăn");
                        break;
                    case 1:
                        Support.replaceFragment(getSupportFragmentManager(), R.id.frm, new TableFragment());
                        getSupportActionBar().setTitle("Quản lý bàn");
                        break;
                    case 2:
                        Support.replaceFragment(getSupportFragmentManager(), R.id.frm, new StaffFragment());
                        getSupportActionBar().setTitle("Quản lý nhân viên");
                        break;
                    case 3:
                        Support.replaceFragment(getSupportFragmentManager(), R.id.frm, new BillFragment());
                        getSupportActionBar().setTitle("Quản lý hóa đơn");
                        break;

                }
                return true;
            }
        });
    }
}