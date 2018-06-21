package com.barberia.upc.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ReservationService {
    @GET("reservation")
    Call<List<Reservation>> listAll();
}
