package com.example.administrator.dbDao;

import com.example.administrator.entity.Session;
import com.example.administrator.entity.db.MessageTable;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dell on 2017/4/22.
 */

public class MessageTableDao {
    Realm realm=Realm.getDefaultInstance();
    public List<MessageTable> select(String uid, String toid){
        RealmResults<MessageTable> messageTables = realm.where(MessageTable.class)
                .equalTo("uid",uid).equalTo("toid",toid).findAll();
        return  realm.copyFromRealm(messageTables);
    }
    public void insert(MessageTable messageTable){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(messageTable);
        Session session  = new Session();
        session.setId(messageTable.getUid()+"_"+messageTable.getToid());
        session.setUid(messageTable.getUid());
        session.setToid(messageTable.getToid());
        session.setTypefile(messageTable.getTypefile());
        session.setTypechat(messageTable.getTypechat());
        session.setIsTop(0);
        session.setName(messageTable.getIMMessage().getTo().getName());
        session.setHeading(messageTable.getIMMessage().getTo().getUrl());
        session.setCreatetime(messageTable.getTime());
        session.setContent(messageTable.getContent());
        session.setUnreadcount(0);
        realm.copyToRealmOrUpdate(session);
        realm.commitTransaction();
    }
}
