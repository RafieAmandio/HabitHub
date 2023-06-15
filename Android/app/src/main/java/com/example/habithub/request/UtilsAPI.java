package com.example.habithub.request;

public class UtilsAPI {
    public static final String BASE_URL_API = "http://3.105.229.243:8080/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
