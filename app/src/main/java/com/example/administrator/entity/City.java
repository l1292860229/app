package com.example.administrator.entity;

/**
 * Created by Administrator on 2017/1/25.
 */

public class City {
    private String id;
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return  city;
    }
}
