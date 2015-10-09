package com.example.shyamsunder.shyam_udacity.PopularMovies.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.example.shyamsunder.shyam_udacity.PopularMovies.view.PopularMoviesHomeFragment;
import com.example.shyamsunder.shyam_udacity.PopularMovies.datamodel.MovieDetailObject;
import com.example.shyamsunder.shyam_udacity.PopularMovies.datamodel.MovieReviewObject;
import com.example.shyamsunder.shyam_udacity.PopularMovies.datamodel.MovieTrailerObject;
import com.example.shyamsunder.shyam_udacity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shyam on 10/3/15.
 */
public class GetAdditionalDetails extends AsyncTask<Object, Void, MovieDetailObject> {
    private final Context mContext;
    private String LOG_TAG;
    ProgressDialog progress;
    PopularMoviesHomeFragment.Callback callback;

    public GetAdditionalDetails(Context context) {
        mContext = context;
        LOG_TAG=context.getClass().toString();
        progress= new ProgressDialog(mContext);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress= new ProgressDialog(mContext);
        progress.setTitle("Loading");
        progress.setMessage("Please Wait....");
        progress.show();
    }
    @Override
    protected MovieDetailObject doInBackground(Object... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String MovieTrailerRespone="";
        String MoviewReviewsResponse="";
        MovieDetailObject currentMovieDetailObject= (MovieDetailObject) params[0];
        callback = (PopularMoviesHomeFragment.Callback) params[1];
        Uri builtUri;
        String KEY_PARAM = "api_key";
        String ID = currentMovieDetailObject.getID();
        String API_key = mContext.getString(R.string.popular_movie_API_key);
        try {
            String base_Url = "http://api.themoviedb.org/3/movie/"+ID+"/videos?";
            builtUri = Uri.parse(base_Url).buildUpon()
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
            MovieTrailerRespone = buffer.toString();


        }catch(Throwable e){
            MovieTrailerRespone="";
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
        Log.d("Shyam-MovieTrailerRes",MovieTrailerRespone);
        currentMovieDetailObject = AddMovieTrailerToObject(currentMovieDetailObject,MovieTrailerRespone);

        try {
            String base_Url = "http://api.themoviedb.org/3/movie/"+ID+"/reviews?";
            builtUri = Uri.parse(base_Url).buildUpon()
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
            MoviewReviewsResponse = buffer.toString();


        }catch(Throwable e){
            Log.e(LOG_TAG, "Error ", e);
            MoviewReviewsResponse="";
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
        Log.d("Shyam-MovieReviewRes",MoviewReviewsResponse);
        currentMovieDetailObject = AddMovieReviewsToObject(currentMovieDetailObject,MoviewReviewsResponse);

        return currentMovieDetailObject;
    }

    private MovieDetailObject AddMovieReviewsToObject(MovieDetailObject currentMovieDetailObject, String response) {
        MovieDetailObject lmovieDetailObject=currentMovieDetailObject;
        final String OWM_RESULTS = "results";
        final String OWM_ID = "id";
        final String OWM_LANGUAGE = "author";
        final String OWM_KEY = "content";
        final String OWM_NAME = "url";
        try {
            JSONObject movieListJson = new JSONObject(response);
            JSONArray movieTrailerArray = movieListJson.getJSONArray(OWM_RESULTS);
            lmovieDetailObject.clearMovieReviews();
            for(int i = 0; i < movieTrailerArray.length(); i++) {
                MovieReviewObject currentReviewObject = new MovieReviewObject();
                JSONObject movieJsonObject = movieTrailerArray.getJSONObject(i);
                currentReviewObject.setId(movieJsonObject.getString(OWM_ID));
                currentReviewObject.setAuthor(movieJsonObject.getString(OWM_LANGUAGE));
                currentReviewObject.setContent(movieJsonObject.getString(OWM_KEY));
                currentReviewObject.setUrl(movieJsonObject.getString(OWM_NAME));
                lmovieDetailObject.addMovieReviewObject(currentReviewObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lmovieDetailObject;
    }

    private MovieDetailObject AddMovieTrailerToObject(MovieDetailObject currentMovieDetailObject, String response) {
        MovieDetailObject lmovieDetailObject=currentMovieDetailObject;
        final String OWM_RESULTS = "results";
        final String OWM_ID = "id";
        final String OWM_LANGUAGE = "iso_639_1";
        final String OWM_KEY = "key";
        final String OWM_NAME = "name";
        final String OWM_SIZE = "size";
        final String OWM_SITE = "site";
        final String OWM_TYPE = "type";
        try {
            JSONObject movieListJson = new JSONObject(response);
            JSONArray movieTrailerArray = movieListJson.getJSONArray(OWM_RESULTS);
            lmovieDetailObject.clearMovieTrailers();
            for(int i = 0; i < movieTrailerArray.length(); i++) {
                MovieTrailerObject currentTrailerObject = new MovieTrailerObject();
                JSONObject movieJsonObject = movieTrailerArray.getJSONObject(i);
                currentTrailerObject.setVideo_ID(movieJsonObject.getString(OWM_ID));
                currentTrailerObject.setLanguage(movieJsonObject.getString(OWM_LANGUAGE));
                currentTrailerObject.setKey(movieJsonObject.getString(OWM_KEY));
                currentTrailerObject.setName(movieJsonObject.getString(OWM_NAME));
                currentTrailerObject.setSize(movieJsonObject.getString(OWM_SIZE));
                currentTrailerObject.setSite(movieJsonObject.getString(OWM_SITE));
                currentTrailerObject.setType(movieJsonObject.getString(OWM_TYPE));
                lmovieDetailObject.addMovieTrailer(currentTrailerObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lmovieDetailObject;
    }


    @Override
    protected void onPostExecute(MovieDetailObject movieDetailObject) {
        super.onPostExecute(movieDetailObject);
        (callback)
                .onItemSelected(movieDetailObject);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
            }
        }, 300); // starting it in 1 second

    }
}
