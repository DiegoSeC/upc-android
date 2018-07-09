package com.barberia.upc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barberia.upc.barberupc.MainActivity;
import com.barberia.upc.barberupc.R;
import com.barberia.upc.models.CreditCard;
import com.barberia.upc.rest.CreditCardService;
import com.barberia.upc.util.Session;
import com.barberia.upc.util.TokenInterceptor;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreditCardFragment extends Fragment implements ValidationListener {

    @NotEmpty
    EditText creditCardNumber;

    @NotEmpty
    MaskedEditText exp;

    @NotEmpty
    EditText ccv;

    @NotEmpty
    EditText name;

    Button save;

    Validator validator;

    Context context;

    Retrofit retrofit;

    CreditCardService creditCardService;

    Session session;

    TokenInterceptor tokenInterceptor;

    public CreditCardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credit_card, container, false);

        creditCardNumber = view.findViewById(R.id.credit_card_number_edit_text);
        exp = view.findViewById(R.id.credit_card_exp_edit_text);
        ccv = view.findViewById(R.id.credit_card_ccv_edit_text);
        name = view.findViewById(R.id.credit_card_name_edit_text);
        save = view.findViewById(R.id.save_credit_card);

        context = view.getContext();

        validator = new Validator(this);
        validator.setValidationListener(this);

        session = new Session(view.getContext());
        tokenInterceptor = new TokenInterceptor(session.getToken());
        OkHttpClient client = tokenInterceptor.makeClient();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://upc.diegoseminario.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        creditCardService = retrofit.create(CreditCardService.class);

        saveCreditCard();

        return view;
    }

    private void saveCreditCard() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        Log.d("BARBER", "Validation succeeded");

        String _name = name.getText().toString();
        String _number = creditCardNumber.getText().toString();
        String _ccv = ccv.getText().toString();
        String _exp = exp.getText().toString();

        CreditCard creditCard = new CreditCard(_name, _number, _ccv, "VISA", _exp);

        Call<CreditCard> call = creditCardService.saveCreditCard(creditCard);

        call.enqueue(new Callback<CreditCard>() {
            @Override
            public void onResponse(Call<CreditCard> call, Response<CreditCard> response) {
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();

                if(fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<CreditCard> call, Throwable t) {
                Toast.makeText(context, R.string.credit_card_error_message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error : errors) {
            View view = error.getView();

            String errorMessage = error.getCollatedErrorMessage(context);

            if(view instanceof EditText) {
                ((EditText) view).setError(errorMessage);
            }
        }
    }
}
