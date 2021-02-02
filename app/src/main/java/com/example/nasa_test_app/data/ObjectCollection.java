package com.example.nasa_test_app.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectCollection {
    @SerializedName("collection")
    @Expose
    private CollectionNasa collection;

    public CollectionNasa getCollection() {
        return collection;
    }

    public void setCollection(CollectionNasa collection) {
        this.collection = collection;
    }
}
