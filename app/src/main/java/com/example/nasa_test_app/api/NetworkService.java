package com.example.nasa_test_app.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService service;
    public static final String BASE_URL = "https://images-api.nasa.gov/";
    private Retrofit retrofit;
    private static final Object LOCK = new Object();

    private NetworkService() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static NetworkService getInstance() {
        synchronized (LOCK) {
            if (service == null) {
                service = new NetworkService();
            }
        }
        return service;
    }

    public NetworkApi getAllApi() {
        return retrofit.create(NetworkApi.class);
    }
}
