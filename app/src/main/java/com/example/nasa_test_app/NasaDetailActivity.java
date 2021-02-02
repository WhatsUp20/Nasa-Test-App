package com.example.nasa_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.nasa_test_app.adapter.NasaAdapter;
import com.example.nasa_test_app.data.Link;
import com.squareup.picasso.Picasso;

public class NasaDetailActivity extends AppCompatActivity {

    private ImageView imageViewToDetails;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_detail);
        imageViewToDetails = findViewById(R.id.imageViewToDetails);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            finish();
        }

    }
}