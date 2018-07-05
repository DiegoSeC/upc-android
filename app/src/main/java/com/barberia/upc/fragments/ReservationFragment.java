package com.barberia.upc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barberia.upc.adapters.BarberAdapter;
import com.barberia.upc.barberupc.R;
import com.barberia.upc.decoration.SimpleDividerItemDecoration;
import com.barberia.upc.models.Barber;
import com.barberia.upc.rest.BarberService;
import com.barberia.upc.util.Session;
import com.barberia.upc.util.TokenInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {

    Session session;

    Retrofit retrofit;

    BarberService barberService;

    Context context;

    List<Barber> barberList;

    BarberAdapter barberAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView barberRecyclerView;


    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        context = view.getContext();

        session = new Session(context);
        String token = session.getToken();

        barberList = new ArrayList<>();

        barberRecyclerView = view.findViewById(R.id.barber_recycler_view);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        barberAdapter = new BarberAdapter(barberList);

        barberRecyclerView.setLayoutManager(linearLayoutManager);
        barberRecyclerView.setAdapter(barberAdapter);
        barberRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));

        TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
        OkHttpClient client = tokenInterceptor.makeClient();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://upc.diegoseminario.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        barberService = retrofit.create(BarberService.class);

        this.getBarbers();

        return view;
    }

    public void getBarbers() {
        Call<List<Barber>> call = barberService.getAllBarber();
        call.enqueue(cbBarber());
    }

    public Callback<List<Barber>> cbBarber() {
        return new Callback<List<Barber>>() {
            @Override
            public void onResponse(Call<List<Barber>> call, Response<List<Barber>> response) {
                barberList = response.body();
                barberAdapter.setBarbers(barberList);
                barberAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Barber>> call, Throwable t) {

            }
        };
    }



}
