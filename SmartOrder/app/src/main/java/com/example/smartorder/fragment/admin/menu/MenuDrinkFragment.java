package com.example.smartorder.fragment.admin.menu;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.MenuDrinksAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
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

public class MenuDrinkFragment extends Fragment {

    private RecyclerView rvListMenuDrink;
    private MenuDrinksAdapter menuDrinksAdapter;
    private RetrofitAPI retrofitAPI;
    private List<Menu> menuListDrink;
    private EditText edtTenMon;
    private EditText edtPrice;
    private EditText edAmonut;
    private ImageView imvFood;
    private Spinner spnType;
    private TextView tvAmount;
    private Button btnUpdate, btnCancel;
    private String type;
    private Uri uriImage = null;
    private int REQUEST_CODE_LOAD_IMAGE = 01234;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_drink, container, false);
        initView(view);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        menuListDrink = new ArrayList<>();
        rvListDrinks();
        getAllMenuFromServer();
        return view;
    }

    private void initView(View view) {
        rvListMenuDrink = (RecyclerView) view.findViewById(R.id.rvListMenuDrink);
    }

    private void getAllMenuFromServer() {
        retrofitAPI.getAllMenu().enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                List<Menu> menus = response.body();
                for (int i = 0; i < menus.size(); i++) {
                    String id = menus.get(i).getId();
                    String name = menus.get(i).getName();
                    Integer price = menus.get(i).getPrice();
                    String image = menus.get(i).getImage();
                    String type = menus.get(i).getType();
                    boolean status = menus.get(i).getStatus();
                    if (type.equals("Drink")) {
                        menuListDrink.add(new Menu(id, name, price, image,type,status));
                        menuDrinksAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                Log.e("onFailureMenuFragment", t.getMessage());
            }
        });
    }
    private void rvListDrinks() {
        menuDrinksAdapter = new MenuDrinksAdapter(menuListDrink, getContext(), new MenuDrinksAdapter.OnClickListener() {
            @Override
            public void deleteDrink(int position, String id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn xóa đồ uống " + menuListDrink.get(position).getName() + " không ?")
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
            public void updateDrink(int position) {
                dialogUpdateDrink(position);
            }
        });
        rvListMenuDrink.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListMenuDrink.setHasFixedSize(true);
        rvListMenuDrink.setAdapter(menuDrinksAdapter);
    }


    private void dialogUpdateDrink(int position) {

        Menu menu = menuListDrink.get(position);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        View alert = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_menu, null);
        alertDialog.setTitle("Chỉnh sửa thông tin món ăn");
        alertDialog.setView(alert);
        alertDialog.setCancelable(false);
        edtTenMon = alert.findViewById(R.id.edtNameFood);
        edtTenMon.setText(String.valueOf(menu.getName()));
        edtPrice = alert.findViewById(R.id.edtPriceFood);
        edtPrice.setText(String.valueOf(menu.getPrice()));
        TextView tvType = alert.findViewById(R.id.tvType);
        tvType.setText(String.valueOf(menu.getType()));
        imvFood = alert.findViewById(R.id.imgAvtFood);
        Glide.with(getContext()).load(Constants.LINK + menu.getImage()).into(imvFood);

        btnCancel = alert.findViewById(R.id.btnCancel);
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

        btnUpdate = alert.findViewById(R.id.btnAddFood);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String tenmon = edtTenMon.getText().toString();
                Integer price = Integer.parseInt(edtPrice.getText().toString());
                String type = tvType.getText().toString();
                boolean status = true;
                if (edtTenMon.getText().toString().isEmpty() || edtPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (uriImage != null) {
                    File file = new File(Support.getPathFromUri(getContext(), uriImage));
                    RequestBody requestBody = RequestBody.create(MediaType.parse(
                            getContext().getContentResolver().getType(uriImage)), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                            "avatar", file.getName(), requestBody);
                    retrofitAPI.updateDrink(menu.getId(), tenmon, price, status, type, filePart).enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.detach(MenuDrinkFragment.this).attach(MenuDrinkFragment.this).commit();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                            Log.e("onFailure: ", t.getMessage());
                        }
                    });
                } else {
                    retrofitAPI.updateDrinkNoImage(menu.getId(), tenmon, price, status, type).enqueue(new Callback<ServerResponse>() {
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