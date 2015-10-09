package com.example.shyamsunder.shyam_udacity.PopularMovies.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.shyamsunder.shyam_udacity.R;

public class PopularMovieDetailsScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie_details_screen);
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle currentBundle = this.getIntent().getExtras();
            Bundle arguments = new Bundle();
            arguments.putParcelable(PopularMoviesDetailFragment.DETAIL_BUNDLE, currentBundle.getParcelable(PopularMoviesHomeScreen.MOVIE_OBJECT_KEY));

            PopularMoviesDetailFragment fragment = new PopularMoviesDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_popularMovies_detail, fragment)
                    .commit();
        }


    }

}
