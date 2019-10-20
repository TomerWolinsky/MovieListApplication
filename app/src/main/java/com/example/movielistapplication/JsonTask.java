package com.example.movielistapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class JsonTask extends AsyncTask<String, String, String> {

    ArrayList<MovieResultObj> movieList = new ArrayList<>();
    private AsyncResponse delegate;
    String url;


    public JsonTask(String url, AsyncResponse delegate) {
        this.url = url;
        this.delegate = delegate;
    }

    protected String doInBackground(String... params) {

        URLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(this.url);
            connection = url.openConnection();
            String redirect = connection.getHeaderField("Location");
            if (redirect != null) {
                connection = new URL(redirect).openConnection();
            } else {
                connection.connect();
            }

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                // connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MovieResultObj movieResultObj = new MovieResultObj(jsonObject.getString("title"), jsonObject.getString("image"), jsonObject.getString("rating"), jsonObject.getString("releaseYear"), jsonObject.getString("genre"));
                movieList.add(movieResultObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        delegate.processFinish(movieList);
    }

}
