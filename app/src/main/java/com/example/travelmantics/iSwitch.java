package com.example.travelmantics;

import android.view.Menu;

import androidx.fragment.app.Fragment;

public interface iSwitch
{
    FirebaseUtil getlist();
    void setswap(Fragment fragment, Boolean back, String tag);
    void setproduct(int adapterPosition);
    Menu getMenu();
    Offers getoffer();

}
