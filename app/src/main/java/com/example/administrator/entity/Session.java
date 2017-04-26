package com.example.administrator.entity;


import com.example.administrator.enumset.MessageType;
import com.example.administrator.enumset.TypeChat;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

import static android.R.attr.type;

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
    private String toid;		// 会话来源用户ID
    private int typechat =100;//100-单聊 200-群聊 300-临时会话 默认为100
    private int typefile;//1-文字 2-图片 3-声音 4-位置
    private int isTop;//session 置顶 序号
    private String name = "";
    private String heading = "";
    private int unreadcount = 0;
    private long createtime;
    private String content;
    @Ignore
    private MessageType messageTypeFile= MessageType.TEXT;
    @Ignore
    private TypeChat messageTypeChat= TypeChat.SINGLE;
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

    public String getToid() {
        return toid;
    }

    public void setToid(String toid) {
        this.toid = toid;
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
    public int getTypechat() {
        return typechat;
    }
    public void setTypechat(int typechat) {
        this.typechat = typechat;
    }
    public int getTypefile() {
        return typefile;
    }
    public void setTypefile(int typefile) {
        this.typefile = typefile;
    }
    public MessageType getMessageTypeFile() {
        return MessageType.valueOf(typefile);
    }
    public void setMessageTypeFile(MessageType messageTypeFile) {
        this.typefile = messageTypeFile.getValue();
    }
    public TypeChat getMessageTypeChat() {
        return TypeChat.valueOf(typechat);
    }
    public void setMessageTypeChat(TypeChat messageTypeChat) {
        this.typechat = messageTypeChat.getValue();
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
                ", toid='" + toid + '\'' +
                ", type=" + type +
                ", isTop=" + isTop +
                ", name='" + name + '\'' +
                ", heading='" + heading + '\'' +
                ", unreadcount=" + unreadcount +
                ", createtime=" + createtime +
                ", content='" + content + '\'' +
                '}';
    }
}
