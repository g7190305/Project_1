package com.example.g7190305.instagramclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID="5d67c34b6e914a66908a95d50f3c6fdc";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photos = new ArrayList<InstagramPhoto>();
        // 1. create the adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // 2. Find the ListView form the Layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // 3. Set the Adapter binding it to the ListView
        lvPhotos.setAdapter(aPhotos);
        // Fetch the popular photos
        fetchPopularPhotos();
    }

    public void fetchPopularPhotos() {

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        Log.i("DEBUG",url);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // super.onSuccess(statusCode, headers, response);
                Log.i("DEBUG", response.toString());
                JSONArray photosJSON;


                try {
                    photosJSON = response.getJSONArray("data");
                } catch( JSONException e) {
                    photosJSON = new JSONArray();
                    e.printStackTrace();
                    // return;
                }
                    //Log.i("DEBUG", )
                    //log.i(photosJSON);
                for (int i=0; i < photosJSON.length(); i++ ) {
                    // if (photoJSON.getString("type") == "image" || photoJSON.getString("type") == "video") {
                    InstagramPhoto photo = new InstagramPhoto();

                    try {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        Log.i("DEBUG", photoJSON.toString());
                        photo.setUsername(photoJSON.getJSONObject("user").getString("username"));
                        photo.setUserImage(photoJSON.getJSONObject("user").getString("profile_picture"));
                        photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
                        photo.setType(photoJSON.getString("type"));
                        photo.setImageUrl(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setImageHeight(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        photo.setLikesCount(photoJSON.getJSONObject("likes").getInt("count"));
                        photo.setCreateTime(photoJSON.getLong("created_time"));

                        photos.add(photo);
                    } catch( JSONException e ) {
                        e.printStackTrace();
                    }
                }

                aPhotos.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // super.onFailure(statusCode, headers, responseString, throwable);
                Log.i("DEBUG","Failure");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
