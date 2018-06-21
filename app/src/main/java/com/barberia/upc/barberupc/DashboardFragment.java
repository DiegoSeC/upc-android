package com.barberia.upc.barberupc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barberia.upc.com.barberia.upc.util.Session;
import com.barberia.upc.com.barberia.upc.util.TokenInterceptor;
import com.barberia.upc.rest.Reservation;
import com.barberia.upc.rest.ReservationService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {

    Retrofit retrofit;

    ReservationService reservationService;

    TextView loadingReservation;

    TextView loadingHistory;

    View view;

    Session session;

    String token;

    public DashboardFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        session = new Session(view.getContext());
        token = session.getToken();

        TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
        OkHttpClient okHttpClient = tokenInterceptor.makeClient();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://upc.diegoseminario.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        reservationService = retrofit.create(ReservationService.class);

        loadingReservation = view.findViewById(R.id.loading_reservation);
        loadingReservation.setText(getResources().getString(R.string.loading_text));

        loadingHistory = view.findViewById(R.id.loading_history);
        loadingHistory.setText(getResources().getString(R.string.loading_text));

        Log.d("TOKEN", token);

        this.getReservation();
        this.getHistory();

        return view;
    }

    private void getReservation() {
        Call<List<Reservation>> reservationCall = reservationService.listAll();
        reservationCall.enqueue(cbReservation());
    }

    private void getHistory() {
        Call<List<Reservation>> historyCall = reservationService.listAll();
        historyCall.enqueue(cbHistory());
    }

    private Callback<List<Reservation>> cbReservation() {
        Callback<List<Reservation>> callback = new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                Log.d("CALLED", "onResponse");

                try {
                    List<Reservation> reservations = response.body();

                    if(reservations.size() == 0) {
                        loadingReservation.setText(getResources().getString(R.string.no_data_found));
                    }

                } catch (Exception e) {
                    loadingReservation.setText(getResources().getString(R.string.no_data_found));
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.d("NETWORK", "ERROR");
                loadingReservation.setText(getResources().getString(R.string.no_data_found));
            }
        };

        return callback;
    }

    private Callback<List<Reservation>> cbHistory() {
        Callback<List<Reservation>> callback = new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                try {
                    List<Reservation> reservations = response.body();

                    if(reservations.size() == 0) {
                        loadingHistory.setText(getResources().getString(R.string.no_data_found));
                    }

                } catch (Exception e) {
                    loadingHistory.setText(getResources().getString(R.string.no_data_found));
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.d("NETWORK", "ERROR");
                loadingHistory.setText(getResources().getString(R.string.no_data_found));
            }
        };

        return callback;
    }
}
