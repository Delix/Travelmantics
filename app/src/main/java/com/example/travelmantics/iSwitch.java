package com.example.travelmantics;

import android.net.Uri;
import android.text.Editable;
import android.view.Menu;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public interface iSwitch
{
    FirebaseUtil getlist();
    void setswap(Fragment fragment, Boolean back, String tag);
    void setproduct(int adapterPosition);
    Menu getMenu();
    Offers getoffer();
    void Upload(Uri dic);
    void SaveNewoffer();

    void settoolbar(String new_offfer);

    void setovject(EditText title, EditText descrip, EditText price);

    void setoffer();
    StorageReference Image(String name);
}
