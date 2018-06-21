package com.barberia.upc.com.barberia.upc.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    String token;

    public TokenInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder ongoing = chain.request().newBuilder();
        ongoing.addHeader("Authorization", "Bearer " + this.token);
        return chain.proceed(ongoing.build());
    }

    public OkHttpClient makeClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(token))
                .build();
    }
}
