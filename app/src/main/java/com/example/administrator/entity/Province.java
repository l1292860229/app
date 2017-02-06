package com.example.administrator.entity;

/**
 * Created by Administrator on 2017/1/25.
 */

public class Province {
    private String state;
    private String id;
    private City[] cities;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public City[] getCities() {
        return cities;
    }
    public void setCities(City[] cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return state;
    }
}
