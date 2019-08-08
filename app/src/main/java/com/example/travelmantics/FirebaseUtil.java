package com.example.travelmantics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FirebaseUtil
{
    private static final int RC_SIGN_IN = 101 ;

   private Activity activity;
    private   String refer;


    private AuthStateListener authStateListener ;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance() ;

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference mStorageRef;


    public DatabaseReference reference;
    public  FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();


    private static final FirebaseUtil ourInstance = new FirebaseUtil();
    private static Boolean isAdmin = false;

    private Boolean open = false;
    public ArrayList<Offers> list ;

    public static FirebaseUtil getInstance()
    {

        return ourInstance;
    }


    public void  open(String ref, Activity act)
    {
        refer = ref;
        activity = act;

        if(open == false)
        {
            authStateListener = new AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null)
                    {
                        Signin();





                    }
                    else
                    {
                        checkadmin();
                    }


                }


            };

            reference = mfirebaseDatabase.getReference().child(refer);

            open = true;
            ConnectStorage("Deals");
        }
        list = new ArrayList<>();


    }


    private void ConnectStorage(String refer)
    {
        mStorageRef = firebaseStorage.getReference().child(refer);

    }


    private void Signin()
    {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        MainActivity mainActivity = (MainActivity) activity;
// Create and launch sign-in intent
        mainActivity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);



    }

    public  void checkadmin()
    {
        String uid = mAuth.getUid();

        setAdmin(false);
        DatabaseReference reference = mfirebaseDatabase.getReference().child("Admin").child(uid);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                setAdmin(true);
                MainActivity mainActivity = (MainActivity) activity;
                mainActivity.showMenu();
                Log.d("Admin","You are admin");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void Signoff()
    {
        AuthUI.getInstance()
                .signOut(activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                     attachListener();

                    }
                });
        dettachListener();
    }

    public void del(String pos)
    {
        reference = mfirebaseDatabase.getReference().child("Traveldeals");
        reference.child(pos).removeValue();

    }
    public void update(Offers pos)
    {
        reference = mfirebaseDatabase.getReference().child("Traveldeals");
        reference.child(pos.getId()).setValue(pos);

    }



    public void attachListener()
    {
        mAuth.addAuthStateListener(authStateListener);
    }
    public void dettachListener()
    {
        mAuth.removeAuthStateListener(authStateListener);
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void save(Offers offers2)
    {
         reference = mfirebaseDatabase.getReference().child("Traveldeals");
         reference.push().setValue(offers2);

    }

    public void delphoto(Offers offers)
    {
      mStorageRef.child(offers.getName()).delete();
    }

}
