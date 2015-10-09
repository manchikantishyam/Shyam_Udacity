package com.example.shyamsunder.shyam_udacity;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.shyamsunder.shyam_udacity.data.MovieDetailObject;
import com.example.shyamsunder.shyam_udacity.data.movieTrailerObject;
import com.squareup.picasso.Picasso;

/**
 * Created by Shyam on 10/6/15.
 */
public class PopularMoviesDetailFragment extends Fragment {
    private MovieDetailObject currentMoviedetailObject;
    static final String DETAIL_BUNDLE = "currentmovie";
    public PopularMoviesDetailFragment() {
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.popular_moviedetail_fragment, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            currentMoviedetailObject = (MovieDetailObject)arguments.getSerializable(PopularMoviesDetailFragment.DETAIL_BUNDLE);
        }

        TextView movieTitleTextView = (TextView)rootView.findViewById(R.id.movie_details_title_text);
        TextView movieReleaseYearTextView = (TextView) rootView.findViewById(R.id.movie_details_year_text);
        TextView movieRatingTextView = (TextView) rootView.findViewById(R.id.movie_details_rating_text);
        TextView movieSynopsisTextView = (TextView) rootView.findViewById(R.id.movie_details_movie_synopsis_text);
        ImageView moviePosterView = (ImageView) rootView.findViewById(R.id.movie_details_poster_view);
        LinearLayout masterDetailLinearLayout = (LinearLayout) rootView.findViewById(R.id.movie_detail_fragment);

        if(currentMoviedetailObject!=null) {
            masterDetailLinearLayout.setVisibility(View.VISIBLE);
            movieTitleTextView.setText(currentMoviedetailObject.getTitle());
            movieReleaseYearTextView.setText(currentMoviedetailObject.getRelease_date().substring(0, 4));
            movieRatingTextView.setText(currentMoviedetailObject.getVote_rating() + "/10");
            movieSynopsisTextView.setText(currentMoviedetailObject.getOverView());

            Picasso.with(getActivity()).load(currentMoviedetailObject.getBackdrop_URL()).into(moviePosterView);


            //Trailer's
            MovieDetailTrailerAdapter movieDetailTrailerAdapter = new MovieDetailTrailerAdapter(getActivity(), R.layout.movie_detail_trailer_item,
                    currentMoviedetailObject.getMovieTrailers());
            ListView movieTrailerListView = (ListView) rootView.findViewById(R.id.trailerListView);
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

            //Adding Review Section
            //Trailer's
            MovieDetailReviewAdapter movieDetailReviewAdapter = new MovieDetailReviewAdapter(getActivity(), R.layout.movie_detail_review_item,
                    currentMoviedetailObject.getMovieReviews());
            ListView movieReviewListView = (ListView) rootView.findViewById(R.id.review_ListView);
            movieReviewListView.setAdapter(movieDetailReviewAdapter);
            setListViewHeightBasedOnChildren(movieTrailerListView, movieReviewListView);

            movieReviewListView.setOnTouchListener(new View.OnTouchListener() {
                // Setting on Touch Listener for handling the touch inside ScrollView
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            final DatabaseHandler local_db = new DatabaseHandler(getActivity(), "themoviedb");
            local_db.createprojecttables();
            setMarkFavoriteTextView(local_db, rootView);
            TextView markFavoriteTextView = (TextView) rootView.findViewById(R.id.favorite_button);
            markFavoriteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (local_db.checkIfMovieExist(currentMoviedetailObject.getID())) {
                        local_db.deleteMovie(currentMoviedetailObject.getID());
                    } else {
                        local_db.addMovie(currentMoviedetailObject.getID(), currentMoviedetailObject);
                    }
                    setMarkFavoriteTextView(local_db, rootView);
                }
            });

            ScrollView scroll_view = (ScrollView) rootView.findViewById(R.id.movie_details_scroll_view);
            scroll_view.smoothScrollTo(0, 0);
        }else{
            masterDetailLinearLayout.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }
    private void setMarkFavoriteTextView(DatabaseHandler local_db, View rootView) {
        TextView markFavoriteTextView = (TextView) rootView.findViewById(R.id.favorite_button);
        if(local_db.checkIfMovieExist(currentMoviedetailObject.getID())){
            markFavoriteTextView.setBackgroundColor(getResources().getColor(R.color.grey05));
            markFavoriteTextView.setText(getResources().getText(R.string.favorite));
        }else{
            markFavoriteTextView.setBackgroundColor(getResources().getColor(R.color.teal_title));
            markFavoriteTextView.setText(getResources().getText(R.string.mark_favorite));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_popular_movie_details_screen, menu);
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
    public static void setListViewHeightBasedOnChildren(ListView listView,ListView listView2) {
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
        params = listView2.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listView2.getAdapter().getCount() - 1));
        listView2.setLayoutParams(params);
    }

}


