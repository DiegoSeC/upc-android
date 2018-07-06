package com.barberia.upc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barberia.upc.barberupc.R;
import com.barberia.upc.models.Barber;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarberFragment extends Fragment {

    private final static String BARBER_KEY = "BARBER";

    Barber barber;

    TextView barberName;
    TextView barberDescription;

    public static BarberFragment newInstance(Barber barber) {
        BarberFragment barberFragment = new BarberFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BARBER_KEY, barber);
        barberFragment.setArguments(bundle);
        return barberFragment;
    }


    public BarberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barber, container, false);

        barber = (Barber) getArguments().getSerializable(BARBER_KEY);
        Log.d("BARBER", "Barber name: " + barber.getName());

        barberName = view.findViewById(R.id.barber_name_info);
        barberDescription = view.findViewById(R.id.barber_description_info);

        barberName.setText(barber.getName());
        barberDescription.setText(barber.getBio());

        return view;
    }

}
