package com.example.habithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habithub.request.BaseApiService;
import com.example.habithub.request.UtilsAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;

    TextView name,email, points;
    ListView listview;

    ImageView logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_profile);

        mApiService = UtilsAPI.getApiService();
        mContext = this;

        name = (TextView) findViewById(R.id.me_name);
        email = (TextView) findViewById(R.id.me_email);
        points = (TextView) findViewById(R.id.me_points);
        listview = (ListView) findViewById(R.id.me_listview);
        logout = (ImageView) findViewById(R.id.me_logoutbutton);

        // set name and email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("Logged", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String useremail = sharedPreferences.getString("email", "");
        String token = sharedPreferences.getString("token", "");
        String userId = sharedPreferences.getString("user_id", "");



        name.setText(username);
        email.setText(useremail);
        getPoints(token, userId);

        logout.setOnClickListener(v -> logout());

    }

    public void getPoints(String token, String userId) {

        Call<ResponseBody> call = mApiService.getPoints(token, userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseString);
                        int pointss = jsonObject.getInt("points");
                        // Do something with the points value
                        points.setText(String.valueOf(pointss));
                        Toast.makeText(mContext, "Points: " + pointss, Toast.LENGTH_SHORT).show();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "Error fetching points", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Failed to fetch points", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void logout() {
        // Clear the shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("Logged", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect the user to the login activity
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish(); // Close the current activity
    }


}