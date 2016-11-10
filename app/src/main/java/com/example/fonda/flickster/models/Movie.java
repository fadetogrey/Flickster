package com.example.fonda.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fonda on 11/7/16.
 * Movie is a template for the items in the data model.
 */

public class Movie {

    String backdropPath;
    String originalTitle;
    String overview;
    String posterPath;

    /**
     * Constructs a Movie object
     * @param jsonObject Contains movie metadata
     * @throws JSONException
     */
    public Movie(JSONObject jsonObject) throws JSONException {
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.posterPath = jsonObject.getString("poster_path");
    }

    /**
     * Creates a list of Movie objects
     * @param array Array of response objects
     * @return ArrayList of Movie objects
     */
    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie((JSONObject) array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    /**
     * Returns a formatted URL for the backdrop image
     * @return Formatted Image URL
     */
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    /**
     * Returns a formatted URL for the poster image
     * @return Formatted Image URL
     */
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    /**
     * Returns the movie title
     * @return String representing movie title
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Returns the movie overview
     * @return String respresenting movie overview
     */
    public String getOverview() {
        return overview;
    }

}
