package com.example.smartorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private Context context;
    private List<User> userList;


    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        Glide.with(context).load(Constants.LINK + userList.get(position).getAvatar()).into(holder.imgUser);
        holder.tvNameUser.setText(userList.get(position).getFullName());
        holder.tvRole.setText(userList.get(position).getRole());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public static class UserHolder extends RecyclerView.ViewHolder {
        private ImageView imgUser;
        private TextView tvNameUser;
        private TextView tvRole;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = (ImageView) itemView.findViewById(R.id.imgUser);
            tvNameUser = (TextView) itemView.findViewById(R.id.tvNameUser);
            tvRole = (TextView) itemView.findViewById(R.id.tvRole);
        }
    }


}
