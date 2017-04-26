package com.example.administrator.interfaceview;

import com.example.administrator.entity.db.MessageTable;
import com.example.administrator.enumset.TypeChat;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface IUIMChatMainView {
    void init(List<MessageTable> messageTables,String name,String toid,String tohead,TypeChat typeChat);
}
