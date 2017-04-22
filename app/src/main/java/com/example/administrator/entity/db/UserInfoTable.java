package com.example.administrator.entity.db;

import com.example.administrator.entity.UserInfo;
import com.example.administrator.util.GsonUtil;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dell on 2017/4/20.
 */

public class UserInfoTable extends RealmObject {
    @PrimaryKey
    private String id;
    private String uid;
    private int group;
    private String data;
    @Ignore
    private UserInfo userInfo;

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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public UserInfo getUserInfo() {
        if(userInfo==null){
            userInfo = GsonUtil.parseJsonWithGson(data, UserInfo.class);
        }
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        this.data = GsonUtil.objectToJson(userInfo);
    }

    @Override
    public String toString() {
        return "UserInfoTable{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", group=" + group +
                ", data='" + data + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
