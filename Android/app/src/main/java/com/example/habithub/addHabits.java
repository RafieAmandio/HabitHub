package com.example.habithub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habithub.model.Habit;
import com.example.habithub.request.BaseApiService;
import com.example.habithub.request.UtilsAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addHabits extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    EditText habitName, habitDescription;
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
        setContentView(R.layout.activity_add_habits);
        mApiService = UtilsAPI.getApiService();
        mContext = this;

        //map all the button
        habitName = (EditText) findViewById(R.id.addhabits_editname);
        habitDescription = (EditText) findViewById(R.id.addhabits_editdesc);
        btnAddGoal = (Button) findViewById(R.id.addhabits_btncreate);
        btnSelectDate = (Button) findViewById(R.id.addhabits_selectdate);
        btnBack = (ImageView) findViewById(R.id.addhabits_btnback);
        tgDate = (TextView) findViewById(R.id.addhabits_date);
        calendar = Calendar.getInstance();

        //show date picker
        btnSelectDate.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        //add new habit
        btnAddGoal.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("Logged", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            String name = habitName.getText().toString();
            String description = habitDescription.getText().toString();
            String date = tgDate.getText().toString();
            String daysOfWeek = getSelectedDaysOfWeek();

            if (name.isEmpty() || description.isEmpty() || date.isEmpty() || daysOfWeek.isEmpty()) {
                Toast.makeText(mContext, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                addHabit(token,Home.selectedGoalId,name, description, date, daysOfWeek);
            }
        });


    }

    private String getSelectedDaysOfWeek() {
        List<Integer> selectedDaysList = new ArrayList<>();
        CheckBox checkBoxSunday = findViewById(R.id.checkBoxSunday);
        CheckBox checkBoxMonday = findViewById(R.id.checkBoxMonday);
        CheckBox checkBoxTuesday = findViewById(R.id.checkBoxTuesday);
        CheckBox checkBoxWednesday = findViewById(R.id.checkBoxWednesday);
        CheckBox checkBoxThursday = findViewById(R.id.checkBoxThursday);
        CheckBox checkBoxFriday = findViewById(R.id.checkBoxFriday);
        CheckBox checkBoxSaturday = findViewById(R.id.checkBoxSaturday);

        if (checkBoxSunday.isChecked()) {
            selectedDaysList.add(1);
        }
        if (checkBoxMonday.isChecked()) {
            selectedDaysList.add(2);
        }
        if (checkBoxTuesday.isChecked()) {
            selectedDaysList.add(3);
        }
        if (checkBoxWednesday.isChecked()) {
            selectedDaysList.add(4);
        }
        if (checkBoxThursday.isChecked()) {
            selectedDaysList.add(5);
        }
        if (checkBoxFriday.isChecked()) {
            selectedDaysList.add(6);
        }
        if (checkBoxSaturday.isChecked()) {
            selectedDaysList.add(7);
        }

        // Convert the selected days list to a string
        return selectedDaysList.toString();
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

    // Add habit function
    private void addHabit(String token, String goalId, String habitDescription, String habitName, String startDate, String daysOfWeek) {
        // Make the API call to add the habit
        Call<Habit> call = mApiService.addHabit(token, goalId, habitDescription, habitName, startDate, daysOfWeek);
        call.enqueue(new Callback<Habit>() {
            @Override
            public void onResponse(Call<Habit> call, Response<Habit> response) {
                if (response.isSuccessful()) {
                    // Habit added successfully
                    Habit habit = response.body();
                    // Handle the response as needed
                    Toast.makeText(mContext, "Habit added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(addHabits.this, Home.class);
                    startActivity(intent);
                } else {
                    // Error adding habit
                    Toast.makeText(mContext, "Error adding habit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Habit> call, Throwable t) {
                // Handle the failure as needed
                Toast.makeText(mContext, "Failed to add habit: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}