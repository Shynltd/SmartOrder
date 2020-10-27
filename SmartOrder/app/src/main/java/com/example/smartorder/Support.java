package com.example.smartorder;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Support {
    public void replaceFragment(FragmentManager fragmentManager, int layoutId, Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId,fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }
}
