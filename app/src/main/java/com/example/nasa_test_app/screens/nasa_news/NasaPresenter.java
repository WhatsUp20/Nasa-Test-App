package com.example.nasa_test_app.screens.nasa_news;

import android.content.Context;
import android.widget.Toast;

import com.example.nasa_test_app.api.NetworkApi;
import com.example.nasa_test_app.api.NetworkService;
import com.example.nasa_test_app.data.Datum;
import com.example.nasa_test_app.data.Item;
import com.example.nasa_test_app.data.Link;
import com.example.nasa_test_app.data.ObjectCollection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class NasaPresenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private NasaContract contract;

    public NasaPresenter(NasaContract contract) {
        this.contract = contract;
    }

    public void loadSpaceData() {

        NetworkService service = NetworkService.getInstance();
        final NetworkApi api = service.getAllApi();
        Disposable disposable = api.getAllSpaceCollections()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> contract.showProgressBar())
                .doFinally(() -> contract.notShowProgressBar())
                .subscribe(new BiConsumer<ObjectCollection, Throwable>() {
                    @Override
                    public void accept(ObjectCollection objectCollection, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            Toast.makeText((Context) contract, "Error load data: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            NasaPresenter.this.callToGetAllDataFromLists(objectCollection);
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadMarsData() {
        NetworkService service = NetworkService.getInstance();
        final NetworkApi api = service.getAllApi();
        Disposable disposable = api.getAllMarsCollection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> contract.showProgressBar())
                .doFinally(() -> contract.notShowProgressBar())
                .subscribe((objectCollection, throwable) -> {
                    if (throwable != null) {
                        Toast.makeText((Context) contract, "Error load data: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        callToGetAllDataFromLists(objectCollection);
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void callToGetAllDataFromLists(ObjectCollection objectCollection) {
        //Get All lists
        List<List<Link>> link = new ArrayList<>();
        List<List<Datum>> datum = new ArrayList<>();
        List<Link> linkList = new ArrayList<>();
        List<Datum> datumList = new ArrayList<>();
        List<Item> items = objectCollection.getCollection().getItems();

        //Fill link and datum lists data from item list
        for (int i = 0; i < items.size(); i++) {
            link.add(objectCollection.getCollection().getItems().get(i).getLinks());
            datum.add(objectCollection.getCollection().getItems().get(i).getData());
        }
        //Get and add all dates to linkList
        for (int i = 0; i < link.size(); i++) {
            List<Link> links = link.get(i);
            linkList.addAll(links);
        }
        //Get and add all dates to datumList
        for (int i = 0; i < datum.size(); i++) {
            List<Datum> datumListOfAllDates = datum.get(i);
            datumList.addAll(datumListOfAllDates);

            contract.showDatumDataFromPresenter(datumList);
            contract.showListDataFromPresenter(linkList);
        }
    }

    public void disposeDisposable() {
        compositeDisposable.dispose();
    }
}
