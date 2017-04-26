package com.example.administrator.entity;

/**
 * Created by dell on 2017/4/25.
 */

import com.example.administrator.entity.db.MessageTable;
import com.example.administrator.enumset.MessageType;
import com.example.administrator.enumset.TypeChat;
import com.example.administrator.util.GsonUtil;

public class BaseMessage {
    protected String id;
    protected Image image;
    protected Voice voice;
    protected Location location;
    protected Redpacket  redpacket;
    protected String content;
    protected TypeChat typechat;
    protected MessageType typefile;
    protected String tag;
    protected long time;
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public Voice getVoice() {
        return voice;
    }
    public void setVoice(Voice voice) {
        this.voice = voice;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public Redpacket getRedpacket() {
        return redpacket;
    }
    public void setRedpacket(Redpacket redpacket) {
        this.redpacket = redpacket;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public TypeChat getTypechat() {
        return typechat;
    }
    public void setTypechat(TypeChat typechat) {
        this.typechat = typechat;
    }
    public MessageType getTypefile() {
        return typefile;
    }
    public void setTypefile(MessageType typefile) {
        this.typefile = typefile;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setCard(Card card){
        this.content = GsonUtil.objectToJson(card);
    }
    public Card getCard(){
        return GsonUtil.parseJsonWithGson(this.content,Card.class);
    }
    public void setInvite(Invite invite){
        this.content = GsonUtil.objectToJson(invite);
    }
    public MessageTable getMessageTable(String uid){
        MessageTable messageTable = new MessageTable();
        messageTable.setId(id);
        messageTable.setTag(tag);
        messageTable.setContent(content);
        messageTable.setTypechat(typechat.getValue());
        messageTable.setTypefile(typefile.getValue());
        messageTable.setTime(time);
        messageTable.setUid(uid);
        messageTable.setImage(GsonUtil.objectToJson(image));
        messageTable.setVoice(GsonUtil.objectToJson(voice));
        messageTable.setLocation(GsonUtil.objectToJson(location));
        messageTable.setRedpacket(GsonUtil.objectToJson(redpacket));
        messageTable.setReadState(0);
        return messageTable;
    }
    /**
     * 用户信息类
     */
    public class UserInfo{
        private String id;
        private String name;
        private String url;
        public UserInfo(){}
        public UserInfo(String id,String name,String url){
            this.id = id;
            this.name = name;
            this.url = url;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        @Override
        public String toString() {
            return "UserInfo{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    /**
     * 图片类
     */
    public class Image{
        private String urlsmall;
        private String urllarge;
        private int width;
        private int height;
        public Image(){}
        public Image(String urlsmall,String urllarge,int width,int height){
            this.urllarge = urllarge;
            this.urlsmall = urlsmall;
            this.width = width;
            this.height = height;
        }
        public String getUrlsmall() {
            return urlsmall;
        }
        public void setUrlsmall(String urlsmall) {
            this.urlsmall = urlsmall;
        }
        public String getUrllarge() {
            return urllarge;
        }
        public void setUrllarge(String urllarge) {
            this.urllarge = urllarge;
        }
        public int getWidth() {
            return width;
        }
        public void setWidth(int width) {
            this.width = width;
        }
        public int getHeight() {
            return height;
        }
        public void setHeight(int height) {
            this.height = height;
        }
    }

    /**
     * 音频类
     */
    public class Voice{
        private int time;
        private String url;
        public Voice(){}
        public Voice(int time, String url) {
            this.time = time;
            this.url = url;
        }
        public int getTime() {
            return time;
        }
        public void setTime(int time) {
            this.time = time;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }

    /**
     * 地址类
     */
    public class Location{
        private double lat;
        private double lng;
        private String address;
        public Location() {
        }
        public Location(double lat, double lng, String address) {
            this.lat = lat;
            this.lng = lng;
            this.address = address;
        }
        public double getLat() {
            return lat;
        }
        public void setLat(double lat) {
            this.lat = lat;
        }
        public double getLng() {
            return lng;
        }
        public void setLng(double lng) {
            this.lng = lng;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
    }

    /**
     * 红包类
     */
    public class Redpacket{
        private String redpackettitle;
        private String redpacketurl;
        public Redpacket() {
        }
        public Redpacket(String redpackettitle, String redpacketurl) {
            this.redpackettitle = redpackettitle;
            this.redpacketurl = redpacketurl;
        }
        public String getRedpackettitle() {
            return redpackettitle;
        }
        public void setRedpackettitle(String redpackettitle) {
            this.redpackettitle = redpackettitle;
        }
        public String getRedpacketurl() {
            return redpacketurl;
        }
        public void setRedpacketurl(String redpacketurl) {
            this.redpacketurl = redpacketurl;
        }
    }

    /**
     * 名片类
     */
    public class Card{
        private String uid;
        private String headsmall;
        private String nickname;
        private String content;
        public Card() {
        }
        public Card(String uid, String headsmall, String nickname, String content) {
            this.uid = uid;
            this.headsmall = headsmall;
            this.nickname = nickname;
            this.content = content;
        }
        public String getUid() {
            return uid;
        }
        public void setUid(String uid) {
            this.uid = uid;
        }
        public String getHeadsmall() {
            return headsmall;
        }
        public void setHeadsmall(String headsmall) {
            this.headsmall = headsmall;
        }
        public String getNickname() {
            return nickname;
        }
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }

    /**
     * 邀请类
     */
    public class Invite{
        private String id;
        private String title;
        private String content;
        private String headsmall;
        public Invite() {
        }
        public Invite(String id, String title, String content, String headsmall) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.headsmall = headsmall;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
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
    }
}

