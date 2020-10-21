package com.aditya.popularmovies1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    Movie movie;
    TextView mTitleTv,mUserRatingTv,mDateTv,mPlotTv;
    ImageView mPosterIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movie = getIntent().getParcelableExtra("movie");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPosterIv = findViewById(R.id.iv_poster);
        mUserRatingTv = findViewById(R.id.user_rating_tv);
        mTitleTv = findViewById(R.id.title_tv);
        mDateTv = findViewById(R.id.date_tv);
        mPlotTv = findViewById(R.id.plot_tv);

        Picasso.get().load(movie.getPoster()).into(mPosterIv);

        mTitleTv.setText(String.valueOf(movie.getOriginalTitle()));
        mPlotTv.setText(String.valueOf(movie.getOverview()));
        mDateTv.setText(String.valueOf(movie.getReleaseDate()));
        mUserRatingTv.setText(String.valueOf(movie.getVoteAverage()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
