package com.example.habithub.request;

public class UtilsAPI {
    public static final String BASE_URL_API = "https://habithub-live-production.up.railway.app/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
