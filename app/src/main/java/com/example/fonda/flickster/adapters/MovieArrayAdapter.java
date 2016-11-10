package com.example.fonda.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
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

    private static class ViewHolder {
        TextView title;
        ImageView image;
        TextView overview;
    }

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
        // Get the data item for this position
        Movie movie = getItem(position);

        // Get the existing view being reused
        ViewHolder viewHolder;
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.image.setImageResource(0);
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());
        //use 3rd party lib to set the image

        String path = "";
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            path = movie.getBackdropPath();
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            path = movie.getPosterPath();
        }
        Picasso.with(getContext()).load(path).into(viewHolder.image);

        return convertView;
    }
}
