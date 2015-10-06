package com.example.shyamsunder.shyam_udacity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.shyamsunder.shyam_udacity.data.MovieDetailObject;

public class PopularMoviesHome extends AppCompatActivity implements PopularMoviesHomeFragment.Callback {
    private boolean mTwoPane;
    private static final String MOVIE_DETAILFRAGMENT_TAG = "DFTAG";
    public final static String MOVIE_OBJECT_KEY = "movie_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies_home);
        if (findViewById(R.id.fragment_popularMovies_detail) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_popularMovies_detail, new PopularMoviesDetailFragment(), MOVIE_DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
    }


    @Override
    public void onItemSelected(MovieDetailObject selectedMovieDetailObject) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putSerializable(PopularMoviesDetailFragment.DETAIL_BUNDLE, selectedMovieDetailObject);

            PopularMoviesDetailFragment fragment = new PopularMoviesDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_popularMovies_detail, fragment, MOVIE_DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(PopularMoviesHome.MOVIE_OBJECT_KEY, selectedMovieDetailObject);
            intent.putExtras(bundle);
            intent.setClass(PopularMoviesHome.this, PopularMovieDetailsScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }
}
