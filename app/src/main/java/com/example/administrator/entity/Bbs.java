package com.example.administrator.entity;

import com.google.gson.IEnum;

import java.io.Serializable;

/**
 * Created by yf on 2016/12/14.
 */

public class Bbs implements Serializable {
    //bbs的类型,枚举类的必须implements IEnum才能被json解析
    public  enum Bbstype implements IEnum {
        MILLION(0),
        INDUSTRY(1);
        private int value;
        Bbstype(int value){
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
    //用户是否已经加入
    public  enum Userjoin implements IEnum{
        JOIN_NOJOIN(0),
        JOIN_ISJOIN(1),
        JOIN_REVIEW(2);
        private int value;
        Userjoin(int value){
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
    //是否有逛逛的权限
    public  enum Visitors implements IEnum{
        NOVISITORS(0),
        CANVISITOR(1);
        private int value;
        Visitors(int value){
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
    //删除聊天信息的权限
    public  enum Delchatpower implements IEnum{
        NODELCHAT(0),
        CANDELCHAT(1);
        private int value;
        Delchatpower(int value){
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
    //删除动态的权限
    public  enum Deldynamicpower implements IEnum{
        NODELDYNAMIC(0),
        CANDELDYNAMIC(1);
        private int value;
        Deldynamicpower(int value){
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
    //power的值
    public  enum Userpower implements IEnum{
        ORDINARYPOWER(0),
        ADMINPOWER(1);
        private int value;
        Userpower(int value){
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
    //关闭动态权限
    public  enum Closefriendloop implements IEnum{
        noclosefriendloop(0),
        canclosefriendloop(1);
        private int value;
        Closefriendloop(int value){
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
    private String id;
    private String uid;
    private String quid;
    private String title;
    private String content;
    private String headsmall;
    private int status;
    private Bbstype type;
    private int isTop;
    private int isFine;
    private int speakStatus;
    private long time;
    private String money;
    private Visitors isvisitors;
    private Userjoin isjoin;
    private Closefriendloop isclosefriendloop;
    private Delchatpower delchat;
    private Deldynamicpower deldynamic;
    private Userpower power;
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

    public Bbstype getType() {
        return type;
    }

    public void setType(Bbstype type) {
        this.type = type;
    }

    public Visitors getIsvisitors() {
        return isvisitors;
    }

    public void setIsvisitors(Visitors isvisitors) {
        this.isvisitors = isvisitors;
    }

    public Userjoin getIsjoin() {
        return isjoin;
    }

    public void setIsjoin(Userjoin isjoin) {
        this.isjoin = isjoin;
    }

    public Closefriendloop getIsclosefriendloop() {
        return isclosefriendloop;
    }

    public void setIsclosefriendloop(Closefriendloop isclosefriendloop) {
        this.isclosefriendloop = isclosefriendloop;
    }

    public Delchatpower getDelchat() {
        return delchat;
    }

    public void setDelchat(Delchatpower delchat) {
        this.delchat = delchat;
    }

    public Deldynamicpower getDeldynamic() {
        return deldynamic;
    }

    public void setDeldynamic(Deldynamicpower deldynamic) {
        this.deldynamic = deldynamic;
    }

    public Userpower getPower() {
        return power;
    }

    public void setPower(Userpower power) {
        this.power = power;
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
