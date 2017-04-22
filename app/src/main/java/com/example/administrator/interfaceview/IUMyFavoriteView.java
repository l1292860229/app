package com.example.administrator.interfaceview;

import com.example.administrator.entity.FavoriteItem;

import java.util.ArrayList;

/**
 * Created by dell on 2017/4/17.
 */

public interface IUMyFavoriteView extends IUPublicView {
    void init(ArrayList<FavoriteItem> mlist);
    void loadsuccess(ArrayList<FavoriteItem> mlist);
    void refreshsuccess(ArrayList<FavoriteItem> mlist);
}
