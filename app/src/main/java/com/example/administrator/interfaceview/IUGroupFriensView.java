package com.example.administrator.interfaceview;

import com.example.administrator.entity.Room;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/2.
 */

public interface IUGroupFriensView extends IUPublicView  {
     void init(ArrayList<Room> roomList);
}
