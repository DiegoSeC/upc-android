package com.barberia.upc.rest;

import com.barberia.upc.models.HairCut;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface HairCutService {
    @GET("hair_cut")
    Call<List<HairCut>> getAllHairCut();
}
