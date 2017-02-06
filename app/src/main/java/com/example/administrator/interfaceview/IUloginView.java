package com.example.administrator.interfaceview;

/**
 * Created by Administrator on 2017/1/21.
 */

public interface IUloginView extends IUPublicView {
    void init(String username,String password);
    String getUserName();
    String getPassword();
}
