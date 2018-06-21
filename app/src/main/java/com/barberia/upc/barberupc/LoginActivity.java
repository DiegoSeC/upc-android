package com.barberia.upc.barberupc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.barberia.upc.com.barberia.upc.util.Session;
import com.barberia.upc.rest.Authentication;
import com.barberia.upc.rest.AuthenticationService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener,
        Callback<Authentication> {

    Button loginButton;

    @NotEmpty
    EditText usernameInput;

    @NotEmpty
    EditText passwordInput;

    Validator validator;

    Retrofit retrofit;

    AuthenticationService authService;

    ProgressBar progressBar;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        validator = new Validator(this);
        validator.setValidationListener(this);

        session = new Session(this);

        loginButton = (Button) findViewById(R.id.login_button);
        usernameInput = (EditText) findViewById(R.id.username_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        progressBar = findViewById(R.id.progress_bar);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://upc.diegoseminario.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authService = retrofit.create(AuthenticationService.class);

        this.login();
    }

    public void login() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
                progressBar.setVisibility(View.VISIBLE);

                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                Log.d("USERNAME", username);
                Log.d("PASSWORD", password);

                Authentication auth = new Authentication(username, password);

                Call<Authentication> call = authService.auth(auth);
                call.enqueue(LoginActivity.this);

                usernameInput.setEnabled(false);
                passwordInput.setEnabled(false);

                loginButton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        Log.d("VALIDATION", "Validation passed");
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error : errors) {
            View view = error.getView();

            String errorMessage = error.getCollatedErrorMessage(this);

            if(view instanceof EditText) {
                ((EditText) view).setError(errorMessage);
            }
        }
    }

    @Override
    public void onResponse(Call<Authentication> call, Response<Authentication> response) {
        String token = response.body().getToken();
        progressBar.setVisibility(View.GONE);
        Log.d("USER LOGGED ", "OK token = " + token);

        if(token == null) {
            this.wrongCredentials();
        } else {
            session.setToken(token);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onFailure(Call<Authentication> call, Throwable t) {
        Toast.makeText(this, R.string.generic_error_http_request, Toast.LENGTH_LONG).show();
    }

    public void wrongCredentials() {
        Toast.makeText(this, R.string.wrong_credentials, Toast.LENGTH_LONG).show();
        usernameInput.setText("");
        passwordInput.setText("");

        usernameInput.requestFocus();

        usernameInput.setEnabled(true);
        passwordInput.setEnabled(true);

        loginButton.setVisibility(View.VISIBLE);
    }
}
