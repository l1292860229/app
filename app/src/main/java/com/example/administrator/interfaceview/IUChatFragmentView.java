package com.example.administrator.interfaceview;

import com.example.administrator.entity.Session;

import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public interface IUChatFragmentView {
    void init(List<Session> mlist);
    void load(List<Session> mlist);
    void setUnReadCount(int count);
}
