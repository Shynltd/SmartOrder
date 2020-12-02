package com.example.smartorder.fragment.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.UserAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.response.ServerResponse;
import com.example.smartorder.model.user.User;
import com.example.smartorder.support.Support;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class UserFragment extends Fragment {
    private RecyclerView rvListUser;
    private List<User> userList;
    private UserAdapter userAdapter;
    private RetrofitAPI retrofitAPI;
    private FloatingActionButton fabAddStaff;
    private final int REQUEST_CODE_LOAD_IMAGE = 1;
    private Uri mUriImage = null;
    private ImageView imgAvatar;
    private String role = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvListUser.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter(getContext(), userList, new UserAdapter.OnClickListener() {

            @Override
            public void deleteUser(int position, String id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn nhân viên " + userList.get(position).getFullName() + " không ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                retrofitAPI.deleteUser(id).enqueue(new Callback<ServerResponse>() {
                                    @Override
                                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.detach(UserFragment.this).attach(UserFragment.this).commit();
                                    }

                                    @Override
                                    public void onFailure(Call<ServerResponse> call, Throwable t) {

                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                        .create().show();
            }

            @Override
            public void updateUser(int position, List<User> userList) {
                dialogUpdateUser(position, userList);
            }


        });
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
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }
        });
        fabAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddStaff();
            }
        });

    }

    private void initView(View view) {
        rvListUser = (RecyclerView) view.findViewById(R.id.rvListUser);
        fabAddStaff = (FloatingActionButton) view.findViewById(R.id.fabAddStaff);
    }


    private void dialogAddStaff() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        View alert = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_staff, null);
        List<String> roles = new ArrayList<>();
        roles.add("Admin");
        roles.add("Staff");
        roles.add("Cashier");
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, roles);
        alertDialog.setTitle("Tạo mới nhân viên");
        alertDialog.setView(alert);
        alertDialog.setCancelable(false);
        imgAvatar = alert.findViewById(R.id.imgAvatar);
        EditText edtFullName = (EditText) alert.findViewById(R.id.edtNameUser);
        EditText edtPhone = (EditText) alert.findViewById(R.id.edtPhoneUser);
        EditText edtAge = (EditText) alert.findViewById(R.id.edtAge);
        EditText edtAddress = (EditText) alert.findViewById(R.id.edtAddress);
        EditText edtCmnd = (EditText) alert.findViewById(R.id.edtCmnd);
        Spinner spnRole = (Spinner) alert.findViewById(R.id.spnRole);
        Button btnCancel = alert.findViewById(R.id.btnCancel);
        Button btnAddUser = alert.findViewById(R.id.btnAddUser);
        spnRole.setAdapter(arrayAdapter);

        spnRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                role = roles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_LOAD_IMAGE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (edtPhone.getText().toString().isEmpty() || edtAddress.getText().toString().isEmpty() ||
                        edtAge.getText().toString().isEmpty() || edtFullName.getText().toString().isEmpty()
                        || edtCmnd.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if(edtPhone.getText().toString().length()<10 ||edtPhone.getText().toString().length()>11  ){
                    Toast.makeText(getContext(), "Phone have 10-11 number. Please try again",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String fullName = edtFullName.getText().toString().trim();
                    String phone = edtPhone.getText().toString().trim();
                    Integer cmnd = Integer.valueOf(edtCmnd.getText().toString().trim());
                    Integer age = Integer.valueOf(edtAge.getText().toString().trim());
                    String address = edtAddress.getText().toString().trim();
                    File file = new File(Support.getPathFromUri(getContext(), mUriImage));
                    RequestBody requestBody = RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(mUriImage)), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);
                    retrofitAPI.createUser(fullName, phone, cmnd, age, address, role, filePart).enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.detach(UserFragment.this).attach(UserFragment.this).commit();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                            Log.e("onFailure: ", t.getMessage());
                        }
                    });
                }
            }
        });
        alertDialog.show();
    }

    private void dialogUpdateUser(int position, List<User> userList) {
        User user = userList.get(position);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        View alert = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_staff, null);
        alertDialog.setView(alert);
        alertDialog.setTitle("Chỉnh sửa thông tin nhân viên");
        alertDialog.setCancelable(false);
        imgAvatar = alert.findViewById(R.id.imgAvatar);

        EditText edtFullName = alert.findViewById(R.id.edtNameUser);
        edtFullName.setText(String.valueOf(user.getFullName()));

        EditText edtPhone = alert.findViewById(R.id.edtPhoneUser);
        edtPhone.setText(String.valueOf(user.getPhone()));

        EditText edtCMND = alert.findViewById(R.id.edtCmnd);
        edtCMND.setText(String.valueOf(user.getIndentityCardNumber()));

        EditText edtAge = alert.findViewById(R.id.edtAge);
        edtAge.setText(String.valueOf(user.getAge()));

        EditText edtAddress = alert.findViewById(R.id.edtAddress);
        edtAddress.setText(String.valueOf(user.getAddress()));

        Glide.with(getContext()).load(Constants.LINK + user.getAvatar()).into(imgAvatar);

        Spinner spnRole = (Spinner) alert.findViewById(R.id.spnRole);
        List<String> roles = new ArrayList<>();
        roles.add("Admin");
        roles.add("Staff");
        roles.add("Cashier");
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, roles);
        spnRole.setAdapter(arrayAdapter);
        spnRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                role = roles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_LOAD_IMAGE);
            }
        });


        Button btnCancel = alert.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Button btnUpdate = alert.findViewById(R.id.btnAddUser);
        btnUpdate.setText("Cập nhật");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String fullName = edtFullName.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                Integer cmnd = Integer.parseInt(edtCMND.getText().toString().trim());
                Integer age = Integer.parseInt(edtAge.getText().toString().trim());
                String address = edtAddress.getText().toString().trim();
                if (edtPhone.getText().toString().isEmpty() || edtAddress.getText().toString().isEmpty() ||
                        edtAge.getText().toString().isEmpty() || edtFullName.getText().toString().isEmpty()
                        || edtCMND.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }  else if(edtPhone.getText().toString().length()<10 ||edtPhone.getText().toString().length()>11  ){
                    Toast.makeText(getContext(), "Phone have 10-11 number. Please try again",
                            Toast.LENGTH_SHORT).show();
                } else if (mUriImage != null) {
                    File file = new File(Support.getPathFromUri(getContext(), mUriImage));
                    RequestBody requestBody = RequestBody.create(MediaType.parse(
                            getContext().getContentResolver().getType(mUriImage)), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                            "avatar", file.getName(), requestBody);
                    retrofitAPI.updateUser(user.getId(), fullName, phone, cmnd, age, address, role, filePart).enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.detach(UserFragment.this).attach(UserFragment.this).commit();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                            Log.e("onFailure: ", t.getMessage());
                        }
                    });
                } else {
                    retrofitAPI.updateUserNoImage(user.getId(), fullName, phone, cmnd, age, address, role).enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.detach(UserFragment.this).attach(UserFragment.this).commit();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                            Log.e("onFailure: ", t.getMessage());
                        }
                    });

                }
            }

        });

        alertDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            mUriImage = uri;
            imgAvatar.setImageURI(uri);

        }
    }
}