package com.example.shyamsunder.shyam_udacity.data;

import java.io.Serializable;

/**
 * Created by Shyam on 10/4/15.
 */
public class movieTrailerObject implements Serializable {
    String video_ID;
    String key;
    String name;
    String site;
    String size;
    String language;
    String type;

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
}