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

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by fonda on 11/7/16.
 * Controller for the Movie model and MovieActivity view.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public static final int POPULAR_THRESHOLD = 5;

    private static class ViewHolder {
        ImageView image;
        TextView overview;
        TextView title;
    }

    private static class ViewHolderPopular {
        ImageView image;
        TextView overview;
        TextView title;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    /**
     * Return an integer representing the type by fetching the enum type ordinal
     */
    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getVoteAverage() > POPULAR_THRESHOLD) {
            return Movie.MovieImageTypes.BACKDROP.ordinal();
        }
        return Movie.MovieImageTypes.POSTER.ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return Movie.MovieImageTypes.values().length;
    }

    /**
     * Populates and returns the View object with item from data model
     * Uses Row View Recycling and ViewHolder Pattern for performance
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
        // Get the view type for this position;
        int viewType = getItemViewType(position);
        String path = "";

        if (viewType == Movie.MovieImageTypes.BACKDROP.ordinal()) {
            ViewHolderPopular viewHolderPopular;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_movie_popular, parent, false);
                viewHolderPopular = new ViewHolderPopular();
            } else {
                viewHolderPopular = (ViewHolderPopular) convertView.getTag();
            }
            viewHolderPopular.overview = (TextView) convertView.findViewById(R.id.tvOverviewPopular);
            viewHolderPopular.title = (TextView) convertView.findViewById(R.id.tvTitlePopular);
            viewHolderPopular.image = (ImageView) convertView.findViewById(R.id.ivMovieImagePopular);
            viewHolderPopular.image.setImageResource(0);
            convertView.setTag(viewHolderPopular);
            // Populate the data from the data object via the viewHolder object
            // into the template view.
            if (viewHolderPopular.title != null) {
                viewHolderPopular.title.setText(movie.getOriginalTitle());
            }
            if (viewHolderPopular.overview != null) {
                viewHolderPopular.overview.setText(movie.getOverview());
            }
            //use 3rd party lib to set the image
            path = movie.getBackdropPath();
            //TODO: Check with Nathan, fit() resizes the image
            Picasso.with(getContext()).load(path)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .placeholder(R.drawable.moviepopcorn).into(viewHolderPopular.image);
        } else if (viewType == Movie.MovieImageTypes.POSTER.ordinal()) {
            ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder();
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.image.setImageResource(0);
            convertView.setTag(viewHolder);
            // Populate the data from the data object via the viewHolder object
            // into the template view.
            viewHolder.title.setText(movie.getOriginalTitle());
            viewHolder.overview.setText(movie.getOverview());
            //use 3rd party lib to set the image
            path = movie.getPosterPath();
            //TODO: Check with Nathan, fit() resizes the image
            Picasso.with(getContext()).load(path)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .placeholder(R.drawable.moviepopcorn).into(viewHolder.image);
        }
        return convertView;
    }
}
