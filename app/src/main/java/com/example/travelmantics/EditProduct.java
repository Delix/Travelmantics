package com.example.travelmantics;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;

import static androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;


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
    Integer Camera_Request_Code = 5;
    Uri dic;


    iSwitch iswitch;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iswitch.getMenu().findItem(R.id.Del).setVisible(true);
        iswitch.getMenu().findItem(R.id.Save).setVisible(true);





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
        button = view.findViewById(R.id.imagebutton);

        if (iswitch.getoffer()== null)
        {

        }
        else
        {
            title.setText(iswitch.getoffer().getTitle());
            Descrip.setText(iswitch.getoffer().getDescription());
            price.setText(iswitch.getoffer().getPrice());
            Glide
                    .with(getContext())
                    .load(iswitch.getoffer().getImageurl())
                    .into(image);
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

   /*     if ((requestCode == Camera_Request_Code) && (resultCode == RESULT_OK))
        {


            //tabManager.getUser().Picprofile(dic);

            Glide.with(this)
                    .load(dic).apply(RequestOptions.circleCropTransform())
                    .into(image);
        }
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            dic = data.getData();
           // tabManager.getUser().Picprofile(dic);

            Glide.with(this)
                    .load(dic).apply(RequestOptions.circleCropTransform())
                    .into(image);
        }
    }

    private File createImageFile()
    {

        String imageFileName = "JPEG_" +  tabManager.getUser().getUserID() + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;

        try {
            image = File.createTempFile(imageFileName,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        dic = Uri.fromFile(image);
        return image;
    }


    private void showPickImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setTitle("Select One Option");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Gallery");
        arrayAdapter.add("Camera");

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
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, 1);
                                break;

                            case 1:
                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {

                                    File photoFile = null;

                                    photoFile = createImageFile();



                                    if (photoFile != null)
                                    {
                                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                        startActivityForResult(cameraIntent,Camera_Request_Code );

                                    }
                                }
                                break;
                        }

                    }
                });
        builderSingle.show();*/
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        iswitch  =  (iSwitch) getActivity();
    }
}
