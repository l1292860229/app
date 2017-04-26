package com.example.administrator.interfaceview;

import com.example.administrator.entity.Group;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/2.
 */

public interface IUGroupFriensView extends IUPublicView  {
     void init(ArrayList<Group> groupList);
     void refreshsuccess(ArrayList<Group> groupList);
}
