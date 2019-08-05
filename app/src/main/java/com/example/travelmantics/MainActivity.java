package com.example.travelmantics;

import android.app.Activity;
import android.app.Application;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity implements iSwitch {
    FirebaseUtil firebaseUtil = FirebaseUtil.getInstance();
    Boolean Switch =false;
    Offers offers;
    Offers offers2 = new Offers();
    Menu menu ;
    Toolbar toolbar;
    EditText t;
    EditText D;
    EditText p;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Travelmantics");
        firebaseUtil.open("Traveldeals",this);

        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu = menu;
        toolbar.setTitle("Travelmantics");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(firebaseUtil.getAdmin() == false)
        {
            menu.findItem(R.id.newoffer).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.newoffer).setVisible(true);
        }

        switch (item.getItemId())

        {
            case R.id.logout:
                firebaseUtil.Signoff();

                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.Del:
                // User chose the "Favorite" action, mark the current item
                  firebaseUtil.del(offers.getId());
                  init();

                // as a favorite...
                return true;

            case R.id.Save:

                if(getoffer() == null)
                {

                    SaveNewoffer();
                    Toast.makeText(this.getBaseContext(), "Saved", Toast.LENGTH_LONG);
                }

                offers.setTitle(t.getText().toString());
                offers.setDescription(D.getText().toString());
                offers.setPrice(p.getText().toString());
                firebaseUtil.update(offers);
                Toast.makeText(this.getBaseContext(), "Saved", Toast.LENGTH_LONG);


                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.newoffer:
                Fragment EditProduct = new EditProduct();
                Switchfragment(EditProduct,true,"",0);
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

               default:
                   return true;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseUtil.dettachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseUtil.attachListener();
    }

    private void Switchfragment(Fragment fragment, Boolean back, String tag, int number)
    {
        FragmentTransaction Ft = getSupportFragmentManager().beginTransaction();

        Ft.replace(R.id.Container,fragment,tag);
        if(back)
        {
            Ft.addToBackStack(tag);
        }
        Ft.commit();
    }
    void init()
    {
        Fragment list = new List();
        Switchfragment( list,false,"List",0);
    }
    @Override
    public FirebaseUtil getlist()
    {
        return firebaseUtil;
    }

    @Override
    public void setswap(Fragment fragment, Boolean back, String tag)
    {
        Switchfragment(fragment,back, tag,0);
    }

    @Override
    public void setproduct(int adapterPosition)
    {
        Fragment product = new EditProduct();
        offers = firebaseUtil.list.get(adapterPosition);
        Switchfragment(product,true,"Product",0);
    }

    @Override
    public Menu getMenu()
    {

        return menu;

    }

    @Override
    public Offers getoffer() {

        return offers;
    }

    @Override
    public void Upload(final Uri dic)
    {

     mStorageRef = firebaseUtil.mStorageRef;


     if(offers != null)
     {
         firebaseUtil.delphoto(offers);
         mStorageRef =  mStorageRef.child(dic.getLastPathSegment());
         mStorageRef.putFile(dic).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
             {
                 String name  = taskSnapshot.getMetadata().getName();
                 Task<Uri> url = firebaseUtil.mStorageRef.child(name).getDownloadUrl();
                 offers.setName(name);
                 offers.setImageurl(url.toString());

             }
         });

     }
     else
         {
           mStorageRef =  mStorageRef.child(dic.getLastPathSegment());
                 mStorageRef.putFile(dic).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
             {
                 String name  = taskSnapshot.getMetadata().getName();
                 Task<Uri> url = firebaseUtil.mStorageRef.child(name).getDownloadUrl();
                 offers2.setName(name);
                 offers2.setImageurl(url.toString());

             }
         });
     }
    }


    @Override
    public void SaveNewoffer()
    {
        offers2.setTitle(t.getText().toString());
        offers2.setDescription(D.getText().toString());
        offers2.setPrice(p.getText().toString());
        firebaseUtil.save(offers2);

    }


    @Override
    public void settoolbar(String new_offer)
    {
        toolbar.setTitle(new_offer);

    }

    @Override
    public void setovject(EditText title, EditText descrip, EditText price)
    {
        t = title;
        D = descrip;
        p = price;

    }

    @Override
    public void setoffer()
    {
        offers = null;
        offers2 = new Offers();

    }

    @Override
    public StorageReference Image(String name)
    {
        return firebaseUtil.getImage(name);
    }

    void addproduct()
    {
        Fragment product = new EditProduct();
        Switchfragment(product,true,"Product",0);

    }



}
