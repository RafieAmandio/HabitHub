package com.example.habithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    // bind the username and password fields
    // BaseApiService mApiService;
    EditText email, password;
    Button btnLogin;
    TextView btnRegister;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_login);

        //bind the button
        btnLogin = (Button) findViewById(R.id.login_button);
        btnRegister = (TextView) findViewById(R.id.login_register);

        // bind the username and password fields
        email = (EditText) findViewById(R.id.login_editemail);
        password = (EditText) findViewById(R.id.login_editpassword);

        // create button to handle login
        btnLogin.setOnClickListener(v -> {
            // TODO Create Login Logic

            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        });

        // create button to handle register
        btnRegister.setOnClickListener(v -> {
            // TODO Create Register Logic

            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });



    }
}