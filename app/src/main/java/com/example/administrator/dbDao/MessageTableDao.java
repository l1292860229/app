package com.example.administrator.dbDao;

import com.example.administrator.entity.db.MessageTable;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dell on 2017/4/22.
 */

public class MessageTableDao {
    Realm realm=Realm.getDefaultInstance();
    public List<MessageTable> select(String uid, String fromid){
        RealmResults<MessageTable> messageTables = realm.where(MessageTable.class)
                .equalTo("uid",uid).equalTo("fromid",fromid).findAll();
        return  realm.copyFromRealm(messageTables);
    }
    public void insert(MessageTable messageTable){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(messageTable);
        realm.commitTransaction();
    }
}
