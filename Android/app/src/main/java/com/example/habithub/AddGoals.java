package com.example.habithub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habithub.model.Goals;
import com.example.habithub.request.BaseApiService;
import com.example.habithub.request.UtilsAPI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGoals extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;
    EditText goalName, goalDescription;
    Button btnAddGoal, btnSelectDate;
    ImageView btnBack;
    TextView tgDate;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_add_goals);
        mApiService = UtilsAPI.getApiService();
        mContext = this;

        goalName = (EditText) findViewById(R.id.addgoals_editname);
        goalDescription = (EditText) findViewById(R.id.addgoals_editdesc);
        btnAddGoal = (Button) findViewById(R.id.addgoals_btncreate);
        btnSelectDate = (Button) findViewById(R.id.addgoals_selectdate);
        btnBack = (ImageView) findViewById(R.id.addgoals_btnback);
        tgDate = (TextView) findViewById(R.id.addgoals_date);
        calendar = Calendar.getInstance();

        btnSelectDate.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        btnAddGoal.setOnClickListener(v -> {
            addGoal();
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });


    }


    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateSelectedDate();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                mContext,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void updateSelectedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String selectedDate = dateFormat.format(calendar.getTime());
        tgDate.setText(selectedDate);
    }
    private void addGoal() {
        SharedPreferences sharedPreferences = getSharedPreferences("Logged", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String userId = sharedPreferences.getString("user_id", "");
        String description = goalDescription.getText().toString().trim();
        String goalNamee = goalName.getText().toString().trim();
        String targetDate = tgDate.getText().toString().trim();
        //print the token in toast
        Call<Goals> call = mApiService.addGoals(token, userId, description, goalNamee, targetDate);
        call.enqueue(new Callback<Goals>() {
            @Override
            public void onResponse(Call<Goals> call, Response<Goals> response) {
                if (response.isSuccessful()) {
                    Goals goals = response.body();
                    // Handle successful addition of goals
                    Toast.makeText(mContext, "Goal added successfully", Toast.LENGTH_SHORT).show();
                    // create intent to home
                    Intent intent = new Intent(AddGoals.this, Home.class);
                    startActivity(intent);

                } else {
                    // Handle unsuccessful addition of goals

                    //print the response body
                    Toast.makeText(mContext, "Failed to add goal: " + response.errorBody(), Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onFailure(Call<Goals> call, Throwable t) {
                // Handle failure in making the API call
                Toast.makeText(mContext, "Failed to add goal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace(); // Print the stack trace for more detailed error information
            }
        });
    }

}