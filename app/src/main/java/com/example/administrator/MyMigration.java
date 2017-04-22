package com.example.administrator;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by dell on 2017/4/20.
 * 当数据模型要变更时,这里进行变更操作
 */

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 1) {
//            final RealmResults<DynamicRealmObject> realmObjects = realm.where("UserInfoTable").findAll();
//            ArrayList<UserInfoTable> userInfoTables = new ArrayList<>();
//            for (DynamicRealmObject dynamicRealmObject : realmObjects) {
//                UserInfoTable userInfoTable = new UserInfoTable();
//                userInfoTable.setGroup(dynamicRealmObject.getInt("group"));
//                userInfoTable.setUid(dynamicRealmObject.getString("uid"));
//                userInfoTable.setData(dynamicRealmObject.getString("data"));
//                userInfoTable.setId(userInfoTable.getUid()+"_"+userInfoTable.getGroup()+"_"+userInfoTable.getUserInfo().getPhone());
//                boolean isadd = true;
//                for (UserInfoTable infoTable : userInfoTables) {
//                    String id = infoTable.getUid()+"_"+infoTable.getGroup()+"_"+infoTable.getUserInfo().getPhone();
//                    if(id.equals(userInfoTable.getId())){
//                        isadd = false;
//                        break;
//                    }
//
//                }
//                if(isadd){
//                    userInfoTables.add(userInfoTable);
//                }
//            }
//            realmObjects.deleteAllFromRealm();
//            schema.get("UserInfoTable")
//                    .addField("id", String.class, FieldAttribute.PRIMARY_KEY);
//            for (UserInfoTable userInfoTable : userInfoTables) {
//                DynamicRealmObject object = realm.createObject("UserInfoTable",userInfoTable.getId()); // Create a new object
//                object.setInt("group",userInfoTable.getGroup());
//                object.setString("uid",userInfoTable.getUid());
//                object.setString("data",userInfoTable.getData());
//            }
//            oldVersion++;
        }
    }
}
