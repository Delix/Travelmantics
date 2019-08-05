package com.example.travelmantics;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import static android.app.Activity.RESULT_OK;
import java.io.IOException;



/**
 * A simple {@link Fragment} subclass.
 */
public class EditProduct extends Fragment
{
    EditText title;
    EditText Descrip;
    EditText price;
    ImageView image;
    Button button;
    Uri dic;
    MenuItem save;
    iSwitch iswitch;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iswitch.getMenu().findItem(R.id.Del).setVisible(true);
        iswitch.getMenu().findItem(R.id.newoffer).setVisible(false);
        save = iswitch.getMenu().findItem(R.id.Save);
                save.setVisible(true);








    }
    public EditProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_edit_product, container, false);
        title = view.findViewById(R.id.Title);
        Descrip = view.findViewById(R.id.Des);
        price = view.findViewById(R.id.price);
        image = view.findViewById(R.id.imageView2);

        iswitch.setovject(title,Descrip,price);
        button = view.findViewById(R.id.imagebutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                showPickImageDialog();




            }
        });



        if (iswitch.getoffer()== null)
        {
            button.setText("Upload Image");


        }
        else
        {
            title.setText(iswitch.getoffer().getTitle());
            Descrip.setText(iswitch.getoffer().getDescription());
            price.setText(iswitch.getoffer().getPrice());
            if(iswitch.getlist().getAdmin())
            {
                button.setVisibility(View.GONE);
            }

            Glide
                    .with(getContext())
                    .load(iswitch.Image(iswitch.getoffer().getName()))
                    .into(image);



        }

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            dic = data.getData();
            iswitch.Upload(dic);

            Glide.with(this)
                    .load(dic)
                    .into(image);
        }
    }



    private void showPickImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Gallery");


        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, 1);





                    }
                });
        builderSingle.show();
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        iswitch  =  (iSwitch) getActivity();
    }

    @Override
    public void onDestroyView()
    {
        iswitch.setoffer();
        super.onDestroyView();

    }
}
