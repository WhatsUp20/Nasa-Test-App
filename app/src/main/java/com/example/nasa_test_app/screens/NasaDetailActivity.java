package com.example.nasa_test_app.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasa_test_app.R;
import com.squareup.picasso.Picasso;

public class NasaDetailActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DESCRIPTION = "desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_detail);
        ImageView imageViewToDetails = findViewById(R.id.imageViewToDetails);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        TextView textViewTitle = findViewById(R.id.textViewTitle);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_IMAGE) && intent.hasExtra(EXTRA_TITLE) && intent.hasExtra(EXTRA_DESCRIPTION)) {
            String image = intent.getStringExtra(EXTRA_IMAGE);
            String title = intent.getStringExtra(EXTRA_TITLE);
            String desc = intent.getStringExtra(EXTRA_DESCRIPTION);
            Picasso.get().load(image).into(imageViewToDetails);
            textViewTitle.setText(title);
            textViewDescription.setText(desc);
        } else {
            finish();
        }
    }
}