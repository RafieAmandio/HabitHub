package com.example.habithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.habithub.model.Goals;
import com.example.habithub.model.Habit;
import com.example.habithub.request.BaseApiService;
import com.example.habithub.request.UtilsAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    static BaseApiService mApiService;
    private ListView listViewHabit;
    private ArrayList<Habit> habitList;
    private HabitAdapter habitAdapter;
    static Context mContext;
    Button btnLogout;
    ListView listView;
    ImageView profile;

    private FloatingActionButton fab;
    private View option1;


    public static ArrayList<Goals> goalsList = new ArrayList<>();
    public static String selectedGoalId;

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
        habitList = new ArrayList<>();
        listViewHabit = (ListView) findViewById(R.id.home_listViewhabits);
        // btnLogout = (Button) findViewById(R.id.home_logout);


//        btnLogout.setOnClickListener(v -> {
//            logout();
//        });

        getGoals();
        getHabits();

        fab = findViewById(R.id.fab);
        option1 = findViewById(R.id.option1);
        profile = findViewById(R.id.main_profileimage);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Profile.class);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (option1.getVisibility() == View.VISIBLE) {
                    option1.setVisibility(View.GONE);
                } else {
                    option1.setVisibility(View.VISIBLE);
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


    }

    private void getGoals() {
        SharedPreferences sharedPreferences = getSharedPreferences("Logged", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String userId = sharedPreferences.getString("user_id", "");
        try {
            Call<ArrayList<Goals>> call = mApiService.getGoalsByUserId(token, userId);
            call.enqueue(new Callback<ArrayList<Goals>>() {
                @Override
                public void onResponse(Call<ArrayList<Goals>> call, Response<ArrayList<Goals>> response) {
                    try {
                        if (response.isSuccessful()) {

                            goalsList = response.body();
                            GoalsAdapter goalsAdapter = new GoalsAdapter(mContext, goalsList);
                            listView.setAdapter(goalsAdapter);

                            // Handle item click on the ListView
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // Retrieve the selected goal
                                    Goals selectedGoal = goalsList.get(position);

                                    // Store the selected goalId in SharedPreferences or any other storage mechanism
                                    selectedGoalId = selectedGoal.getGoalId();
                                    // Open AddHabits activity
                                    Intent intent = new Intent(mContext, addHabits.class);
                                    mContext.startActivity(intent);
                                }
                            });
                        } else {
                            // Handle API call failure
                            // if res status code is 401, logout
                            if (response.code() == 401) {
                                logout();
                            }

                            Toast.makeText(mContext, "Error" + response.errorBody().string(), Toast.LENGTH_SHORT).show();
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

    private void getHabits() {
        SharedPreferences sharedPreferences = getSharedPreferences("Logged", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String userId = sharedPreferences.getString("user_id", "");
        // Fetch habits by user ID
        Call<ArrayList<Habit>> call = mApiService.getHabitByUserId(token, userId);
        call.enqueue(new Callback<ArrayList<Habit>>() {
            @Override
            public void onResponse(Call<ArrayList<Habit>> call, Response<ArrayList<Habit>> response) {
                if (response.isSuccessful()) {
                    habitList = response.body();
                    ArrayList<Habit> filteredHabits = new ArrayList<>();

                    // Get the current day of the week (1 = Sunday, 2 = Monday, ..., 7 = Saturday)
                    Calendar calendar = Calendar.getInstance();
                    int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

                    for (Habit habit : habitList) {
                        // Parse the daysOfWeek string into a list of integers
                        List<Integer> daysOfWeekList = parseDaysOfWeek(habit.getDaysOfWeek());

                        // Check if the habit is scheduled for the current day
                        if (daysOfWeekList.contains(currentDay)) {
                            filteredHabits.add(habit);
                        }
                    }

                    // Create and set the adapter with the filtered habits
                    habitAdapter = new HabitAdapter(mContext, filteredHabits);
                    listViewHabit.setAdapter(habitAdapter);
                } else {
                    Toast.makeText(mContext, "Error fetching habits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Habit>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to fetch habits", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void checkHabit(Habit habit) {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("Logged", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String userId = sharedPreferences.getString("user_id", "");
        String habitId = habit.getHabitId().toString();
        Call<Void> call = mApiService.checkHabit(token, userId, habitId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Habit checked successfully
                    Toast.makeText(mContext, "Habit checked", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(mContext, "API call failed: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle API call failure
                Toast.makeText(mContext, "Failed to check habit", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void uncheckedHabit(Habit habit) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("Logged", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String userId = sharedPreferences.getString("user_id", "");
        String habitId = habit.getHabitId().toString();

        Call<Void> call = mApiService.uncheckHabit(token, userId, habitId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Habit unchecked successfully
                    Toast.makeText(mContext, "Habit unchecked", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle API call failure
                    Toast.makeText(mContext, "Failed to uncheck habit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle API call failure
                Toast.makeText(mContext, "Failed to uncheck habit", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<Integer> parseDaysOfWeek(String daysOfWeek) {
        List<Integer> daysOfWeekList = new ArrayList<>();
        try {
            // Remove the brackets and split the string by commas
            String[] daysArray = daysOfWeek.replace("[", "").replace("]", "").split(",");

            for (String day : daysArray) {
                // Trim any whitespaces and parse each element as an integer
                int dayOfWeek = Integer.parseInt(day.trim());
                daysOfWeekList.add(dayOfWeek);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return daysOfWeekList;
    }

}