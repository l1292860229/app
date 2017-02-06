package com.example.administrator.interfaceview;

/**
 * Created by Administrator on 2017/1/24.
 */

public interface IUPrivateSetView {
    void init(String title);
    void showPrivacy(boolean addFriendVerify,boolean recommendContactsFriend);
    void showNotice(boolean systemSettingNotice,boolean noticeSound,boolean noticeShock);
}
