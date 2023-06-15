package com.example.habithub.request;

public class UtilsAPI {
    public static final String BASE_URL_API = "http://10.0.2.2:8080/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
