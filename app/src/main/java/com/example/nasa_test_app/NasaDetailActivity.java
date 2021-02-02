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

    private String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_detail);
        imageViewToDetails = findViewById(R.id.imageViewToDetails);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewTitle = findViewById(R.id.textViewTitle);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("image")) {
            image = intent.getStringExtra("image");
            Picasso.get().load(image).into(imageViewToDetails);
            textViewTitle.setText("New All-in-One Antenna for the Deep Space Network");
            textViewDescription.setText("Deep Space Station 56, or DSS-56, is a powerful 34-meter-wide (112-foot-wide) antenna that was added to the Deep Space Network's Madrid Deep Space Communications Complex in Spain in early 2021 after beginning construction in 2017. Deep Space Network (DSN) radio antennas communicate with spacecraft throughout the solar system. Previous antennas have been limited in the frequency bands they can receive and transmit, often being restricted to communicating only with specific spacecraft. DSS-56 is the first to use the DSN's full range of communication frequencies. This means DSS-56 is an \\\"all-in-one\\\" antenna that can communicate with all the missions that the DSN supports and can be used as a backup for any of the Madrid complex's other antennas.  With the addition of DSS-56 and other 34-meter antennas to all three DSN complexes, the network is preparing to play a critical role in ensuring communication and navigation support for upcoming Moon and Mars missions and the crewed Artemis missions.  https://photojournal.jpl.nasa.gov/catalog/PIA24163\"");
        } else {
            finish();
        }
    }
}