package com.example.smartorder.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.smartorder.R;
import com.example.smartorder.model.user.User;
import com.example.smartorder.support.Support;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChangePassFragment extends Fragment {

    private CircleImageView imgVongTron;
    private User user;

    public ChangePassFragment(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        initView(view);
        imgVongTron.setAnimation(Support.setAnimation(getContext(),R.anim.rotate_change_pass, 20000,0));
        return view;
    }

    private void initView(View view) {
        imgVongTron = (CircleImageView) view.findViewById(R.id.imgVongTron);
    }
}