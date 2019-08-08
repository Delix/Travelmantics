package com.example.travelmantics;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    Uri imagephoto;
    int number;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Travelmantics");
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu = menu;
        firebaseUtil.open("Traveldeals",this);
        menu.findItem(R.id.Save).setVisible(false);
        menu.findItem(R.id.Del).setVisible(false);
        if(firebaseUtil.getAdmin() == false)
        {

            menu.findItem(R.id.newoffer).setVisible(false);
            menu.findItem(R.id.Save).setVisible(false);
            menu.findItem(R.id.Del).setVisible(false);


        }
        else
        {

            menu.findItem(R.id.newoffer).setVisible(true);
        }
        toolbar.setTitle("Travelmantics");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {


        switch (item.getItemId())

        {
            case R.id.logout:
                firebaseUtil.Signoff();

                return true;

            case R.id.Del:

                  firebaseUtil.list.remove(number);
                  firebaseUtil.del(offers.getId());
                  init();
                Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();

                return true;

            case R.id.Save:

                if(getoffer() == null)
                {
                    Upload(imagephoto);

                }
                else
                    {
                        if(imagephoto != null)
                        {
                            Upload(imagephoto);
                        }
                        else
                            {
                            offers.setTitle(t.getText().toString());
                            offers.setDescription(D.getText().toString());
                            offers.setPrice(p.getText().toString());
                            firebaseUtil.update(offers);
                        }
                }

                init();
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();


                return true;

            case R.id.newoffer:

                Fragment EditProduct = new EditProduct();
                Switchfragment(EditProduct,true,"View");

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
        firebaseUtil.open("Traveldeals",this);
        firebaseUtil.attachListener();
    }

    private void Switchfragment(Fragment fragment, Boolean back, String tag)
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

        Switchfragment( list,false,"List");

    }
    @Override
    public FirebaseUtil getlist()
    {
        return firebaseUtil;
    }


    @Override
    public void setproduct(int adapterPosition)
    {
        Fragment product = new EditProduct();
        offers = firebaseUtil.list.get(adapterPosition);
        number = adapterPosition;
        Switchfragment(product,true,"View");
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
                 final String name  = taskSnapshot.getMetadata().getName();
                 Task<Uri> url = firebaseUtil.mStorageRef.child(name).getDownloadUrl();
                 url.addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri)
                     {
                         offers.setImageurl(uri.toString());
                         offers.setName(name);
                         offers.setTitle(t.getText().toString());
                         offers.setDescription(D.getText().toString());
                         offers.setPrice(p.getText().toString());
                         firebaseUtil.update(offers);

                     }
                 });


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
                 final String name  = taskSnapshot.getMetadata().getName();
                 Task<Uri> url = firebaseUtil.mStorageRef.child(name).getDownloadUrl();
                 url.addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri)
                     {

                         offers2.setImageurl(uri.toString());
                         offers2.setName(name);
                         SaveNewoffer();

                     }
                 });



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
    public void setphoto(Uri dic)
    {
        imagephoto = dic;

    }


    public void showMenu()
    {
        supportInvalidateOptionsMenu();
        invalidateOptionsMenu();

    }
}
