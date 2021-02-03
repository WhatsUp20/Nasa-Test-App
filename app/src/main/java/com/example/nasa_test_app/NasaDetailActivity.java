package com.example.nasa_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasa_test_app.adapter.NasaAdapter;
import com.example.nasa_test_app.data.Datum;
import com.example.nasa_test_app.data.Link;
import com.squareup.picasso.Picasso;

public class NasaDetailActivity extends AppCompatActivity {

    private ImageView imageViewToDetails;
    private TextView textViewDescription;
    private TextView textViewTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_detail);
        imageViewToDetails = findViewById(R.id.imageViewToDetails);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewTitle = findViewById(R.id.textViewTitle);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("image") && intent.hasExtra("title") && intent.hasExtra("desc")) {
            String image = intent.getStringExtra("image");
            String title = intent.getStringExtra("title");
            String desc = intent.getStringExtra("desc");
            Picasso.get().load(image).into(imageViewToDetails);
            textViewTitle.setText(title);
            textViewDescription.setText(desc);
        } else {
            finish();
        }
    }
}