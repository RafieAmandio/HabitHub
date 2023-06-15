package com.example.habithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habithub.model.User;
import com.example.habithub.request.BaseApiService;
import com.example.habithub.request.UtilsAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText email, password, name, passwordConfirm;
    TextView btnLogin;
    Button btnRegister;
    BaseApiService mApiService;
    Context mContext;
    RadioGroup genderRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_register);

        mApiService = UtilsAPI.getApiService();
        mContext = this;

        // bind the username and password fields
        email = (EditText) findViewById(R.id.register_editemail);
        password = (EditText) findViewById(R.id.register_editpassword);
        name = (EditText) findViewById(R.id.register_editname);
        passwordConfirm = (EditText) findViewById(R.id.register_confeditpassword);
        btnLogin = (TextView) findViewById(R.id.register_login);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);


        // create button to handle register
        btnRegister = (Button) findViewById(R.id.register_button);
        btnRegister.setOnClickListener(v -> {
            if (validatePasswords())
                register(getSelectedGender());
        });

    }

    private String getSelectedGender() {
        int selectedRadioButtonId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String selectedGender = selectedRadioButton.getText().toString();
        return selectedGender;
    }

    private boolean validatePasswords() {
        String passwordValue = password.getText().toString();
        String passwordConfirmValue = passwordConfirm.getText().toString();

        if (!passwordValue.equals(passwordConfirmValue)) {
            Toast.makeText(mContext, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void register(String gender) {
        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();
        String nameValue = name.getText().toString();

        // Perform validation if needed

        // Make the API call
        Call<User> call = mApiService.register(nameValue, emailValue, passwordValue, gender);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    User user = response.body();

                    // Registration successful, intent to login
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);

                } else {
                    // Registration failed, show error message
                    Toast.makeText(mContext, "Registration failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Registration request failed, handle the error
                Toast.makeText(mContext, "Registration request failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

}