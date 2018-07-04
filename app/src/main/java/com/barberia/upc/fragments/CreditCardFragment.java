package com.barberia.upc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.barberia.upc.barberupc.R;
import com.barberia.upc.models.CreditCard;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import br.com.sapereaude.maskedEditText.MaskedEditText;

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
