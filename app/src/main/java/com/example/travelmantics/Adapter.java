package com.example.travelmantics;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    Context activity;
    FirebaseUtil firebaseUtil = FirebaseUtil.getInstance();
    public ArrayList<Offers> list;
    private  iSwitch Iswitch;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;


  public Adapter(Context activity,iSwitch Iswitch)
    {
             this.activity = activity;
             this.Iswitch = Iswitch;
        mFirebaseDatabase = firebaseUtil.mfirebaseDatabase;
        mDatabaseReference = firebaseUtil.reference;
        this.list = firebaseUtil.list;
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Offers offer = dataSnapshot.getValue(Offers.class);

                offer.setId(dataSnapshot.getKey());
                list.add(offer);
                notifyItemInserted(list.size()-1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };
        mDatabaseReference.addChildEventListener(mChildListener);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(activity).inflate(R.layout.cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Offers offers = list.get(position) ;
        holder.bind(offers);


    }



    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView Title;
        TextView Descri;
        TextView price;
        ImageView image;

        public ViewHolder(@NonNull View itemView)
        {

            super(itemView);
            Title = itemView.findViewById(R.id.n_product);
            Descri = itemView.findViewById(R.id.d_product);
            price = itemView.findViewById(R.id.p_product);
            image = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View view)
        {
            Iswitch.setproduct(getAdapterPosition());

        }

        public void bind(Offers offers)
        {
            Uri url = Uri.parse(offers.getImageurl());
            Title.setText(offers.getTitle());
            price.setText(offers.getPrice());
            Descri.setText(offers.getDescription());

            Glide     .with(activity)
                    .load(url)
                    .into(image);

        }
    }

}
