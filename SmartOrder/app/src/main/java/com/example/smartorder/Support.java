package com.example.smartorder;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Support {
    public static void  replaceFragment(FragmentManager fragmentManager, int layoutId, Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId,fragment);
        fragmentTransaction.commit();
    }
    public static void  replaceFragmentAndBackStack(FragmentManager fragmentManager, int layoutId, Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId,fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }
}
