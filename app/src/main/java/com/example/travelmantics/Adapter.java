package com.example.travelmantics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    Context activity;
    iSwitch iSwitch;
    FirebaseUtil firebaseUtil;




  public Adapter(Context activity, iSwitch aISwitch)
    {
             this.activity = activity;
             firebaseUtil= aISwitch.getlist();
             iSwitch = (iSwitch) activity;
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
        StorageReference name = firebaseUtil.getImage(firebaseUtil.list.get(position).getName());

        holder.Title.setText(firebaseUtil.list.get(position).getTitle());
        holder.price.setText(firebaseUtil.list.get(position).getPrice());
        holder.Descri.setText(firebaseUtil.list.get(position).getDescription());

       Glide     .with(activity)
                .load(name)
                .into(holder.image);

    }



    @Override
    public int getItemCount()
    {
        return firebaseUtil.list.size();
    }


    public class  ViewHolder extends RecyclerView.ViewHolder
    {
        TextView Title;
        TextView Descri;
        TextView price;
        ImageView image;
        CardView cardView;

        public ViewHolder(@NonNull View itemView)
        {

            super(itemView);
            cardView = itemView.findViewById(R.id.Card);
            Title = itemView.findViewById(R.id.n_product);
            Descri = itemView.findViewById(R.id.d_product);
            price = itemView.findViewById(R.id.p_product);
            image = itemView.findViewById(R.id.imageView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    iSwitch.setproduct(getAdapterPosition());

                }
            });

        }


    }

}
