package com.barberia.upc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.barberia.upc.adapters.CreditCardAdapter;
import com.barberia.upc.barberupc.R;
import com.barberia.upc.decoration.SimpleDividerItemDecoration;
import com.barberia.upc.models.CreditCard;
import com.barberia.upc.models.User;
import com.barberia.upc.rest.CreditCardService;
import com.barberia.upc.rest.UserService;
import com.barberia.upc.util.Session;
import com.barberia.upc.util.TokenInterceptor;
import com.bumptech.glide.Glide;

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
public class ProfileFragment extends Fragment {

    Session session;

    Retrofit retrofit;

    UserService userService;
    CreditCardService creditCardService;

    CreditCardAdapter creditCardAdapter;
    LinearLayoutManager linearLayoutManager;

    Context context;

    LinearLayout userInfoLinearLayout;
    TextView userNameTextView;
    TextView emailTextView;
    TextView profileLoading;
    ImageView userPicture;
    EditText userPhoneNumber;
    EditText userAddress;
    RecyclerView creditRecyclerView;

    List<CreditCard> creditCards;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = view.getContext();

        session = new Session(context);
        TokenInterceptor tokenInterceptor = new TokenInterceptor(session.getToken());

        OkHttpClient client = tokenInterceptor.makeClient();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://upc.diegoseminario.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
        creditCardService = retrofit.create(CreditCardService.class);

        userInfoLinearLayout = view.findViewById(R.id.user_info);
        userNameTextView = view.findViewById(R.id.user_name);
        emailTextView = view.findViewById(R.id.user_email);
        profileLoading = view.findViewById(R.id.profile_loading);
        userPicture = view.findViewById(R.id.user_picture);
        userPhoneNumber = view.findViewById(R.id.user_phoneNumber);
        userAddress = view.findViewById(R.id.user_address);
        creditRecyclerView = view.findViewById(R.id.credit_card_recycler_view);

        creditCards = new ArrayList<>();
        creditCardAdapter = new CreditCardAdapter(creditCards, view.getContext());
        linearLayoutManager = new LinearLayoutManager(view.getContext());

        creditRecyclerView.setAdapter(creditCardAdapter);
        creditRecyclerView.setLayoutManager(linearLayoutManager);
        creditRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(view.getContext()));

        getCurrentUser();
        getCreditCards();

        return view;
    }

    private void getCurrentUser() {
        Call<User> call = userService.getCurrentUser();
        call.enqueue(cbGetUser());
    }

    private Callback<User> cbGetUser() {
        return new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Log.d("BARBER", "User Name: " + user.getName());

                userNameTextView.setText(user.getName());
                emailTextView.setText(user.getEmail());
                userPhoneNumber.setText(user.getPhoneNumber());
                userAddress.setText(user.getAddress());

                Glide.with(context)
                        .load(user.getPicture())
                        .into(userPicture);

                profileLoading.setVisibility(View.GONE);
                userInfoLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("BARBER", t.getLocalizedMessage());
                Toast.makeText(context, "Couldn't load User Info", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void getCreditCards() {
        Call<List<CreditCard>> call = creditCardService.getCreditCards();
        call.enqueue(cbGetCreditCards());
    }

    private Callback<List<CreditCard>> cbGetCreditCards() {
        return new Callback<List<CreditCard>>() {
            @Override
            public void onResponse(Call<List<CreditCard>> call, Response<List<CreditCard>> response) {
                List<CreditCard> creditCards = response.body();
                Log.d("BARBER", "Credit cards size: " + creditCards.size());
                creditCardAdapter.setCreditCards(creditCards);
                creditCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CreditCard>> call, Throwable t) {
                Log.d("BARBER", t.getLocalizedMessage());
                Toast.makeText(context, "Couldn't load Credit Cards Info", Toast.LENGTH_LONG).show();
            }
        };
    }

}
