package com.example.smartorder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartorder.R;
import com.example.smartorder.adapter.UserAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StaffFragment extends Fragment {


    private RecyclerView rvListUser;
    private List<User> userList;
    private UserAdapter userAdapter;
    private RetrofitAPI retrofitAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvListUser.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter(getContext(), userList);
        rvListUser.setHasFixedSize(true);
        rvListUser.setAdapter(userAdapter);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        retrofitAPI.getAllUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                for (int i = 0; i < users.size(); i++) {
                    String id = users.get(i).getId();
                    String passWord = users.get(i).getPassWord();
                    String role = users.get(i).getRole();
                    String fullName = users.get(i).getFullName();
                    String phone = users.get(i).getPhone();
                    String address = users.get(i).getAddress();
                    Integer age = users.get(i).getAge();
                    String avatar = users.get(i).getAvatar();
                    Integer indentityCardNumber = users.get(i).getIndentityCardNumber();
                    userList.add(new User(id, passWord, role, fullName, phone, address, age, avatar, indentityCardNumber));
                    userAdapter.notifyDataSetChanged();
                }


                Log.e("onResponse: ", String.valueOf(userList.size()));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }
        });

    }

    private void initView(View view) {
        rvListUser = (RecyclerView) view.findViewById(R.id.rvListUser);
    }
}