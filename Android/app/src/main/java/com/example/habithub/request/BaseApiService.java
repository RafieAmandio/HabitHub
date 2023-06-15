package com.example.habithub.request;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class BaseApiService {
    @GET("account/{id}")
    Call<String> getAccount() {
        return null;
    }

}
