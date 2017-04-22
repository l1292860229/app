package com.example.administrator.dbDao;

import com.example.administrator.entity.Session;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by dell on 2017/4/20.
 */

public class SessionDao {
    Realm realm=Realm.getDefaultInstance();
    public void insertOrUpdate(Session session){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(session);
        realm.commitTransaction();
    }
    public void clearReadCount(String uid){
        RealmResults<Session>  realmResults =  realm.where(Session.class).equalTo("uid", uid).findAll();
        realm.beginTransaction();
        for (Session realmResult : realmResults) {
            realmResult.setUnreadcount(0);
        }
        realm.commitTransaction();
    }
    public void delete(String id){
        final RealmResults<Session> sessions = realm.where(Session.class)
                .equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //删除所有数据
                sessions.deleteAllFromRealm();
            }
        });
    }
    public int  getUnReadCount(String uid){
        Number sum=  realm.where(Session.class).equalTo("uid",uid).findAll().sum("unreadcount");
        return sum.intValue();
    }
    public List<Session> select(String uid){
        RealmResults<Session> sessionRealmResults = realm.where(Session.class)
                .equalTo("uid",uid).findAll();
        sessionRealmResults = sessionRealmResults.sort("isTop", Sort.DESCENDING,"createtime",Sort.DESCENDING);
        return  realm.copyFromRealm(sessionRealmResults);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if(!realm.isClosed()){
            realm.close();
        }
        return super.clone();
    }
}
