package com.example.travelmantics;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class List extends Fragment
{
     iSwitch iswitch;
     Adapter adapter;
     RecyclerView recyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);
        recyclerView =  view.findViewById(R.id.Listview);
        adapter = new Adapter(this.getContext(),iswitch);
        iswitch.getlist().adapter = adapter;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu = iswitch.getMenu();
        menu.findItem(R.id.Del).setVisible(false);
        menu.findItem(R.id.Save).setVisible(false);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        iswitch  =  (iSwitch) getActivity();
    }
}
