package com.example.shyamsunder.shyam_udacity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

public class PopularMoviesHome extends AppCompatActivity {
    String LOG_TAG ="PopularMoviesHome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies_home);
        showToast("PopularMovies started");
        TheMovieDBAPI getMoviesTask = new TheMovieDBAPI();
        getMoviesTask.execute("popularity.desc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_movies_home, menu);
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

    public class TheMovieDBAPI extends AsyncTask<String,Void,ArrayList<MovieDetailObject>> {

        @Override
        protected void onPostExecute(ArrayList<MovieDetailObject> MoviesList) {

        }

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
                String API_key = getApplicationContext().getResources().getString(R.string.popular_movie_API_key);
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


            }catch(IOException e){
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

//            try {
                return getMoviesDataFromJson(MoviesRespones);
//            } catch (JSONException e) {
//                Log.e(LOG_TAG, e.getMessage(), e);
//                e.printStackTrace();
//            }


//            return null;
        }

        @Override
        protected void onPreExecute() {}

    }

    public ArrayList<MovieDetailObject> getMoviesDataFromJson(String response){
        final String OWM_RESULTS = "results";
        final String OWM_ID = "id";
        final String OWM_TITLE = "title";
        final String OWM_RELEASE_DATE = "release_date";
        final String OWM_BACKDROP_PATH = "backdrop_path";
        final String OWM_OVERVIEW = "overview";
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
                currentMovieDetailObject.setBackdrop_ID(movieJsonObject.getString(OWM_BACKDROP_PATH));
                currentMovieDetailObject.setOverView(movieJsonObject.getString(OWM_OVERVIEW));
                parsedMovieDetailsArray.add(currentMovieDetailObject);

            }

            return parsedMovieDetailsArray;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    public void showToast(String toastString){
        Toast.makeText(this.getBaseContext(), toastString,
                Toast.LENGTH_SHORT).show();
    }
}
