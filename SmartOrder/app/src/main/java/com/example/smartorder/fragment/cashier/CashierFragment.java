package com.example.smartorder.fragment.cashier;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.example.smartorder.R;
import com.example.smartorder.activity.CashierActivity;
import com.example.smartorder.activity.LoginActivity;
import com.example.smartorder.adapter.admin.BillAdapter;
import com.example.smartorder.api.APIModule;
import com.example.smartorder.api.RetrofitAPI;
import com.example.smartorder.constants.Constants;
import com.example.smartorder.fragment.ProfileFragment;
import com.example.smartorder.model.bill.Bill;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashierFragment extends Fragment {

    private EditText edtSearch;
    private RecyclerView rvListBillUnpaid;
    private BillAdapter billAdapter;
    private List<Bill> billList;
    private RetrofitAPI retrofitAPI;
    private CircleImageView imgProfile;
    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cashier, container, false);
        initView(view);
        Glide.with(getActivity()).load(Constants.LINK + Constants.AvatarUser).into(imgProfile);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bill");
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomPopupMenu(view);
            }
        });
        initRecycle();
        getDataFromServer();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
    private void getDataFromServer() {
        retrofitAPI = APIModule.getInstance().create(RetrofitAPI.class);
        retrofitAPI.getListUnpaid().enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                List<Bill> bills = response.body();
                for (int i = 0; i < bills.size(); i++) {
                    String dateBill = bills.get(i).getDateBill();
                    String id = bills.get(i).getId();
                    String billCode = bills.get(i).getBillCode();
                    String nameCashier = bills.get(i).getNameCashier();
                    Integer tableCode = bills.get(i).getTableCode();
                    Integer totalPrice = bills.get(i).getTotalPrice();
                    String status = bills.get(i).getStatus();
                    billList.add(new Bill(dateBill, id, billCode, nameCashier, tableCode, totalPrice, status));
                    billAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Log.e( "CashierActivity: ", t.getMessage());
            }
        });
    }

    private void initRecycle() {
        billList = new ArrayList<>();
        billAdapter = new BillAdapter(getActivity(), billList, new BillAdapter.onItemClick() {
            @Override
            public void onClick(int pos) {
                String billCode = billList.get(pos).getBillCode();
                Integer tableCode = billList.get(pos).getTableCode();
                Integer totalPrice = billList.get(pos).getTotalPrice();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frq, new PayBillFragment(billCode, tableCode, totalPrice), Constants.fragmentPayBill).commit();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvListBillUnpaid.setLayoutManager(gridLayoutManager);
        rvListBillUnpaid.setHasFixedSize(true);
        rvListBillUnpaid.setAdapter(billAdapter);
    }

    private void initView(View view) {
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        rvListBillUnpaid = (RecyclerView) view.findViewById(R.id.rvListBillUnpaid);
        toolbar = view.findViewById(R.id.toolbar);
        imgProfile = (CircleImageView) view.findViewById(R.id.imgProfile);
    }
    private void showCustomPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.my_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        ProfileFragment profileFragment = new ProfileFragment();
                        transaction.setCustomAnimations(R.anim.list_food_bottom_to_top, 0).add(R.id.frq, profileFragment, Constants.fragmentProfile).commit();
                        break;
                    case R.id.logOut:
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        ActivityCompat.finishAffinity(getActivity());
                        getActivity().finish();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}