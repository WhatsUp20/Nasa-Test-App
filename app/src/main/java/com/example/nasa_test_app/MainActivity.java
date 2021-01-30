package com.example.nasa_test_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.nasa_test_app.adapter.NasaAdapter;
import com.example.nasa_test_app.api.NetworkApi;
import com.example.nasa_test_app.api.NetworkService;
import com.example.nasa_test_app.data.CollectionNasa;
import com.example.nasa_test_app.data.Datum;
import com.example.nasa_test_app.data.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NasaAdapter adapter;
    private CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new NasaAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        compositeDisposable = new CompositeDisposable();
        NetworkService service = NetworkService.getInstance();
        final NetworkApi api = service.getAllApi();
        Disposable disposable = api.getAllCollections()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<CollectionNasa, Throwable>() {
                    @Override
                    public void accept(CollectionNasa collectionNasa, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            Toast.makeText(MainActivity.this, "Data loading error " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            adapter.setDatumList(collectionNasa.getItems().listIterator().next().getData());
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}