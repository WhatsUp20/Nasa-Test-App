package com.example.nasa_test_app.data;

import android.content.ClipData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CollectionNasa {

    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("href")
    @Expose
    private String href;

    public String getHref() {
        return href;
    }

    public String getVersion() {
        return version;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
