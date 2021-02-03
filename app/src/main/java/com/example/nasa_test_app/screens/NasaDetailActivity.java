package com.example.nasa_test_app.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasa_test_app.R;
import com.squareup.picasso.Picasso;

public class NasaDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_detail);
        ImageView imageViewToDetails = findViewById(R.id.imageViewToDetails);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        TextView textViewTitle = findViewById(R.id.textViewTitle);

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