package com.example.administrator.imservice;

import android.util.Log;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by dell on 2017/4/27.
 */

public class IMMessageListener implements MessageListener {
    @Override
    public void processMessage(Chat chat, Message message) {
        String chatId = chat.getParticipant().split("@")[0];  // 发来消息的用户
        String content = message.getBody();					// 发送来的内容.
        Log.e("IMMessageListener","chatId="+chatId);
        Log.e("IMMessageListener","content="+content);
    }
}
