package com.example.movielistapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "movies";
    public static final String CONTACTS_COLUMN_title = "title";
    public static final String CONTACTS_COLUMN_image = "image";
    public static final String CONTACTS_COLUMN_rating = "rating";
    public static final String CONTACTS_COLUMN_releaseYear = "releaseYear";
    public static final String CONTACTS_COLUMN_genre = "genre";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table movies " +
                        "(title text, image text,rating text,releaseYear text, genre text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS movies");
        onCreate(db);
    }


    public void dropTableIfExist() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS movies");
    }

    public boolean insertMovie(String title, String image, String rating, String releaseYear, String genre) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("image", image);
        contentValues.put("rating", rating);
        contentValues.put("releaseYear", releaseYear);
        contentValues.put("genre", genre);
        if (getData(title) != null) {
            if (getData(title).getCount() == 0) {
                db.insert("movies", null, contentValues);
            }
        }
        return true;
    }

    public Cursor getData(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            String select = "SELECT * FROM movies where title = '" + title + "'";
            res = db.rawQuery(select, null);

        } catch (Exception e) {
            Log.d("exc", e.toString());

        }
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }


    public ArrayList<MovieResultObj> getAllMovies() {
        ArrayList<MovieResultObj> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM movies ORDER BY releaseYear DESC", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String title = res.getString(res.getColumnIndex(CONTACTS_COLUMN_title));
            String image = res.getString(res.getColumnIndex(CONTACTS_COLUMN_image));
            String rating = res.getString(res.getColumnIndex(CONTACTS_COLUMN_rating));
            String releaseYear = res.getString(res.getColumnIndex(CONTACTS_COLUMN_releaseYear));
            String genre = res.getString(res.getColumnIndex(CONTACTS_COLUMN_genre));
            MovieResultObj movieResultObj = new MovieResultObj(title, image, rating, releaseYear, genre);
            array_list.add(movieResultObj);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }
}

