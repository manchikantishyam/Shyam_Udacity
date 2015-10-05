package com.example.shyamsunder.shyam_udacity.data;

import java.io.Serializable;

/**
 * Created by Shyam on 10/4/15.
 */
public class movieReviewObject implements Serializable {
    String id;
    String author;
    String content;
    String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
