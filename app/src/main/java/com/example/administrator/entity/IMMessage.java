package com.example.administrator.entity;

import com.example.administrator.entity.db.MessageTable;
import com.example.administrator.enumset.MessageType;
import com.example.administrator.enumset.TypeChat;
import com.example.administrator.util.GsonUtil;

/**
 * Created by dell on 2017/4/22.
 */

public class IMMessage {
    private UserInfo from;
    private String bid;
    private UserInfo to;
    private Image image;
    private Voice voice;
    private Location location;
    private Redpacket  redpacket;
    private String content;
    private TypeChat typechat;
    private MessageType typefile;
    private String tag;
    private long time;
    private String id;
    public UserInfo getFrom() {
        return from;
    }
    public void setFrom(UserInfo from) {
        this.from = from;
    }
    public String getBid() {
        return bid;
    }
    public void setBid(String bid) {
        this.bid = bid;
    }
    public UserInfo getTo() {
        return to;
    }
    public void setTo(UserInfo to) {
        this.to = to;
    }
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
    public MessageTable getMessageTable(String uid){
        MessageTable messageTable = new MessageTable();
        messageTable.setId(id);
        messageTable.setTag(tag);
        messageTable.setBid(bid);
        messageTable.setContent(content);
        messageTable.setTypechat(typechat.getValue());
        messageTable.setTypefile(typefile.getValue());
        messageTable.setTime(time);
        messageTable.setFromid(from.getId());
        messageTable.setUid(uid);
        messageTable.setFrom(GsonUtil.objectToJson(from));
        messageTable.setTo(GsonUtil.objectToJson(to));
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
    @Override
    public String toString() {
        return "IMMessage{" +
                "from=" + from +
                ", bid='" + bid + '\'' +
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
