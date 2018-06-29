package com.barberia.upc.rest;

import com.barberia.upc.models.CreditCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CreditCardService {
    @GET("credit_card")
    Call<List<CreditCard>> getCreditCards();
}
