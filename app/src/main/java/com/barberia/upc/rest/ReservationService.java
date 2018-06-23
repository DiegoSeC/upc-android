package com.barberia.upc.rest;

import com.barberia.upc.models.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReservationService {
    @GET("reservation")
    Call<List<Reservation>> listAll();
}
