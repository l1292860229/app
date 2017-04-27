package com.example.administrator.imservice;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;

/**
 * Created by dell on 2017/4/27.
 */

public class IMChatManagerListener implements ChatManagerListener {
    @Override
    public void chatCreated(Chat chat, boolean b) {
        if(!b){
            chat.addMessageListener(new IMMessageListener());
        }
    }
}
