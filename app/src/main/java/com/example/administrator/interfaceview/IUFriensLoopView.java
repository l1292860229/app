package com.example.administrator.interfaceview;

import com.example.administrator.entity.FriendsLoopItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/27.
 */

public interface IUFriensLoopView extends IUPublicView {
    void init(ArrayList<FriendsLoopItem> mlist);
    void initHeader();
    void loadsuccess(ArrayList<FriendsLoopItem> mlist);
    void refreshsuccess(ArrayList<FriendsLoopItem> mlist);
    void showPinLun(int position,String toUid,String toName,String hid);
    void hidePinLun();
}
