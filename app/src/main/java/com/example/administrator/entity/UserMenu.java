package com.example.administrator.entity;

import java.io.Serializable;

/**
 * Created by yf on 2017/3/22.
 */

public class UserMenu implements Serializable {
    private String id;
    private String uid;
    private String menuname;
    private String menuurl;
    public UserMenu(){}
    public UserMenu(String uid, String menuname, String menuurl){
        this.uid = uid;
        this.menuname = menuname;
        this.menuurl = menuurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getMenuurl() {
        return menuurl;
    }

    public void setMenuurl(String menuurl) {
        this.menuurl = menuurl;
    }
}
