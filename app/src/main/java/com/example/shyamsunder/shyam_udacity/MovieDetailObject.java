package com.example.shyamsunder.shyam_udacity;

import java.io.Serializable;

/**
 * Created by Shyam on 8/9/15.
 */
public class MovieDetailObject implements Serializable {
    private String ID;
    private String Title;
    private String Backdrop_ID;
    private String Release_date;
    private String OverView;
    private String BaseImageURL="http://image.tmdb.org/t/p/w500/";

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
        Backdrop_ID = BaseImageURL+backdrop_ID;
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

}
