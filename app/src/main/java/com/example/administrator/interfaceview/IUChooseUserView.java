package com.example.administrator.interfaceview;

import com.example.administrator.entity.UserInfo;

import java.util.ArrayList;

/**
 * Created by dell on 2017/4/25.
 */

public interface IUChooseUserView {
    void init(ArrayList<UserInfo> userInfos,boolean isMultiple);
}
