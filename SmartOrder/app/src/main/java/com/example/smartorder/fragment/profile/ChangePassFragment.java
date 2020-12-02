package com.example.smartorder.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.smartorder.R;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.response.ServerResponse;
import com.example.smartorder.model.user.User;
import com.example.smartorder.support.Support;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePassFragment extends Fragment {

    private CircleImageView imgVongTron;
    private User user;
    private EditText edtCurrentPass;
    private EditText edtNewPass;
    private EditText edtComfirmPass;
    private ImageButton btnChangePass;
    private RetrofitAPI retrofitAPI;

    public ChangePassFragment(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        initView(view);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        imgVongTron.setAnimation(Support.setAnimation(getContext(), R.anim.rotate_change_pass, 20000, 0));
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });
        return view;
    }

    private void changePass() {
        String currentPass = edtCurrentPass.getText().toString().trim();
        String newPass = edtNewPass.getText().toString().trim();
        String confirmPass = edtComfirmPass.getText().toString().trim();
        if (confirmPass.equals(newPass)) {
            retrofitAPI.changePass(user.getId(),currentPass,newPass).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.code() == 200) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.fragmentEditProfile);
                        if (fragment != null) {
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.admin_fragment_main_translate_enter_left_to_right,
                                            R.anim.admin_fragment_main_translate_exit_left_to_right)
                                    .remove(fragment)
                                    .commit();
                            Fragment profileFrament = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.fragmentProfile);
                            if (profileFrament != null){
                                getFragmentManager().beginTransaction().detach(profileFrament).attach(profileFrament).commit();
                            }

                        }
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            edtComfirmPass.setError("Mật khẩu nhập lại k chính xác");
        }
    }

    private void initView(View view) {
        imgVongTron = (CircleImageView) view.findViewById(R.id.imgVongTron);
        edtCurrentPass = (EditText) view.findViewById(R.id.edtCurrentPass);
        edtNewPass = (EditText) view.findViewById(R.id.edtNewPass);
        edtComfirmPass = (EditText) view.findViewById(R.id.edtComfirmPass);
        btnChangePass = (ImageButton) view.findViewById(R.id.btnChangePass);
    }
}