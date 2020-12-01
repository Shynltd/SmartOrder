package com.example.smartorder.fragment.admin.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.MenuFoodAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.model.menu.ListDrink;
import com.example.smartorder.model.menu.ListFood;
import com.example.smartorder.model.menu.Menu;
import com.example.smartorder.model.response.ServerResponse;
import com.example.smartorder.support.Support;

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

public class MenuFoodFragment extends Fragment {

    private RecyclerView rvListMenuFood;
    private MenuFoodAdapter menuFoodAdapter;
    private RetrofitAPI retrofitAPI;
    private List<ListFood> listFoods;
    private int REQUEST_CODE_LOAD_IMAGE = 01234;
    private Uri uriImage = null;
    private ImageView imvFood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_food, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvListFood();
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        retrofitAPI.getAllMenu().enqueue(new Callback<Menu>() {
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {
                if (response.body() != null) {
                    List<ListFood> foods = response.body().getListFood();
                    getListFoodFromServer(foods);

                }
            }

            @Override
            public void onFailure(Call<Menu> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }


    private void initView(View view) {
        rvListMenuFood = (RecyclerView) view.findViewById(R.id.rvListMenuFood);
    }

    private void rvListFood() {
        listFoods = new ArrayList<>();
        menuFoodAdapter = new MenuFoodAdapter(listFoods, getContext(), new MenuFoodAdapter.OnClickListener() {
            @Override
            public void deleteFood(int position, String id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn xóa món ăn" + listFoods.get(position).getName() + " không?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                retrofitAPI.deleteDrink(id).enqueue(new Callback<ServerResponse>() {
                                    @Override
                                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.detach(MenuFoodFragment.this).attach(MenuFoodFragment.this).commit();
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
                }).create().show();
            }

            @Override
            public void updateFood(int position, List<ListFood> listFood) {
                dialogUpdateFoods(position,listFood);
            }
        });
        rvListMenuFood.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListMenuFood.setHasFixedSize(true);
        rvListMenuFood.setAdapter(menuFoodAdapter);
    }


    private void getListFoodFromServer(List<ListFood> foods) {
        for (int j = 0; j < foods.size(); j++) {
            String id = foods.get(j).getId();
            String name = foods.get(j).getName();
            Integer price = foods.get(j).getPrice();
            String image = foods.get(j).getImage();
            String type = foods.get(j).getType();
            listFoods.add(new ListFood(id, name, price, image, type));
            menuFoodAdapter.notifyDataSetChanged();
        }
    }

    private void dialogUpdateFoods(int position, List<ListFood> listDrinks) {
        ListFood listFood = listFoods.get(position);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        View alert = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_menu, null);
        alertDialog.setView(alert);
        alertDialog.setTitle("Chỉnh sửa thông tin Món ăn");
        alertDialog.setCancelable(false);

        EditText edtName = alert.findViewById(R.id.edtNameFood);
        edtName.setText(String.valueOf(listFood.getName()));
        EditText edtPrice = alert.findViewById(R.id.edtPriceFood);
        edtPrice.setText(String.valueOf(listFood.getPrice()));
        EditText edtAmount = alert.findViewById(R.id.edtAmountFood);
        TextView tvAmount = alert.findViewById(R.id.tvAmountFood);
        tvAmount.setVisibility(View.INVISIBLE);
        edtAmount.setText("0");
        edtAmount.setVisibility(View.INVISIBLE);
        TextView tvType = alert.findViewById(R.id.tvType);
        tvType.setText(String.valueOf(listFood.getType()));
         imvFood = alert.findViewById(R.id.imgAvtFood);
        Glide.with(getContext()).load(Constants.LINK + listFood.getImage()).into(imvFood);

        Button btnCancel = alert.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        imvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_LOAD_IMAGE);
            }
        });

        Button btnUpdate = alert.findViewById(R.id.btnAddFood);
        btnUpdate.setText("Chỉnh sửa");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String tenmon = edtName.getText().toString();
                Integer price = Integer.parseInt(edtPrice.getText().toString());
                String type = tvType.getText().toString();
                if (uriImage != null) {
                    File file = new File(Support.getPathFromUri(getContext(), uriImage));
                    RequestBody requestBody = RequestBody.create(MediaType.parse(
                            getContext().getContentResolver().getType(uriImage)), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                            "avatar", file.getName(), requestBody);
                    retrofitAPI.updateFood(listFood.getId(), tenmon, price,type, filePart).enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.detach(MenuFoodFragment.this).attach(MenuFoodFragment.this).commit();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                            Log.e("onFailure: ", t.getMessage());
                        }
                    });
                }else {
                    retrofitAPI.updateFoodNoImage(listFood.getId(), tenmon, price, type).enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.detach(MenuFoodFragment.this).attach(MenuFoodFragment.this).commit();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                            Log.e("onFailureNoImage: ", t.getMessage());
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
            uriImage = uri;
            imvFood.setImageURI(uri);
        }
    }
}