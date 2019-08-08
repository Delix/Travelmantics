package com.example.travelmantics;

import android.net.Uri;
import android.view.Menu;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public interface iSwitch
{
    FirebaseUtil getlist();
    void setproduct(Offers adapterPosition);
    Menu getMenu();
    Offers getoffer();
    void Upload(Uri dic);
    void SaveNewoffer();

    void settoolbar(String new_offfer);

    void setovject(EditText title, EditText descrip, EditText price);

    void setoffer();
    void setphoto(Uri dic);
}
