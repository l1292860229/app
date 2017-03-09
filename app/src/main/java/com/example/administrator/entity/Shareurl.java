package com.example.administrator.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/8.
 */

public class Shareurl implements Serializable {
    private String url;
    private String title;
    private String imageurl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
