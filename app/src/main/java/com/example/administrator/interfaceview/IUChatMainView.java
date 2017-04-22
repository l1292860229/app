package com.example.administrator.interfaceview;

import com.example.administrator.entity.db.MessageTable;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface IUChatMainView {
    void init(List<MessageTable> messageTables);
}
