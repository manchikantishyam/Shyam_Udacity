package com.example.shyamsunder.shyam_udacity.PopularMovies.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.shyamsunder.shyam_udacity.PopularMovies.datamodel.MovieDetailObject;

import java.util.ArrayList;

/**
 * Created by Shyam on 10/5/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    static Context context;
    private static final int DATABASE_VERSION = 2;
// Database Name
    private static final String KEY_VALUE = "value";
    static final String TABLE_FAVORITE_MOVIES = "favoritemovies";
    final int movie_id=0;
    final int movie_title=1;
    final int movie_releasedate=2;
    final int movie_backdrop=3;
    final int movie_overView=4;
    final int movie_rating =5;
    static final String[] KEY_FAVORITE_ATTRIBUTES={"id","title","releasedate","backdrop","overview","rating"};

    public DatabaseHandler(Context context, String ProjectName) {
        super(context, ProjectName, null, DATABASE_VERSION);
        this.context=context;
    }

    public void createprojecttables() {
       try{
           SQLiteDatabase db = this.getWritableDatabase();
           String sqlstring="";
           int k=0;
           for (int u=1; u<KEY_FAVORITE_ATTRIBUTES.length-1; u++){
               sqlstring=sqlstring+KEY_FAVORITE_ATTRIBUTES[u]+" TEXT,";
               k=u;
           }
           k++;
           sqlstring=sqlstring+KEY_FAVORITE_ATTRIBUTES[k]+" TEXT)";
           Log.d("sqlstring==", sqlstring);
           String CREATE_TABLE = "CREATE TABLE " + TABLE_FAVORITE_MOVIES + "("
                   + KEY_FAVORITE_ATTRIBUTES[movie_id] + " INTEGER PRIMARY KEY,"
                   + sqlstring;
           db.execSQL(CREATE_TABLE);
       } catch (SQLiteException e) {
           // database doesn't exist yet.
       }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Boolean checkIfMovieExist(String movieID){
        Boolean movieExits=false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query(TABLE_FAVORITE_MOVIES, null, KEY_FAVORITE_ATTRIBUTES[movie_id]+"=?",new String[]{movieID}, null, null, null, null);
        cursor.moveToFirst();
        if(cursor!=null&&cursor.getCount()>0)
            movieExits=true;
        else
            movieExits=false;

        return movieExits;
    }

    public void addMovie(String movie_ID, MovieDetailObject currentMovieDetailObject){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FAVORITE_ATTRIBUTES[movie_id], movie_ID);
        values.put(KEY_FAVORITE_ATTRIBUTES[movie_title], currentMovieDetailObject.getTitle());
        values.put(KEY_FAVORITE_ATTRIBUTES[movie_releasedate], currentMovieDetailObject.getRelease_date());
        values.put(KEY_FAVORITE_ATTRIBUTES[movie_backdrop], currentMovieDetailObject.getBackdrop_URL());
        values.put(KEY_FAVORITE_ATTRIBUTES[movie_overView], currentMovieDetailObject.getOverView());
        values.put(KEY_FAVORITE_ATTRIBUTES[movie_rating], currentMovieDetailObject.getVote_rating());

        // Inserting Row
        db.insert(TABLE_FAVORITE_MOVIES, null, values);
    }



    public void deleteMovie(String movie_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITE_MOVIES, KEY_FAVORITE_ATTRIBUTES[movie_id] + "=" + movie_ID, null);
    }

    public ArrayList<MovieDetailObject> getFavoriteMovieDetailobjects (){
        ArrayList<MovieDetailObject> lmovieDetailObjects = new ArrayList<>();
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_FAVORITE_MOVIES, null, null,
                    null, null, null, null, null);
            cursor.moveToFirst();
            int numberofrows=cursor.getCount();
            for(int y=0; y<numberofrows; y++){
                MovieDetailObject currentMovieDetailObject = new MovieDetailObject();
                currentMovieDetailObject.setID(cursor.getString(movie_id));
                currentMovieDetailObject.setBackdrop_URL(cursor.getString(movie_backdrop));
                currentMovieDetailObject.setOverView(cursor.getString(movie_overView));
                currentMovieDetailObject.setVote_rating(cursor.getString(movie_rating));
                currentMovieDetailObject.setRelease_date(cursor.getString(movie_releasedate));
                currentMovieDetailObject.setTitle(cursor.getString(movie_title));
                lmovieDetailObjects.add(currentMovieDetailObject);
                cursor.moveToNext();
            }
            cursor.close();
        }catch(Throwable e){
            e.printStackTrace();
        }
        return lmovieDetailObjects;
    }

}
