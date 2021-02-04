package com.example.nasa_test_app.screens.nasa_news;

import com.example.nasa_test_app.data.Datum;
import com.example.nasa_test_app.data.Link;

import java.util.List;

public interface NasaContract {
    void showDatumDataFromPresenter(List<Datum> datumList);
    void showListDataFromPresenter(List<Link> linkList);
    void showProgressBar();
    void notShowProgressBar();
}
