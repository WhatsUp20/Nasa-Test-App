package com.example.nasa_test_app.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nasa_test_app.R;
import com.example.nasa_test_app.adapter.NasaAdapter;
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
import io.reactivex.schedulers.Schedulers;

import static com.example.nasa_test_app.screens.NasaDetailActivity.EXTRA_DESCRIPTION;
import static com.example.nasa_test_app.screens.NasaDetailActivity.EXTRA_IMAGE;
import static com.example.nasa_test_app.screens.NasaDetailActivity.EXTRA_TITLE;

public class MainActivity extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TextView textViewSpaceNews;
    private TextView textViewMarsNews;
    private NasaAdapter adapter;
    private RecyclerView recyclerView;
    private SwitchCompat switchNasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        switchNasa.setChecked(true);
        switchNasa.setOnCheckedChangeListener((buttonView, isChecked) -> updateSwitchState(isChecked));
        switchNasa.setChecked(false);

        textViewMarsNews.setOnClickListener(v -> {
            updateSwitchState(true);
            switchNasa.setChecked(true);
        });

        textViewSpaceNews.setOnClickListener(v -> {
            updateSwitchState(false);
            switchNasa.setChecked(false);
        });
    }

    private void init() {
        textViewSpaceNews = findViewById(R.id.textViewSpace);
        textViewMarsNews = findViewById(R.id.textViewMars);
        switchNasa = findViewById(R.id.switchNasa);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new NasaAdapter();
    }

    private void updateSwitchState(boolean isMarsSelected) {
        if (isMarsSelected) {
            loadMarsData();
            textViewMarsNews.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewSpaceNews.setTextColor(getResources().getColor(R.color.colorWhite));
        } else {
            loadSpaceData();
            textViewMarsNews.setTextColor(getResources().getColor(R.color.colorWhite));
            textViewSpaceNews.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    private void setImageClickListener() {
        adapter.setOnImageClickListener(position -> {
            Link link1 = adapter.getLinkList().get(position);
            Datum datum1 = adapter.getDatumList().get(position);
            Intent intent = new Intent(MainActivity.this, NasaDetailActivity.class);
            intent.putExtra(EXTRA_IMAGE, link1.getHref());
            intent.putExtra(EXTRA_TITLE, datum1.getTitle());
            intent.putExtra(EXTRA_DESCRIPTION, datum1.getDescription());
            MainActivity.this.startActivity(intent);
        });
    }

    private void loadSpaceData() {

        NetworkService service = NetworkService.getInstance();
        final NetworkApi api = service.getAllApi();
        Disposable disposable = api.getAllSpaceCollections()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((objectCollection, throwable) -> {
                    if (throwable != null) {
                        Toast.makeText(MainActivity.this, "Data loading error " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        callToGetAllDataFromLists(objectCollection);
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void loadMarsData() {
        NetworkService service = NetworkService.getInstance();
        final NetworkApi api = service.getAllApi();
        Disposable disposable = api.getAllMarsCollection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((objectCollection, throwable) -> {
                    if (throwable != null) {
                        Toast.makeText(MainActivity.this, "Data loading error " + throwable, Toast.LENGTH_SHORT).show();
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
        }
        adapter.setLinkList(linkList);
        adapter.setDatumList(datumList);

        setImageClickListener();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}