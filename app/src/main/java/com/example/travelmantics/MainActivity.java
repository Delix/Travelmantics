package com.example.travelmantics;

import android.app.Application;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements iSwitch {
    FirebaseUtil firebaseUtil = FirebaseUtil.getInstance();
    Boolean Switch =false;
    Offers offers;
    Menu menu ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
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
       if(firebaseUtil.getAdmin() == false)
        {
            menu.findItem(R.id.newoffer).setVisible(false);
        }
       else
       {
           menu.findItem(R.id.newoffer).setVisible(true);
       }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId())

        {
            case R.id.logout:
                firebaseUtil.Signoff();

                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.Del:
                // User chose the "Favorite" action, mark the current item
                //firebaseUtili.del();
                  init();

                // as a favorite...
                return true;

            case R.id.Save:
                //firebaseUtili.save();
                Toast.makeText(this,"Saved",Toast.LENGTH_LONG);

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

    void addproduct()
    {
        Fragment product = new EditProduct();
        Switchfragment(product,true,"Product",0);
    }

}
