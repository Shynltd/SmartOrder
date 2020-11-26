package com.example.smartorder.fragment.admin.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.MenuDrinksAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.fragment.admin.MenuFragment;
import com.example.smartorder.fragment.admin.TableFragment;
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

public class MenuDrinkFragment extends Fragment {

    private RecyclerView rvListMenuDrink;
    private MenuDrinksAdapter menuDrinksAdapter;
    private RetrofitAPI retrofitAPI;
    private List<ListDrink> listDrinks;


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
                builder.setMessage("Bạn có muốn xóa đồ uống "+listDrinks.get(position).getName()+" không ?")
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
                        })
                        .create().show();
            }

            @Override
            public void updateDrink(int position, List<ListDrink> listDrinks) {
                dialogUpdateDrink();
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

    private void dialogUpdateDrink() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        View alert = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_menu, null);
        alertDialog.setView(alert);
        alertDialog.setTitle("Chỉnh sửa thông tin Menu");
        alertDialog.setCancelable(false);

//        List<String> mListSpinner = new ArrayList<>();
//        mListSpinner.add("Drink");
//        mListSpinner.add("Food");
//        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(),
//                R.layout.support_simple_spinner_dropdown_item, mListSpinner);
//        spnType.setAdapter(arrayAdapter);


//        tvAmount = alert.findViewById(R.id.tvAmountFood);
//        edtTenMon = alert.findViewById(R.id.edtNameFood);
//        edtPrice = alert.findViewById(R.id.edtPriceFood);
//        edAmonut = alert.findViewById(R.id.edtAmountFood);
//        imvFood = alert.findViewById(R.id.imgAvtFood);
//        spnType = alert.findViewById(R.id.spnTypeFood);
//        btnAdd = alert.findViewById(R.id.btnAddFood);
//        btnCancel = alert.findViewById(R.id.btnCancel);


//        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                type = mListSpinner.get(position);
//                switch (position) {
//                    case 0:
//                        tvAmount.setVisibility(View.VISIBLE);
//                        edAmonut.setVisibility(View.VISIBLE);
//                        break;
//                    case 1:
//                        tvAmount.setVisibility(View.INVISIBLE);
//                        edAmonut.setVisibility(View.INVISIBLE);
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        imvFood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
//                        REQUEST_CODE_LOAD_IMAGE);
//            }
//        });
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onClick(View v) {
//
//                if(edAmonut.getText().toString().isEmpty()||edtTenMon.getText().toString().isEmpty()||
//                        edtPrice.getText().toString().isEmpty()){
//                    Toast.makeText(getContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
//                }else {
//                    String tenmon = edtTenMon.getText().toString();
//                    Integer pricae = Integer.parseInt(edtPrice.getText().toString());
//                    File file = new File(Support.getPathFromUri(getContext(), uriImage));
//                    RequestBody requestBody = RequestBody.create(MediaType.parse(
//                            getContext().getContentResolver().getType(uriImage)), file);
//                    MultipartBody.Part filePart = MultipartBody.Part.createFormData(
//                            "avatar", file.getName(), requestBody);
//                    Integer amonut;
//                    if (type.equals("Food")) {
//                        amonut = 0;
//                    } else {
//                        amonut = Integer.parseInt(edAmonut.getText().toString().trim());
//                    }
//
//                    retrofitAPI.createFood(tenmon, pricae, amonut, type,filePart)
//                            .enqueue(new Callback<ServerResponse>() {
//                                @Override
//                                public void onResponse(Call<ServerResponse> call,
//                                                       Response<ServerResponse> response) {
//                                    Toast.makeText(getContext(), response.body().getMessage(),
//                                            Toast.LENGTH_SHORT).show();
//                                    FragmentTransaction fragmentTransaction = getFragmentManager().
//                                            beginTransaction();
//                                    fragmentTransaction.detach(MenuFragment.this).
//                                            attach(MenuFragment.this).commit();
//                                    alertDialog.dismiss();
//                                }
//
//                                @Override
//                                public void onFailure(Call<ServerResponse> call, Throwable t) {
//                                    Log.e("onFailure: ", t.getMessage());
//
//                                }
//                            });
//                }
//            }
//        });
        alertDialog.show();
    }
}