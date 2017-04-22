package com.example.administrator;

import android.app.Application;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.administrator.util.ImageUitl;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        BMapManager.init();
        //图片加载类的初始化
        ImageUitl.init(getApplicationContext());
        //初始化数据库
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(2)//数据库版本号
                //.migration(new MyMigration())//更新操作
                .deleteRealmIfMigrationNeeded() //如果您处于开发中，并且经常更改模型，允许丢失所有数据
                .build();
        Realm.setDefaultConfiguration(config);
    }
}