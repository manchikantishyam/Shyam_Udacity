package com.example.shyamsunder.shyam_udacity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PopularMovieDetailsScreen extends AppCompatActivity {
    private MovieDetailObject currentMoviedetailObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie_details_screen);
        Bundle currentBundle = this.getIntent().getExtras();
        if (currentBundle!=null);
        currentMoviedetailObject = (MovieDetailObject) currentBundle.getSerializable(PopularMoviesHome.MOVIE_OBJECT_KEY);

        TextView movieTitleTextView = (TextView) findViewById(R.id.movie_details_title_text);
        TextView movieReleaseYearTextView = (TextView) findViewById(R.id.movie_details_year_text);
        TextView movieRatingTextView = (TextView) findViewById(R.id.movie_details_rating_text);
        TextView movieSynopsisTextView = (TextView) findViewById(R.id.movie_details_movie_synopsis_text);
        ImageView moviePosterView = (ImageView) findViewById(R.id.movie_details_poster_view);

        movieTitleTextView.setText(currentMoviedetailObject.getTitle());
        movieReleaseYearTextView.setText(currentMoviedetailObject.getRelease_date().substring(0,4));
        movieRatingTextView.setText(currentMoviedetailObject.getVote_rating()+"/10");
        movieSynopsisTextView.setText(currentMoviedetailObject.getOverView());

        Picasso.with(this).load(currentMoviedetailObject.getBackdrop_URL()).into(moviePosterView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_movie_details_screen, menu);
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
