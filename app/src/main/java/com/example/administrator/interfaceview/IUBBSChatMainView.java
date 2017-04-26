package com.example.administrator.interfaceview;

import com.example.administrator.entity.BBSMessage;
import com.example.administrator.entity.Bbs;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface IUBBSChatMainView {
    void init(List<BBSMessage> bbsMessageList, String bid, String name, String head, Bbs.Bbstype bbstype, int max, int min);
    void add(BBSMessage bbsMessage);
}
