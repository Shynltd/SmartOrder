package com.example.smartorder.fragment.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.smartorder.R;
import com.example.smartorder.adapter.admin.TabMenuAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.model.response.ServerResponse;
import com.example.smartorder.support.Support;
import com.google.android.material.tabs.TabLayout;
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

public class MenuFragment extends Fragment {

    private RetrofitAPI retrofitAPI;
    private TabLayout tabMenu;
    private ViewPager vpMenu;
    private FloatingActionButton fabAddMenu;
    private EditText edtTenMon;
    private EditText edtPrice;
    private EditText edAmonut;
    private TextView tvAmount;
    private ImageView imvFood;
    private Spinner spnType;
    private Button btnAdd, btnCancel;

    private String type;
    private Uri uriImage = null;
    private int REQUEST_CODE_LOAD_IMAGE = 01234;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vpMenu.setAdapter(new TabMenuAdapter(getFragmentManager()));
        tabMenu.setupWithViewPager(vpMenu);
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        fabAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddMenu();

            }
        });
    }

    private void dialogAddMenu() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        View alert = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_menu, null);
        alertDialog.setView(alert);
        alertDialog.setTitle("Thêm mới món ăn");
        alertDialog.setCancelable(false);

        List<String> mListSpinner = new ArrayList<>();
        mListSpinner.add("Drink");
        mListSpinner.add("Food");

        //ánh xạ view
        tvAmount = alert.findViewById(R.id.tvAmountFood);
        edtTenMon = alert.findViewById(R.id.edtNameFood);
        edtPrice = alert.findViewById(R.id.edtPriceFood);
        edAmonut = alert.findViewById(R.id.edtAmountFood);
        imvFood = alert.findViewById(R.id.imgAvtFood);
        spnType = alert.findViewById(R.id.spnTypeFood);
        btnAdd = alert.findViewById(R.id.btnAddFood);
        btnCancel = alert.findViewById(R.id.btnCancel);

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, mListSpinner);
        spnType.setAdapter(arrayAdapter);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = mListSpinner.get(position);
                switch (position) {
                    case 0:
                        tvAmount.setVisibility(View.VISIBLE);
                        edAmonut.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tvAmount.setVisibility(View.INVISIBLE);
                        edAmonut.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                if(edAmonut.getText().toString().isEmpty()||edtTenMon.getText().toString().isEmpty()||
                        edtPrice.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else {
                    String tenmon = edtTenMon.getText().toString();
                    Integer pricae = Integer.parseInt(edtPrice.getText().toString());
                    File file = new File(Support.getPathFromUri(getContext(), uriImage));
                    RequestBody requestBody = RequestBody.create(MediaType.parse(
                            getContext().getContentResolver().getType(uriImage)), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                            "avatar", file.getName(), requestBody);
                    Integer amonut;
                    if (type.equals("Food")) {
                        amonut = Integer.parseInt("0");
                    } else {
                        amonut = Integer.parseInt(edAmonut.getText().toString().trim());
                    }

                    retrofitAPI.createFood(tenmon,pricae,amonut,type,filePart)
                            .enqueue(new Callback<ServerResponse>() {
                                @Override
                                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
//                   FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                   fragmentTransaction.detach(MenuFragment.this).attach(MenuFragment.this).commit();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            uriImage = uri;
            imvFood.setImageURI(uri);
        }
    }

    private void initView(View view) {
        tabMenu = (TabLayout) view.findViewById(R.id.tabMenu);
        vpMenu = (ViewPager) view.findViewById(R.id.vpMenu);
        fabAddMenu = (FloatingActionButton) view.findViewById(R.id.fabAddMenu);
    }
}