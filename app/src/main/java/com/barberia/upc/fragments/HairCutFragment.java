package com.barberia.upc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barberia.upc.barberupc.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HairCutFragment extends Fragment {


    public HairCutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hair_cut, container, false);
    }

}
