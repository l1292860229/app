package com.example.administrator.enumset;

import com.google.gson.IEnum;

/**
 * Created by dell on 2017/4/21.
 */

public enum TypeChat implements IEnum {
    SINGLE(100),
    GROUP(300),
    BBS(400);
    TypeChat(int value){
        this.value = value;
    }
    private int value;
    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void setValue(int i) {
        this.value = i;
    }
    public static TypeChat valueOf(int i){
        switch(i){
            case 100:
                return SINGLE;
            case 300:
                return GROUP;
            default:
                return SINGLE;
        }
    }
}
