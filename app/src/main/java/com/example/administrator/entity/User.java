package com.example.administrator.entity;

import android.databinding.ObservableField;

/**
 * Created by Administrator on 2017/1/21.
 */

/**
 * 登录用的用户信息
 */
public class User {
    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public User(){}
    public User(String username,String password){
        this.username.set(username);
        this.password.set(password);
    }
}
