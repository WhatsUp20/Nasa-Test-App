package com.example.nasa_test_app.api;

import com.example.nasa_test_app.data.ObjectCollection;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NetworkApi {
    @GET("search?q=space&media_type=image&year_start=2021&year_end=2021")
    Single<ObjectCollection> getAllSpaceCollections();
    @GET("search?q=mars&media_type=image&year_start=2021&year_end=2021")
    Single<ObjectCollection> getAllMarsCollection();
}
