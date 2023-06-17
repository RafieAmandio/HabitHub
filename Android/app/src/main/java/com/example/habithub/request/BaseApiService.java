package com.example.habithub.request;

import com.example.habithub.model.Goals;
import com.example.habithub.model.LoginResponse;
import com.example.habithub.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("users/register")
    Call<User> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender
    );

    @FormUrlEncoded
    @POST("goals/user")
    Call<ArrayList<Goals>> getGoalsByUserId(
            @Header("x-access-token") String token,
            @Field("userid") String userid
    );

    @FormUrlEncoded
    @POST("goals/create")
    Call<Goals> addGoals(
            @Header("x-access-token") String token,
            @Field("userId") String userid,
            @Field("description") String description,
            @Field("goalName") String goalName,
            @Field("targetDate") String targetDate
    );



}
