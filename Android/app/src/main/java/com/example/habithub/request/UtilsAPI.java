package com.example.habithub.request;

public class UtilsAPI {
    public static final String BASE_URL_API = "http://13.239.64.98:8080/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
