package com.example.habithub.request;

import com.example.habithub.model.Goals;
import com.example.habithub.model.Habit;
import com.example.habithub.model.LoginResponse;
import com.example.habithub.model.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
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

    @FormUrlEncoded
    @POST("habits/create")
    Call<Habit> addHabit(
            @Header("x-access-token") String token,
            @Field("goalId") String goalid,
            @Field("description") String description,
            @Field("habitName") String habitName,
            @Field("startDate") String startDate,
            @Field("daysOfWeek") String daysOfWeek
    );

    @FormUrlEncoded
    @POST("habits/getbyuser")
    Call<ArrayList<Habit>> getHabitByUserId(
            @Header("x-access-token") String token,
            @Field("userId") String userid
    );

    @FormUrlEncoded
    @POST("habits/getbygoal")
    Call<ArrayList<Habit>> getHabitByGoalId(
            @Header("x-access-token") String token,
            @Field("goalId") String goalid
    );

    @FormUrlEncoded
    @POST("checklists/checkin")
    Call<Void> checkHabit(
            @Header("x-access-token") String token,
            @Field("userId") String userId,
            @Field("habitId") String habitId
    );

    @FormUrlEncoded
    @POST("users/checkpoint")
    Call<ResponseBody> getPoints(
            @Header("x-access-token") String token,
            @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST("checklists/uncheck")
    Call<Void> uncheckHabit(
            @Header("x-access-token") String token,
            @Field("userId") String userId,
            @Field("habitId") String habitId
    );


}
