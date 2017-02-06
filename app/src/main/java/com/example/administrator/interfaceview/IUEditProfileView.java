package com.example.administrator.interfaceview;

import com.example.administrator.entity.UserInfo;

/**
 * Created by Administrator on 2017/1/24.
 */

public interface IUEditProfileView extends  IUPublicView {
    void init(UserInfo userInfo);
    void close();
}
