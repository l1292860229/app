package com.example.administrator.entity;

import com.example.administrator.entity.db.MessageTable;
import com.example.administrator.util.GsonUtil;

/**
 * Created by dell on 2017/4/22.
 */

public class IMMessage extends BaseMessage {
    private UserInfo from;
    private UserInfo to;
    public UserInfo getFrom() {
        return from;
    }
    public void setFrom(UserInfo from) {
        this.from = from;
    }
    public UserInfo getTo() {
        return to;
    }
    public void setTo(UserInfo to) {
        this.to = to;
    }
    @Override
    public MessageTable getMessageTable(String uid){
        MessageTable messageTable = super.getMessageTable(uid);
        messageTable.setToid(to.getId());
        messageTable.setFrom(GsonUtil.objectToJson(from));
        messageTable.setTo(GsonUtil.objectToJson(to));
        return messageTable;
    }
    @Override
    public String toString() {
        return "IMMessage{" +
                "from=" + from +
                ", to=" + to +
                ", image=" + image +
                ", voice=" + voice +
                ", location=" + location +
                ", redpacket=" + redpacket +
                ", content='" + content + '\'' +
                ", typechat=" + typechat +
                ", typefile=" + typefile +
                ", tag='" + tag + '\'' +
                ", time=" + time +
                ", id='" + id + '\'' +
                '}';
    }
}
