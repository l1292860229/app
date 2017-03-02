package com.example.administrator.interfaceview;

import com.example.administrator.entity.UserInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/28.
 */

public interface IUContactsFragmentView extends IUPublicView {
    void init(ArrayList<UserInfo> userInfos);
    void loadsuccess(ArrayList<UserInfo> userInfos);
    void refreshsuccess(ArrayList<UserInfo> userInfos);
}
