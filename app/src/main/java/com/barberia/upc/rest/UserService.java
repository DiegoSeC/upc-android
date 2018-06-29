package com.barberia.upc.rest;

import com.barberia.upc.models.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("user")
    Call<User> getCurrentUser();
}
