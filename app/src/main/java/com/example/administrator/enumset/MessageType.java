package com.example.administrator.enumset;

import com.google.gson.IEnum;

/**
 * Created by Administrator on 2017/3/9.
 * 获取数据方式
 */
public enum MessageType implements IEnum {
    TEXT(1),
    PICTURE(2),
    VOICE(3),
    MAP(4),
    REDPACKET(5),
    SHAREURL(6),
    CARD(7),
    INVITE(9),
    VIDEO(10);
    private int value;
    MessageType(int value){
        this.value = value;
    }
    @Override
    public int getValue(){
        return value;
    }
    @Override
    public void setValue(int value) {
        this.value = value;
    }
    public static MessageType valueOf(int value){
        switch(value){
            case 2:
                return PICTURE;
            case 3:
                return VOICE;
            case 4:
                return MAP;
            case 5:
                return REDPACKET;
            case 6:
                return SHAREURL;
            case 7:
                return CARD;
            case 9:
                return INVITE;
            case 10:
                return VIDEO;
        }
        return TEXT;
    }
}
