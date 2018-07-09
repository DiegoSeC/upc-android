package com.barberia.upc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barberia.upc.barberupc.MainActivity;
import com.barberia.upc.barberupc.R;
import com.barberia.upc.models.Barber;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarberFragment extends Fragment {

    private final static String BARBER_KEY = "BARBER";

    Barber barber;

    TextView barberName;
    TextView barberDescription;
    ImageView barberPicture;
    Button reservation;

    LinearLayout pointsLayout;
    Context context;

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
        context = view.getContext();

        Log.d("BARBER", "Barber name: " + barber.getName());

        barberName = view.findViewById(R.id.barber_name_info);
        barberDescription = view.findViewById(R.id.barber_description_info);
        barberPicture = view.findViewById(R.id.barber_picture_info);
        pointsLayout = view.findViewById(R.id.points_layout);
        reservation = view.findViewById(R.id.reservation_button);

        barberName.setText(barber.getName());
        barberDescription.setText(barber.getBio());

        Glide.with(view.getContext())
                .load(barber.getPicture())
                .into(barberPicture);

        setPointsImages();

        reservation.setOnClickListener(goToAppointment());

        return view;
    }

    private void setPointsImages() {
        Log.d("BARBER", "Points to show: " + barber.getRank());

        for (int i = 0; i < barber.getRank(); i++) {
            ImageView star = new ImageView(context);
            star.setImageResource(R.drawable.ic_star_black_24dp);
            star.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            pointsLayout.addView(star);
        }
    }

    public View.OnClickListener goToAppointment() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentFragment appointmentFragment = new AppointmentFragment();
                FragmentTransaction fragmentTransaction = ((MainActivity) v.getContext())
                        .getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.frame_layout_view, appointmentFragment);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        };
    }

}
