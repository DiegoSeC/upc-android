package com.barberia.upc.rest;

import com.barberia.upc.models.Barber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BarberService {
    @GET("barber")
    Call<List<Barber>> getAllBarber();
}
