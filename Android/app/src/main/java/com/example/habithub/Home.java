package com.example.habithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.habithub.request.BaseApiService;
import com.example.habithub.request.UtilsAPI;

public class Home extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;
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


    }
}