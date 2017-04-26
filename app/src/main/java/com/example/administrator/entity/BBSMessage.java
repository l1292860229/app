package com.example.administrator.entity;

import com.example.administrator.entity.db.MessageTable;
import com.google.gson.IEnum;

/**
 * Created by dell on 2017/4/22.
 */

public class BBSMessage extends BaseMessage {
    public  enum  PowerType implements IEnum {
        GENERAL(0),ADMIN(1);
        private int value;
        PowerType(int value){
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
    }
    public  enum  DelchatType implements IEnum {
        NODEL(0),CANDEL(1);
        private int value;
        DelchatType(int value){
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
    }
    public  enum  DeldynamicType implements IEnum {
        NODEL(0),CANDEL(1);
        private int value;
        DeldynamicType(int value){
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
    }
    private String bid;
    private String uid;
    private String nickname;
    private String headsmall;
    private String phone;
    private String job;
    private String company;
    private PowerType power;
    private DelchatType delchat;
    private DeldynamicType deldynamic;
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getHeadsmall() {
        return headsmall;
    }
    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public PowerType getPower() {
        return power;
    }
    public void setPower(PowerType power) {
        this.power = power;
    }
    public DelchatType getDelchat() {
        return delchat;
    }
    public void setDelchat(DelchatType delchat) {
        this.delchat = delchat;
    }
    public DeldynamicType getDeldynamic() {
        return deldynamic;
    }
    public void setDeldynamic(DeldynamicType deldynamic) {
        this.deldynamic = deldynamic;
    }
    public String getBid() {
        return bid;
    }
    public void setBid(String bid) {
        this.bid = bid;
    }
    @Override
    public MessageTable getMessageTable(String uid) {
        MessageTable messageTable =  super.getMessageTable(uid);
        messageTable.setBid(bid);
        messageTable.setToid("bbs_"+bid);
        return messageTable;
    }

    @Override
    public String toString() {
        return "BBSMessage{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", phone='" + phone + '\'' +
                ", job='" + job + '\'' +
                ", company='" + company + '\'' +
                ", power=" + power +
                ", delchat=" + delchat +
                ", deldynamic=" + deldynamic +
                '}';
    }
}
