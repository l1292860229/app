package com.example.administrator.entity;


/**
 * Created by Administrator on 2017/1/23.
 */

public class Session{
    private static final long serialVersionUID = 5389219102904727377L;
    private String fromId;		// 会话来源用户ID
    private int type = 0;
    private int isTop;//session 置顶 序号
    private MessageInfo mMessageInfo;
    private String name = "";
    private String heading = "";
    private int unreadcount = 0;
    public Session(){
        super();
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public String getFromId() {
        return fromId;
    }
    public void setFromId(String fromId) {
        this.fromId = fromId;
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
    public MessageInfo getmMessageInfo() {
        return mMessageInfo;
    }
    public void setmMessageInfo(MessageInfo mMessageInfo) {
        this.mMessageInfo = mMessageInfo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHeading() {
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }
    public int getUnreadcount() {
        return unreadcount;
    }
    public void setUnreadcount(int unreadcount) {
        this.unreadcount = unreadcount;
    }
}
