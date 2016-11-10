package com.example.fonda.flickster;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.fonda.flickster.adapters.MovieArrayAdapter;
import com.example.fonda.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * MovieActivity
 * Main view for displaying list of current movies
 */
public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;
    private SwipeRefreshLayout swipeContainer;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        // Hide the action bar
        getSupportActionBar().hide();

        // Initialize the data model
        movies = new ArrayList<>();
        lvItems = (ListView) findViewById(R.id.lvMovies);
        // Initialize the adapter with the model
        movieAdapter = new MovieArrayAdapter(this, movies);
        // Assign the adapter to the view
        lvItems.setAdapter(movieAdapter);
        // Make the initial fetch
        fetchMoviesData();

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh the data
                fetchMoviesData();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    public void fetchMoviesData() {

        // Url for the data source
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        client = new AsyncHttpClient();

        // Send the network request to fetch the updated data
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    // Get data from response
                    movieJsonResults = response.getJSONArray("results");
                    // Populate data model after clearing first
                    movieAdapter.clear();
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    // Fire data changed event
                    movieAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);

                    Log.d("DEBUG", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
}
