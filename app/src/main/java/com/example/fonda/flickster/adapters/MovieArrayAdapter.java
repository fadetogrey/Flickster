package com.example.fonda.flickster.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fonda.flickster.R;
import com.example.fonda.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by fonda on 11/7/16.
 * Controller for the Movie model and MovieActivity view.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    /**
     * Populates and returns the View object with item from data model
     * @param position Position of item in the list
     * @param convertView
     * @param parent
     * @return Populated View
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for this position
        Movie movie = getItem(position);

        //get the existing view being reused
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        //find the image view widget
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
        ivImage.setImageResource(0);

        //find the movie title widget
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        //find the overview widget
        TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);

        //populate data to the widgets
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());
        //use 3rd party lib to set the image
        Picasso.with(getContext()).load(movie.getPosterPath()).into(ivImage);

        return convertView;
    }
}
