package com.example.administrator.dbDao;

import com.example.administrator.entity.UserInfo;
import com.example.administrator.entity.db.UserInfoTable;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dell on 2017/4/20.
 */

public class UserInfoTableDao {
    Realm realm=Realm.getDefaultInstance();
    public void insert(String uid, int group, ArrayList<UserInfo> userInfos){
        realm.beginTransaction();
        for (UserInfo userInfo : userInfos) {
            UserInfoTable userInfoTable = new UserInfoTable(); // Create a new object
            userInfoTable.setId(uid+"_"+group+"_"+userInfo.getPhone());
            userInfoTable.setGroup(group);
            userInfoTable.setUid(uid);
            userInfoTable.setUserInfo(userInfo);
            realm.copyToRealmOrUpdate(userInfoTable);
        }
        realm.commitTransaction();
    }
    public void insert(String uid, int group, UserInfo userInfos) {
        realm.beginTransaction();
        UserInfoTable userInfoTable = new UserInfoTable(); // Create a new object
        userInfoTable.setId(uid+"_"+group+"_"+userInfos.getPhone());
        userInfoTable.setGroup(group);
        userInfoTable.setUid(uid);
        userInfoTable.setUserInfo(userInfos);
        realm.copyToRealmOrUpdate(userInfoTable);
        realm.commitTransaction();
    }
    public void delete(String uid, int group){
        final RealmResults<UserInfoTable> userInfoTable = realm.where(UserInfoTable.class)
                .equalTo("uid",uid).equalTo("group",group).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //删除所有数据
                userInfoTable.deleteAllFromRealm();
            }
        });
    }
    public void delete(String uid){
        final RealmResults<UserInfoTable> userInfoTable = realm.where(UserInfoTable.class)
                .equalTo("uid",uid).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //删除所有数据
                userInfoTable.deleteAllFromRealm();
            }
        });
    }
    public ArrayList<UserInfo> select(String uid, int group){
        RealmResults<UserInfoTable> userInfoTable = realm.where(UserInfoTable.class)
                .equalTo("uid",uid).equalTo("group",group).findAll();
        ArrayList<UserInfo> userInfos = new ArrayList<>();
        for (UserInfoTable table : userInfoTable) {
            userInfos.add(table.getUserInfo());
        }
        return userInfos;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if(!realm.isClosed()){
            realm.close();
        }
        return super.clone();
    }
}
