package com.barberia.upc.rest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationService {
    @POST("auth")
    Call<Authentication> auth(@Body Authentication auth);
}
