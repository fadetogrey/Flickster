package com.example.fonda.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailsActivity extends AppCompatActivity {

    public final String ITEM_TITLE = "itemTitle";
    public final String ITEM_DATE = "itemDate";
    public final String ITEM_PATH = "itemPath";
    public final String ITEM_OVERVIEW = "itemOverview";
    public final String ITEM_RATING = "itemRating";
    public final double RATING_MAX = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // Suppress the action bar
        getSupportActionBar().hide();

        // View components
        TextView tvTitle;
        TextView tvDate;
        ImageView ivMovieImage;
        RatingBar rbMovieRating;
        TextView tvMovieOverview;

        // Hold values from Intent
        String path;
        String overview;
        String title;
        String date;
        Double rating;

        // Get the values passed in
        path = getIntent().getStringExtra(ITEM_PATH);
        title = getIntent().getStringExtra(ITEM_TITLE);
        date = getIntent().getStringExtra(ITEM_DATE);
        rating = getIntent().getDoubleExtra(ITEM_RATING, 0.0);
        overview = getIntent().getStringExtra(ITEM_OVERVIEW);

        // Get the views
        ivMovieImage = (ImageView) findViewById(R.id.ivDetailsMovieImage);
        tvTitle = (TextView) findViewById(R.id.tvDetailsMovieTitle);
        tvDate = (TextView) findViewById(R.id.tvDetailsMovieDate);
        rbMovieRating = (RatingBar) findViewById(R.id.rbDetailsRating);
        tvMovieOverview = (TextView) findViewById(R.id.tvDetailsOverview);

        // Populate the view
        tvTitle.setText(title);
        tvDate.setText(date);
        // Cap the rating at 5 stars
        if (rating > RATING_MAX) {
            rating = RATING_MAX;
        }
        rbMovieRating.setNumStars((int) Math.round(rating));
        rbMovieRating.setMax(5); // doesn't work, conflicts with layout_width
        tvMovieOverview.setText(overview);
        Picasso.with(this.getApplicationContext()).load(path)
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(R.drawable.moviepopcorn).into(ivMovieImage);

    }
}
