package com.example.habithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.habithub.model.Goals;
import com.example.habithub.request.BaseApiService;
import com.example.habithub.request.UtilsAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;

    ListView listView;

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


    }

    private void getGoals() {
        Call<ArrayList<Goals>> call = mApiService.getGoalsByUserId(Login.user.getUserId());
        call.enqueue(new Callback<ArrayList<Goals>>() {
            @Override
            public void onResponse(Call<ArrayList<Goals>> call, Response<ArrayList<Goals>> response) {
                try {
                    if (response.isSuccessful()) {
                        ArrayList<Goals> goalsList = response.body();

                        ArrayList<String> goalNamesList = new ArrayList<>();

                        // Iterate through the goalsList and extract the goalName for each Goals object
                        for (Goals goal : goalsList) {
                            String goalName = goal.getGoalName();
                            goalNamesList.add(goalName);
                        }
                        // Create an adapter to populate the ListView
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, goalNamesList);
                        // Set the adapter to the ListView
                        listView.setAdapter(adapter);
                    } else {
                        // Handle API call failure

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Goals>> call, Throwable t) {
                // Handle API call failure
            }
        });


    }
}