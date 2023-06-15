package com.example.habithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habithub.model.LoginResponse;
import com.example.habithub.model.User;
import com.example.habithub.request.BaseApiService;
import com.example.habithub.request.UtilsAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    // bind the username and password fields
    BaseApiService mApiService;
    EditText email, password;
    Button btnLogin;
    TextView btnRegister;
    Context mContext;

    public static User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_login);

        mApiService = UtilsAPI.getApiService();
        mContext = this;

        //bind the button
        btnLogin = (Button) findViewById(R.id.login_button);
        btnRegister = (TextView) findViewById(R.id.login_register);

        // bind the username and password fields
        email = (EditText) findViewById(R.id.login_editemail);
        password = (EditText) findViewById(R.id.login_editpassword);

        // create button to handle login
        btnLogin.setOnClickListener(v -> {
            login();
        });

        // create button to handle register
        btnRegister.setOnClickListener(v -> {
            // TODO Create Register Logic

            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });



    }

    private void login() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        Call<LoginResponse> call = mApiService.login(userEmail, userPassword);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    // Handle successful login response
                    String token = loginResponse.getToken();
                    user = loginResponse.getUser();
                    // Save the token or perform any required actions
                    // Start the Home activity
                    Intent intent = new Intent(mContext, Home.class);
                    startActivity(intent);
                    finish(); // Close the login activity
                } else {
                    // Handle unsuccessful login response
                    Toast.makeText(mContext, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle failure in making the login request
                Toast.makeText(mContext, "Failed to login: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace(); // Print the stack trace for more detailed error information
            }
        });
    }
}