package com.example.shyamsunder.shyam_udacity.PopularMovies.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Shyam on 8/9/15.
 */
public class MovieDetailObject implements Parcelable {
    private String ID;
    private String Title;
    private String Backdrop_ID;
    private String Release_date;
    private String OverView;
    public String BaseImageURL="http://image.tmdb.org/t/p/w500/";
    private ArrayList<MovieTrailerObject> movieTrailers = new ArrayList<>();
    private ArrayList<MovieReviewObject> movieReviews = new ArrayList<>();

    protected MovieDetailObject(Parcel in) {
        ID = in.readString();
        Title = in.readString();
        Backdrop_ID = in.readString();
        Release_date = in.readString();
        OverView = in.readString();
        BaseImageURL = in.readString();
        Vote_rating = in.readString();
    }

    public static final Creator<MovieDetailObject> CREATOR = new Creator<MovieDetailObject>() {
        @Override
        public MovieDetailObject createFromParcel(Parcel in) {
            return new MovieDetailObject(in);
        }

        @Override
        public MovieDetailObject[] newArray(int size) {
            return new MovieDetailObject[size];
        }
    };

    public String getVote_rating() {
        return Vote_rating;
    }

    public void setVote_rating(String vote_rating) {
        Vote_rating = vote_rating;
    }

    private String Vote_rating;

    public MovieDetailObject(){
        super();
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBackdrop_URL() {
        return Backdrop_ID;
    }

    public void setBackdrop_URL(String backdrop_ID) {
        Backdrop_ID = backdrop_ID;
    }

    public String getRelease_date() {
        return Release_date;
    }

    public void setRelease_date(String release_date) {
        Release_date = release_date;
    }

    public String getOverView() {
        return OverView;
    }

    public void setOverView(String overView) {
        OverView = overView;
    }

    public ArrayList<MovieTrailerObject> getMovieTrailers() {return movieTrailers;}

    public void addMovieTrailer(MovieTrailerObject movieTrailer) {this.movieTrailers.add(movieTrailer);}

    public ArrayList<MovieReviewObject> getMovieReviews() {return movieReviews;}

    public void addMovieReviewObject(MovieReviewObject movieReview) {this.movieReviews.add(movieReview);}

    public void clearMovieTrailers(){
       movieTrailers = new ArrayList<>();
    }
    public void clearMovieReviews(){
        movieReviews = new ArrayList<>();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(Title);
        parcel.writeString(Backdrop_ID);
        parcel.writeString(Release_date);
        parcel.writeString(OverView);
        parcel.writeString(BaseImageURL);
        parcel.writeString(Vote_rating);
    }
}
