package com.example.shyamsunder.shyam_udacity.PopularMovies.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shyam on 10/4/15.
 */
public class MovieTrailerObject implements Parcelable {
    String video_ID;
    String key;
    String name;
    String site;
    String size;
    String language;
    String type;

    public MovieTrailerObject(){
        super();
    }

    protected MovieTrailerObject(Parcel in) {
        video_ID = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        size = in.readString();
        language = in.readString();
        type = in.readString();
    }

    public static final Creator<MovieTrailerObject> CREATOR = new Creator<MovieTrailerObject>() {
        @Override
        public MovieTrailerObject createFromParcel(Parcel in) {
            return new MovieTrailerObject(in);
        }

        @Override
        public MovieTrailerObject[] newArray(int size) {
            return new MovieTrailerObject[size];
        }
    };

    public String getVideo_ID() {
        return video_ID;
    }

    public void setVideo_ID(String video_ID) {
        this.video_ID = video_ID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(video_ID);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(size);
        parcel.writeString(language);
        parcel.writeString(type);
    }
}