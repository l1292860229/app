package com.example.administrator.entity;


import com.example.administrator.enumset.MessageType;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2017/1/23.
 */

public class Session extends RealmObject{
    @Ignore
    public final static int READ_UNTOP=0;
    @Ignore
    public final static int UNREAD_UNTOP=1;
    @Ignore
    public final static int UNREAD_TOP=2;
    @Ignore
    public final static int READ_TOP=3;
    @PrimaryKey
    private String id;
    private String uid;
    private String fromId;		// 会话来源用户ID
    private int type = 1;
    private int isTop;//session 置顶 序号
    private String name = "";
    private String heading = "";
    private int unreadcount = 0;
    private long createtime;
    private String content;
    @Ignore
    private MessageType messageType= MessageType.TEXT;
    public Session(){
        super();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public MessageType getMessageType() {
        return MessageType.valueOf(type);
    }
    public void setMessageType(MessageType messageType) {
        this.type = messageType.getValue();
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int itemViewType(){
        if (unreadcount==0) {
            if(isTop==0){
                return  READ_UNTOP;
            }else{
                return  READ_TOP;
            }
        }else {
            if (isTop == 0) {
                return  UNREAD_UNTOP;
            } else {
                return  UNREAD_TOP;
            }
        }
    }
    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", fromId='" + fromId + '\'' +
                ", type=" + type +
                ", isTop=" + isTop +
                ", name='" + name + '\'' +
                ", heading='" + heading + '\'' +
                ", unreadcount=" + unreadcount +
                ", createtime=" + createtime +
                ", content='" + content + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
