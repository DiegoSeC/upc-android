package com.barberia.upc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barberia.upc.adapters.HairCutAdapter;
import com.barberia.upc.barberupc.R;
import com.barberia.upc.decoration.SimpleDividerItemDecoration;
import com.barberia.upc.models.HairCut;
import com.barberia.upc.rest.HairCutService;
import com.barberia.upc.util.Session;
import com.barberia.upc.util.TokenInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class HairCutFragment extends Fragment {

    List<HairCut> hairCutList;

    HairCutAdapter hairCutAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView hairCutRecyclerView;
    Context context;
    Session session;
    Retrofit retrofit;
    HairCutService hairCutService;

    public HairCutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hair_cut, container, false);

        context = view.getContext();

        session = new Session(context);
        String token = session.getToken();

        hairCutList = new ArrayList<>();

        hairCutAdapter = new HairCutAdapter(hairCutList);
        linearLayoutManager = new LinearLayoutManager(view.getContext());

        hairCutRecyclerView = view.findViewById(R.id.hair_cut_recycler);

        hairCutRecyclerView.setAdapter(hairCutAdapter);
        hairCutRecyclerView.setLayoutManager(linearLayoutManager);
        hairCutRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(view.getContext()));

        TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
        OkHttpClient client = tokenInterceptor.makeClient();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://upc.diegoseminario.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        hairCutService = retrofit.create(HairCutService.class);

        getHairCuts();

        return view;
    }

    public  void getHairCuts(){
        Call<List<HairCut>> call = hairCutService.getAllHairCut();
        call.enqueue(cbHairCuts());
    }

    public Callback<List<HairCut>> cbHairCuts(){
        return new Callback<List<HairCut>>() {
            @Override
            public void onResponse(Call<List<HairCut>> call, Response<List<HairCut>> response) {
                hairCutList = response.body();
                hairCutAdapter.setHairCutList(hairCutList);
                hairCutAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<HairCut>> call, Throwable t) {

            }
        };
    }
}
