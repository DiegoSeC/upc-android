package com.barberia.upc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barberia.upc.adapters.HairCutAdapter;
import com.barberia.upc.barberupc.R;
import com.barberia.upc.decoration.SimpleDividerItemDecoration;
import com.barberia.upc.models.HairCut;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HairCutFragment extends Fragment {

    List<HairCut> hairCutList;

    HairCutAdapter hairCutAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView hairCutRecyclerView;


    public HairCutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hair_cut, container, false);

        hairCutList = new ArrayList<>();

        hairCutAdapter = new HairCutAdapter(hairCutList);
        linearLayoutManager = new LinearLayoutManager(view.getContext());

        hairCutRecyclerView = view.findViewById(R.id.hair_cut_recycler);

        hairCutRecyclerView.setAdapter(hairCutAdapter);
        hairCutRecyclerView.setLayoutManager(linearLayoutManager);
        hairCutRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(view.getContext()));

        return view;
    }

}
