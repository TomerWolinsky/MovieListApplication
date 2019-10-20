package com.example.movielistapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    ImageView imageView;
    TextView title, year, rating, genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imageView = findViewById(R.id.image_txt1);
        title = findViewById(R.id.title_txt1);
        year = findViewById(R.id.year_txt1);
        rating = findViewById(R.id.rating_txt1);
        genre = findViewById(R.id.genre_txt1);
        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("image")).into(imageView);
        title.setText(intent.getStringExtra("title"));
        year.setText(intent.getStringExtra("releaseYear"));
        rating.setText(intent.getStringExtra("rating"));
        genre.setText(intent.getStringExtra("genre"));
    }
}
