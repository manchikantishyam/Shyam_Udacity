package com.example.shyamsunder.shyam_udacity;

/**
 * Created by Shyam on 8/9/15.
 */
public class MovieDetailObject {
    private String ID;
    private String Title;
    private String Backdrop_ID;
    private String Release_date;
    private String OverView;

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

    public String getBackdrop_ID() {
        return Backdrop_ID;
    }

    public void setBackdrop_ID(String backdrop_ID) {
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

}
