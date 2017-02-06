package com.example.administrator.entity;

/**
 * Created by Administrator on 2017/1/22.
 */

public class Menu {
    private String title;
    private String url;
    private String imageurl;
    public Menu(){}
    public Menu(String title,String url,String imageurl){
        this.title = title;
        this.url = url;
        this.imageurl = imageurl;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
