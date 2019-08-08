package com.example.travelmantics;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;



import com.bumptech.glide.Glide;


import static android.app.Activity.RESULT_OK;




/**
 * A simple {@link Fragment} subclass.
 */
public class EditProduct extends Fragment
{
    EditText title;
    EditText Descrip;
    EditText price;
    ImageView image;
    private static final int PICTURE_RESULT = 42;
    Button button;
    Uri dic;
    MenuItem save;
    iSwitch iswitch;




    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        if(iswitch.getlist().getAdmin())
        {
            menu.findItem(R.id.newoffer).setVisible(false);
            menu.findItem(R.id.Save).setVisible(true);
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.Del).setVisible(true);
        }
        else
        {
            menu.findItem(R.id.newoffer).setVisible(false);
            menu.findItem(R.id.Save).setVisible(false);
            menu.findItem(R.id.logout).setVisible(true);
            menu.findItem(R.id.Del).setVisible(false);

        }

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
        setHasOptionsMenu(true);
        button = view.findViewById(R.id.imagebutton);

        if (iswitch.getlist().getAdmin())
        {

            enableEditTexts(true);
        }
        else
        {

            enableEditTexts(false);
        }





        if (iswitch.getoffer()== null)
        {
            button.setText("Upload Image");


        }
        else
        {
            title.setText(iswitch.getoffer().getTitle());
            Descrip.setText(iswitch.getoffer().getDescription());
            price.setText(iswitch.getoffer().getPrice());
            button.setText("Select Image");

          Glide
                    .with(this)
                    .load(iswitch.getoffer().getImageurl())
                    .into(image);



        }

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 42 && resultCode == RESULT_OK)
        {
            dic = data.getData();
            iswitch.setphoto(dic);

            Glide.with(this)
                    .load(dic)
                    .into(image);
        }

    }


    private void showPickImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this.getContext());


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this.getContext(),
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
                        startActivityForResult(pickPhoto, PICTURE_RESULT);





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

    private void enableEditTexts(boolean isEnabled)
    {
        title.setEnabled(isEnabled);
        Descrip.setEnabled(isEnabled);
        price.setEnabled(isEnabled);
        if(!isEnabled)
        {
            button.setVisibility(View.GONE);
        }
        else
            {

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        showPickImageDialog();




                    }
                });
        }



    }
}
