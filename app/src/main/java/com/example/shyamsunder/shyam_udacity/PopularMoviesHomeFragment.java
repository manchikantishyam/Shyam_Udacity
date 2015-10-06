package com.example.shyamsunder.shyam_udacity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.shyamsunder.shyam_udacity.data.MovieDetailObject;
import com.example.shyamsunder.shyam_udacity.services.getAdditionalDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Shyam on 10/5/15.
 */
public class PopularMoviesHomeFragment extends Fragment {
    String LOG_TAG ="PopularMoviesHome";
    private GridView movieGridView;
    private MovieImageGridAdapter movieGridAdapter;
    private ArrayList<MovieDetailObject> movieGridData;
    public final static String MOVIE_OBJECT_KEY = "movie_detail";
    public static String MOVIE_SORT_ORDER="popularity.desc";
    public final String SORT_ORDER_KEY="sort_key";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_popular_movies_home, menu);
    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(MovieDetailObject currentMovieDetailObject);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popular) {
            TheMovieDBAPI getMoviesTask = new TheMovieDBAPI();
            MOVIE_SORT_ORDER="popularity.desc";
            getMoviesTask.execute(MOVIE_SORT_ORDER);
            return true;
        }
        if (id == R.id.action_rated) {
            TheMovieDBAPI getMoviesTask = new TheMovieDBAPI();
            MOVIE_SORT_ORDER="vote_average.desc";
            getMoviesTask.execute(MOVIE_SORT_ORDER);
            return true;
        }
        if (id == R.id.action_favorite) {
            DatabaseHandler db = new DatabaseHandler(getActivity(),"themoviedb");
            ArrayList<MovieDetailObject> MoviesList = db.getFavoriteMovieDetailobjects();
            if(MoviesList!=null&&MoviesList.size()>0){
                movieGridData = MoviesList;
                movieGridAdapter.setGridData(movieGridData);
                Toast.makeText(getActivity(), "Movies Loading", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.popular_home_fragment, container, false);

        if (savedInstanceState != null)
            MOVIE_SORT_ORDER = savedInstanceState.getString(SORT_ORDER_KEY);

        movieGridView = (GridView) rootView.findViewById(R.id.gridView);

        //Initialize with empty data
        movieGridData = new ArrayList<>();
        movieGridAdapter = new MovieImageGridAdapter(getActivity(), R.layout.movie_grid_item_layout, movieGridData);
        movieGridView.setAdapter(movieGridAdapter);

        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                MovieDetailObject currentMovieDetailObject = (MovieDetailObject) parent.getItemAtPosition(position);
                getAdditionalDetails getAdditionalDetailsTask = new getAdditionalDetails(getActivity());
                getAdditionalDetailsTask.execute(currentMovieDetailObject,getActivity());


            }
        });

        TheMovieDBAPI getMoviesTask = new TheMovieDBAPI();
        getMoviesTask.execute(MOVIE_SORT_ORDER);

        return rootView;
    }

    public class TheMovieDBAPI extends AsyncTask<String,Void,ArrayList<MovieDetailObject>> {
        @Override
        protected void onPreExecute() {}

        @Override
        protected ArrayList<MovieDetailObject> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String MoviesRespones="";
            Uri builtUri;
            try {
                String SORT_PARAM = "sort_by";
                String KEY_PARAM = "api_key";
                String sort_by = params[0];
                String API_key = getActivity().getResources().getString(R.string.popular_movie_API_key);
                String base_Url = "http://api.themoviedb.org/3/discover/movie?";
                builtUri = Uri.parse(base_Url).buildUpon()
                        .appendQueryParameter(SORT_PARAM, sort_by)
                        .appendQueryParameter(KEY_PARAM, API_key)
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                MoviesRespones = buffer.toString();


            }catch(Throwable e){
                Log.e(LOG_TAG, "Error ", e);
                return null;
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            Log.d("Shyam-URL",builtUri.toString());
            Log.d("Shyam-Movie Response",MoviesRespones);

            return getMoviesDataFromJson(MoviesRespones);
        }

        @Override
        protected void onPostExecute(ArrayList<MovieDetailObject> MoviesList) {
            if(MoviesList!=null&&MoviesList.size()>0){
                movieGridData = MoviesList;
                movieGridAdapter.setGridData(movieGridData);
                Toast.makeText(getActivity(), "Movies Loading", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data! Please check API Key", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public ArrayList<MovieDetailObject> getMoviesDataFromJson(String response){
        final String OWM_RESULTS = "results";
        final String OWM_ID = "id";
        final String OWM_TITLE = "title";
        final String OWM_RELEASE_DATE = "release_date";
        final String OWM_BACKDROP_PATH = "backdrop_path";
        final String OWM_OVERVIEW = "overview";
        final String OWM_VOTE_RATING = "vote_average";
        try {
            JSONObject movieListJson = new JSONObject(response);
            JSONArray movieListArray = movieListJson.getJSONArray(OWM_RESULTS);
            ArrayList<MovieDetailObject> parsedMovieDetailsArray = new ArrayList<MovieDetailObject>();

            for(int i = 0; i < movieListArray.length(); i++) {
                MovieDetailObject currentMovieDetailObject = new MovieDetailObject();
                JSONObject movieJsonObject = movieListArray.getJSONObject(i);
                currentMovieDetailObject.setID(movieJsonObject.getString(OWM_ID));
                currentMovieDetailObject.setTitle(movieJsonObject.getString(OWM_TITLE));
                currentMovieDetailObject.setRelease_date(movieJsonObject.getString(OWM_RELEASE_DATE));
                currentMovieDetailObject.setBackdrop_URL(currentMovieDetailObject.BaseImageURL+movieJsonObject.getString(OWM_BACKDROP_PATH));
                currentMovieDetailObject.setOverView(movieJsonObject.getString(OWM_OVERVIEW));
                currentMovieDetailObject.setVote_rating(movieJsonObject.getString(OWM_VOTE_RATING));
                parsedMovieDetailsArray.add(currentMovieDetailObject);
            }

            return parsedMovieDetailsArray;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void showToast(String toastString){
        Toast.makeText(this.getActivity(), toastString,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(SORT_ORDER_KEY, MOVIE_SORT_ORDER);
    }
}
