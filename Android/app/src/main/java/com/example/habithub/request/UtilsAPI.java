package com.example.habithub.request;

public class UtilsAPI {
    public static final String BASE_URL_API = "http://54.206.236.156:8080/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
