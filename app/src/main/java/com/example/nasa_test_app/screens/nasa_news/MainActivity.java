package com.example.nasa_test_app.screens.nasa_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nasa_test_app.R;
import com.example.nasa_test_app.adapter.NasaAdapter;
import com.example.nasa_test_app.data.Datum;
import com.example.nasa_test_app.data.Link;
import com.example.nasa_test_app.screens.NasaDetailActivity;

import java.util.List;

import static com.example.nasa_test_app.screens.NasaDetailActivity.EXTRA_DESCRIPTION;
import static com.example.nasa_test_app.screens.NasaDetailActivity.EXTRA_IMAGE;
import static com.example.nasa_test_app.screens.NasaDetailActivity.EXTRA_TITLE;

public class MainActivity extends AppCompatActivity implements NasaContract {

    private TextView textViewSpaceNews;
    private TextView textViewMarsNews;
    private NasaAdapter adapter;
    private SwitchCompat switchNasa;
    private ProgressBar progressBarLoading;
    private NasaPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

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

    private void updateSwitchState(boolean isMarsSelected) {
        if (isMarsSelected) {
            presenter.loadMarsData();
            setImageClickListener();
            textViewMarsNews.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewSpaceNews.setTextColor(getResources().getColor(R.color.colorWhite));
        } else {
            presenter.loadSpaceData();
            setImageClickListener();
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

    private void init() {
        textViewSpaceNews = findViewById(R.id.textViewSpace);
        textViewMarsNews = findViewById(R.id.textViewMars);
        switchNasa = findViewById(R.id.switchNasa);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        adapter = new NasaAdapter();
        presenter = new NasaPresenter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showDatumDataFromPresenter(List<Datum> datumList) {
        adapter.setDatumList(datumList);
    }

    @Override
    public void showListDataFromPresenter(List<Link> linkList) {
        adapter.setLinkList(linkList);
    }

    @Override
    public void showProgressBar() {
        progressBarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void notShowProgressBar() {
        progressBarLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        presenter.disposeDisposable();
        super.onDestroy();
    }
}