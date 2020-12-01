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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.MenuDrinksAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.admin.MenuFragment;
import com.example.smartorder.fragment.admin.TableFragment;
import com.example.smartorder.fragment.admin.UserFragment;
import com.example.smartorder.model.menu.ListDrink;
import com.example.smartorder.model.menu.Menu;
import com.example.smartorder.model.response.ServerResponse;
import com.example.smartorder.model.table.Table;
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

public class MenuDrinkFragment extends Fragment {

    private RecyclerView rvListMenuDrink;
    private MenuDrinksAdapter menuDrinksAdapter;
    private RetrofitAPI retrofitAPI;
    private List<ListDrink> listDrinks;
    private ImageView imvFood;
    private Uri uriImage = null;
    private int REQUEST_CODE_LOAD_IMAGE = 01234;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_drink, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvListDrinks();
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        retrofitAPI.getAllMenu().enqueue(new Callback<Menu>() {
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {
                if (response.body() != null) {
                    List<ListDrink> drinks = response.body().getListDrink();
                    getListDrinksFromServer(drinks);

                }
            }

            @Override
            public void onFailure(Call<Menu> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

    }


    private void initView(View view) {
        rvListMenuDrink = (RecyclerView) view.findViewById(R.id.rvListMenuDrink);
    }


    private void rvListDrinks() {
        listDrinks = new ArrayList<>();
        menuDrinksAdapter = new MenuDrinksAdapter(listDrinks, getContext(), new MenuDrinksAdapter.OnClickListener() {
            @Override
            public void deleteDrink(int position, String id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn xóa đồ uống " + listDrinks.get(position).getName() + " không ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                retrofitAPI.deleteDrink(id).enqueue(new Callback<ServerResponse>() {
                                    @Override
                                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.detach(MenuDrinkFragment.this).attach(MenuDrinkFragment.this).commit();

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
            public void updateDrink(int position, List<ListDrink> listDrinks) {
                dialogUpdateDrink(position, listDrinks);
            }
        });
        rvListMenuDrink.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListMenuDrink.setHasFixedSize(true);
        rvListMenuDrink.setAdapter(menuDrinksAdapter);
    }

    private void getListDrinksFromServer(List<ListDrink> drinks) {
        for (int i = 0; i < drinks.size(); i++) {
            String id = drinks.get(i).getId();
            String name = drinks.get(i).getName();
            Integer price = drinks.get(i).getPrice();
            String image = drinks.get(i).getImage();
            String type = drinks.get(i).getType();
            Integer amount = drinks.get(i).getAmount();
            listDrinks.add(new ListDrink(id, name, price, image, type, amount));
            menuDrinksAdapter.notifyDataSetChanged();
        }
    }

    private void dialogUpdateDrink(int position, List<ListDrink> listDrinks) {
        ListDrink listDrink = listDrinks.get(position);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        View alert = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_menu, null);
        alertDialog.setView(alert);
        alertDialog.setTitle("Chỉnh sửa thông tin Menu");
        alertDialog.setCancelable(false);
        EditText edtTenMon = alert.findViewById(R.id.edtNameFood);
        edtTenMon.setText(listDrink.getName());
        EditText edtPrice = alert.findViewById(R.id.edtPriceFood);
        edtPrice.setText(String.valueOf(listDrink.getPrice()));
        TextView tvType = alert.findViewById(R.id.tvType);
        tvType.setText(listDrink.getType());
        EditText edAmonut = alert.findViewById(R.id.edtAmountFood);
        edAmonut.setText(String.valueOf(listDrink.getAmount()));
        imvFood = alert.findViewById(R.id.imgAvtFood);
        Glide.with(getContext()).load(Constants.LINK+listDrink.getImage()).into(imvFood);
        imvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        REQUEST_CODE_LOAD_IMAGE);
            }
        });
        Button btnUpdate = alert.findViewById(R.id.btnAddFood);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                    String tenmon = edtTenMon.getText().toString();
                    Integer price = Integer.parseInt(edtPrice.getText().toString());
                    Integer amonut = Integer.parseInt(edAmonut.getText().toString().trim());
                    String type = tvType.getText().toString();
                    if (uriImage != null) {
                        File file = new File(Support.getPathFromUri(getContext(), uriImage));
                        RequestBody requestBody = RequestBody.create(MediaType.parse(
                                getContext().getContentResolver().getType(uriImage)), file);
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                                "avatar", file.getName(), requestBody);
                        retrofitAPI.updateDrink(listDrink.getId(),tenmon, price, amonut, type, filePart).enqueue(new Callback<ServerResponse>() {
                            @Override
                            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.detach(MenuDrinkFragment.this).attach(MenuDrinkFragment.this).commit();
                            }
                            @Override
                            public void onFailure(Call<ServerResponse> call, Throwable t) {
                                Log.e( "onFailure: ", t.getMessage());
                            }
                        });
                    } else {
                        retrofitAPI.updateDrinkNoImage(listDrink.getId(), tenmon, price, amonut, type).enqueue(new Callback<ServerResponse>() {
                            @Override
                            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.detach(MenuDrinkFragment.this).attach(MenuDrinkFragment.this).commit();
                            }
                            @Override
                            public void onFailure(Call<ServerResponse> call, Throwable t) {
                                Log.e("onFailureNoImage: ", t.getMessage());
                            }
                        });
                    }
                }

        });

        Button btnCancel = alert.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            uriImage = uri;
            imvFood.setImageURI(uri);
        }
    }

}