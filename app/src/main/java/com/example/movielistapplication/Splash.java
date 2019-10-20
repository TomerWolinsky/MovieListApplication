package com.example.movielistapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class Splash extends Activity {

    String url = "http://api.androidhive.info/json/movies.json";
    MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dbHelper = new MyDbHelper(this);
        new JsonTask(url, new AsyncResponse() {
            @Override
            public void processFinish(ArrayList<MovieResultObj> outPut) {
                for (int i = 0; i < outPut.size(); i++) {
                    String title = outPut.get(i).getTitle();
                    String image = outPut.get(i).getImage();
                    String rating = outPut.get(i).getRating();
                    String releaseYear = outPut.get(i).getReleaseYear();
                    String genre = outPut.get(i).getGenre();
                    dbHelper.insertMovie(title, image, rating, releaseYear, genre);
                }
                Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }).execute();

    }
}
