package com.example.administrator.entity;

import com.example.administrator.enumset.MessageType;
import com.example.administrator.util.GsonUtil;

import java.io.Serializable;

/**
 * Created by dell on 2017/4/17.
 */

public class FavoriteItem implements Serializable {
    private String id;
    private String uid;
    private String fid;
    private String otherid;
    private String content;
    private long createtime;
    private String headsmall;
    private String nickname;

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

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getOtherid() {
        return otherid;
    }

    public void setOtherid(String otherid) {
        this.otherid = otherid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
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

    @Override
    public String toString() {
        return "FavoriteItem{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", fid='" + fid + '\'' +
                ", otherid='" + otherid + '\'' +
                ", content='" + content + '\'' +
                ", createtime=" + createtime +
                ", headsmall='" + headsmall + '\'' +
                ", nickname='" + nickname + '\'' +
                ", getFavoriteItemContent='" + getFavoriteItemContent() + '\'' +
                '}';
    }
    public FavoriteItemContent getFavoriteItemContent(){
        return  GsonUtil.parseJsonWithGson(content,FavoriteItemContent.class);
    }
    public class  FavoriteItemContent{
        private String content;
        private MessageType typefile;
        private String urllarge;
        private String urlsmall;
        private String address;
        private double lat;
        private double lng;
        private String image;
        private String time;
        private String url;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public MessageType getTypefile() {
            return typefile;
        }

        public void setTypefile(MessageType typefile) {
            this.typefile = typefile;
        }

        public String getUrllarge() {
            return urllarge;
        }

        public void setUrllarge(String urllarge) {
            this.urllarge = urllarge;
        }

        public String getUrlsmall() {
            return urlsmall;
        }

        public void setUrlsmall(String urlsmall) {
            this.urlsmall = urlsmall;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "FavoriteItemContent{" +
                    "content='" + content + '\'' +
                    ", typefile=" + typefile +
                    ", urllarge='" + urllarge + '\'' +
                    ", urlsmall='" + urlsmall + '\'' +
                    ", address='" + address + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", image='" + image + '\'' +
                    ", time='" + time + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
