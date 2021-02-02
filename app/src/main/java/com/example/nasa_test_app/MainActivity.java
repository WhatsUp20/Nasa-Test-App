package com.example.nasa_test_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.nasa_test_app.adapter.NasaAdapter;
import com.example.nasa_test_app.api.NetworkApi;
import com.example.nasa_test_app.api.NetworkService;
import com.example.nasa_test_app.data.Item;
import com.example.nasa_test_app.data.Link;

import java.util.ArrayList;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private NasaAdapter adapter;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
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
                .subscribe((objectCollection, throwable) -> {
                    if (throwable != null) {
                        Toast.makeText(MainActivity.this, "Data loading error " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        List<Item> items = objectCollection.getCollection().getItems();
                        List<List<Link>> items1 = new ArrayList<>();
                        List<Link> linkList = new ArrayList<>();

                        for(int i = 0;i < items.size();i++) {
                            items1.add(objectCollection.getCollection().getItems().get(i).getLinks());
                        }
                        for (int i = 0;i < items1.size(); i++) {
                            List<Link> links = items1.get(i);
                            linkList.addAll(links);
                        }
                        adapter.setLinkList(linkList);

                        adapter.setOnImageClickListener(position -> {
                            Link link = adapter.getLinkList().get(position);

                            Intent intent = new Intent(MainActivity.this, NasaDetailActivity.class);
                            intent.putExtra("image",link.getHref());
                            startActivity(intent);
                        });
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}