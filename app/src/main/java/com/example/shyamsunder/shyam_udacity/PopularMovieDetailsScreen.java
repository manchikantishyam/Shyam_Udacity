package com.example.shyamsunder.shyam_udacity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.shyamsunder.shyam_udacity.data.MovieDetailObject;
import com.example.shyamsunder.shyam_udacity.data.movieTrailerObject;
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

        //Trailer's
        MovieDetailTrailerAdapter movieDetailTrailerAdapter = new MovieDetailTrailerAdapter(PopularMovieDetailsScreen.this,R.layout.movie_detail_trailer_item,
                                                                    currentMoviedetailObject.getMovieTrailers());
        ListView movieTrailerListView = (ListView) findViewById(R.id.trailerListView);
        movieTrailerListView.setAdapter(movieDetailTrailerAdapter);

        movieTrailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                movieTrailerObject currentTrailerObject = (movieTrailerObject) parent.getItemAtPosition(position);
                String urlStr = "http://www.youtube.com/watch?v=" + currentTrailerObject.getKey();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlStr));
                startActivity(intent);
            }
        });
        setListViewHeightBasedOnChildren(movieTrailerListView);

        //Adding Review Section
        //Trailer's
        MovieDetailReviewAdapter movieDetailReviewAdapter = new MovieDetailReviewAdapter(PopularMovieDetailsScreen.this,R.layout.movie_detail_review_item,
                currentMoviedetailObject.getMovieReviews());
        ListView movieReviewListView = (ListView) findViewById(R.id.review_ListView);
        movieReviewListView.setAdapter(movieDetailReviewAdapter);

        setListViewHeightBasedOnChildren(movieReviewListView);

        ScrollView scroll_view = (ScrollView) findViewById(R.id.movie_details_scroll_view);
        scroll_view.smoothScrollTo(0, 0);

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
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
