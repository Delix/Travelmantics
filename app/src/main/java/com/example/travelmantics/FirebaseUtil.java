package com.example.travelmantics;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class FirebaseUtil
{
    private static final int RC_SIGN_IN = 101 ;

    Activity activity;
    String refer;


    private AuthStateListener authStateListener ;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance() ;
    private StorageReference mStorageRef;
   // mStorageRef = FirebaseStorage.getInstance().getReference();

    private  DatabaseReference reference;
    private  FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
    public ArrayList<Offers> list = new ArrayList<Offers>();

    private static final FirebaseUtil ourInstance = new FirebaseUtil();
    private static Boolean isAdmin = false;
    public Adapter adapter;
    private Boolean open = false;

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
                        Toast.makeText(activity, "Welcome Back", Toast.LENGTH_LONG);
                         checkadmin(mAuth.getUid());

                    }


                }


            };

            reference = mfirebaseDatabase.getReference().child(refer);
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Offers offers = dataSnapshot.getValue(Offers.class);
                    offers.setId(dataSnapshot.getKey());
                    list.add(offers);
                    adapter.notifyDataSetChanged();

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
  open = true;
        }



    }

    private void checkadmin(String uid)
    {
        setAdmin(false);
        DatabaseReference reference = mfirebaseDatabase.getReference().child("Admin").child(uid);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                setAdmin(true);

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

    private void Signin()
    {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);

    }

    public void Signoff()
    {
        AuthUI.getInstance()
                .signOut(activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        activity.finish();
                        System.exit(0);
                    }
                });
    }


    public void attachListener()
    {
        mAuth.addAuthStateListener(authStateListener);
    }
    public void dettachListener()
    {
        mAuth.addAuthStateListener(authStateListener);
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
