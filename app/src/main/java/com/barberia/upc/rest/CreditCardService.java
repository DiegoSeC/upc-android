package com.barberia.upc.rest;

import com.barberia.upc.models.CreditCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CreditCardService {
    @GET("credit_card")
    Call<List<CreditCard>> getCreditCards();

    @POST("credit_card")
    Call<CreditCard> saveCreditCard(@Body CreditCard creditCard);
}
