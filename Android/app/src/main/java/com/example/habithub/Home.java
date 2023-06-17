package com.example.habithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.habithub.model.Goals;
import com.example.habithub.request.BaseApiService;
import com.example.habithub.request.UtilsAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    BaseApiService mApiService;

    Context mContext;
    Button btnLogout;
    ListView listView;

    private FloatingActionButton fab;
    private View option1;
    private View option2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_home);
        mApiService = UtilsAPI.getApiService();
        mContext = this;

        listView = (ListView) findViewById(R.id.home_listView);
        // btnLogout = (Button) findViewById(R.id.home_logout);


//        btnLogout.setOnClickListener(v -> {
//            logout();
//        });

        getGoals();

        fab = findViewById(R.id.fab);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (option1.getVisibility() == View.VISIBLE && option2.getVisibility() == View.VISIBLE) {
                    option1.setVisibility(View.GONE);
                    option2.setVisibility(View.GONE);
                } else {
                    option1.setVisibility(View.VISIBLE);
                    option2.setVisibility(View.VISIBLE);
                }
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect the user to the add goals activity
                Intent intent = new Intent(mContext, AddGoals.class);
                startActivity(intent);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void getGoals() {
        SharedPreferences sharedPreferences = getSharedPreferences("Logged", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String userId = sharedPreferences.getString("user_id", "");
        try {
            Call<ArrayList<Goals>> call = mApiService.getGoalsByUserId(token,userId);
            call.enqueue(new Callback<ArrayList<Goals>>() {
                @Override
                public void onResponse(Call<ArrayList<Goals>> call, Response<ArrayList<Goals>> response) {
                    try {
                        if (response.isSuccessful()) {

                            Toast.makeText(mContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                            ArrayList<Goals> goalsList = response.body();
                            Log.d("Response", goalsList.toString());


                            ArrayList<String> goalNamesList = new ArrayList<>();

                            // Iterate through the goalsList and extract the goalName for each Goals object
                            for (Goals goal : goalsList) {
                                String goalName = goal.getGoalName();
                                goalNamesList.add(goalName);
                            }

                            System.out.println(goalNamesList);

                            // Create an adapter to populate the ListView
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, goalNamesList);
                            // Set the adapter to the ListView
                            listView.setAdapter(adapter);
                        } else {
                            // Handle API call failure
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<Goals>> call, Throwable t) {
                    // Handle API call failure
                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


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