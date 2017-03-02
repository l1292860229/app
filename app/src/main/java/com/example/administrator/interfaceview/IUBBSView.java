package com.example.administrator.interfaceview;

import com.example.administrator.entity.Bbs;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/2.
 */

public interface IUBBSView extends  IUPublicView {
    void init(ArrayList<Bbs> bbsArrayList);
}
