package com.example.habithub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    private static final String SHARED_PREF_NAME = "Logged";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENDER = "gender";

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
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });

        // Check if the token and user data exist in SharedPreferences
        String savedToken = getTokenFromSharedPreferences();
        String savedUserId = getUserIdFromSharedPreferences();
        if (savedToken != null && savedUserId != null) {
            // Token and user data exist, proceed to the Home activity
            navigateToHome();
        }
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
                    // Save the token and user data in SharedPreferences
                    saveTokenToSharedPreferences(token);
                    saveUserDataToSharedPreferences(user);
                    // Start the Home activity
                    navigateToHome();
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

    private void saveTokenToSharedPreferences(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    private String getTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    private void saveUserDataToSharedPreferences(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, user.getUserId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_GENDER, user.getGender());

        editor.apply();
    }

    private String getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    private void navigateToHome() {
        Intent intent = new Intent(mContext, Home.class);
        startActivity(intent);
        finish(); // Close the login activity
    }
}
