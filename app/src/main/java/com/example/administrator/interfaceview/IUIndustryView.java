package com.example.administrator.interfaceview;

import com.example.administrator.entity.Bbs;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface IUIndustryView extends  IUPublicView {
    void init(ArrayList<Bbs> bbsArrayList);
    void loadsuccess(ArrayList<Bbs> bbsArrayList);
    void refreshsuccess(ArrayList<Bbs> bbsArrayList);
}
