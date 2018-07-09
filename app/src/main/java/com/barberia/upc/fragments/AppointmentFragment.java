package com.barberia.upc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.barberia.upc.adapters.CreditCardAdapter;
import com.barberia.upc.barberupc.R;
import com.barberia.upc.models.CreditCard;
import com.barberia.upc.rest.CreditCardService;
import com.barberia.upc.util.Session;
import com.barberia.upc.util.TokenInterceptor;

import java.util.ArrayList;
import java.util.Date;
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
public class AppointmentFragment extends Fragment {

    CalendarView calendarView;
    RecyclerView paymentList;

    CreditCardAdapter creditCardAdapter;
    LinearLayoutManager linearLayoutManager;
    List<CreditCard> creditCardList;

    Session session;
    Retrofit retrofit;
    TokenInterceptor tokenInterceptor;
    CreditCardService creditCardService;


    public AppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        paymentList = view.findViewById(R.id.payment_list);

        creditCardList = new ArrayList<>();
        creditCardAdapter = new CreditCardAdapter(creditCardList, view.getContext());
        linearLayoutManager = new LinearLayoutManager(view.getContext());

        paymentList.setAdapter(creditCardAdapter);
        paymentList.setLayoutManager(linearLayoutManager);

        session = new Session(view.getContext());
        tokenInterceptor = new TokenInterceptor(session.getToken());

        OkHttpClient client = tokenInterceptor.makeClient();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://upc.diegoseminario.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        creditCardService = retrofit.create(CreditCardService.class);

        setCalendar();
        setRecyclerView();

        return view;
    }

    public void setCalendar() {
        Date today = new Date();
        calendarView.setDate(today.getTime());
        calendarView.setMinDate(today.getTime());
    }

    public void setRecyclerView() {
        Call<List<CreditCard>> call = creditCardService.getCreditCards();
        call.enqueue(new Callback<List<CreditCard>>() {
            @Override
            public void onResponse(Call<List<CreditCard>> call, Response<List<CreditCard>> response) {
                creditCardList = response.body();
                creditCardAdapter.setCreditCards(creditCardList);
                creditCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CreditCard>> call, Throwable t) {

            }
        });
    }

}
