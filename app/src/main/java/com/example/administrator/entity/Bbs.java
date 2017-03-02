package com.example.administrator.entity;

import java.io.Serializable;

/**
 * Created by yf on 2016/12/14.
 */

public class Bbs implements Serializable {
    private String id;
    private String uid;
    private String quid;
    private String title;
    private String content;
    private String headsmall;
    private int status;
    private int type;
    private int isTop;
    private int isFine;
    private int speakStatus;
    private long time;
    private String money;
    private int isvisitors;
    private int isjoin;
    private int isclosefriendloop;
    private int delchat;
    private int deldynamic;
    private int power;
    private int peopleCount=1;
    private int replyCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQuid() {
        return quid;
    }

    public void setQuid(String quid) {
        this.quid = quid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getIsFine() {
        return isFine;
    }

    public void setIsFine(int isFine) {
        this.isFine = isFine;
    }

    public int getSpeakStatus() {
        return speakStatus;
    }

    public void setSpeakStatus(int speakStatus) {
        this.speakStatus = speakStatus;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getIsvisitors() {
        return isvisitors;
    }

    public void setIsvisitors(int isvisitors) {
        this.isvisitors = isvisitors;
    }

    public int getIsjoin() {
        return isjoin;
    }

    public void setIsjoin(int isjoin) {
        this.isjoin = isjoin;
    }

    public int getIsclosefriendloop() {
        return isclosefriendloop;
    }

    public void setIsclosefriendloop(int isclosefriendloop) {
        this.isclosefriendloop = isclosefriendloop;
    }

    public int getDelchat() {
        return delchat;
    }

    public void setDelchat(int delchat) {
        this.delchat = delchat;
    }

    public int getDeldynamic() {
        return deldynamic;
    }

    public void setDeldynamic(int deldynamic) {
        this.deldynamic = deldynamic;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "Bbs{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", quid='" + quid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", isTop=" + isTop +
                ", isFine=" + isFine +
                ", speakStatus=" + speakStatus +
                ", time=" + time +
                ", money='" + money + '\'' +
                ", isvisitors=" + isvisitors +
                ", isjoin=" + isjoin +
                ", isclosefriendloop=" + isclosefriendloop +
                ", delchat=" + delchat +
                ", deldynamic=" + deldynamic +
                ", power=" + power +
                ", peopleCount=" + peopleCount +
                ", replyCount=" + replyCount +
                '}';
    }
}
