package com.example.nasa_test_app.api;

import com.example.nasa_test_app.data.CollectionNasa;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NetworkApi {
    @GET("search?q=space&media_type=image&year_start=2020&year_end=2020")
    Single<CollectionNasa> getAllCollections();
}
