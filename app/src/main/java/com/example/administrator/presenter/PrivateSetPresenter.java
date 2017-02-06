package com.example.administrator.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.administrator.entity.Constants;
import com.example.administrator.interfaceview.IUPrivateSetView;

/**
 * Created by Administrator on 2017/1/24.
 */

public class PrivateSetPresenter {
    private Context context;
    private Intent intent;
    private IUPrivateSetView privateSet;
    public PrivateSetPresenter(){}
    public PrivateSetPresenter(Context context,Intent intent,IUPrivateSetView privateSet){
        this.context = context;
        this.intent = intent;
        this.privateSet = privateSet;
    }
    public void init(){
        SharedPreferences preferences;
        String systemSetting =  intent.getStringExtra(Constants.SYSTEM_SETTING);
        //判断那边传过来是要设置什么类型隐私或通知
        if (Constants.SYSTEM_SETTING_PRIVACY.equals(systemSetting)) {
            //隐私
            preferences = context.getSharedPreferences(Constants.SYSTEM_SETTING_PRIVACY, 0);
            boolean addFriendVerify = preferences.getBoolean(Constants.PRIVACY_ADDFRIENDVERIFY,true);
            boolean recommendContactsFriend = preferences.getBoolean(Constants.PRIVACY_RECOMMENDCONTACTSFRIEND,true);
            privateSet.showPrivacy(addFriendVerify,recommendContactsFriend);
        }else if(Constants.SYSTEM_SETTING_NOTICE.equals(systemSetting)){
            //通知
            preferences = context.getSharedPreferences(Constants.SYSTEM_SETTING_NOTICE, 0);
            boolean systemSettingNotice = preferences.getBoolean(Constants.SYSTEM_SETTING_NOTICE,true);
            boolean noticeSound = preferences.getBoolean(Constants.NOTICE_SOUND,true);
            boolean noticeShock = preferences.getBoolean(Constants.NOTICE_SHOCK,true);
            privateSet.showNotice(systemSettingNotice,noticeSound,noticeShock);
        }
    }
    public void SetPrivacyAddfriendverify(boolean ischecked){
        SharedPreferences preferences = context.getSharedPreferences(Constants.SYSTEM_SETTING_PRIVACY, 0);
        preferences.edit().putBoolean(Constants.PRIVACY_ADDFRIENDVERIFY,ischecked).commit();
    }
    public void SetPrivacyRecommendcontactsfriend(boolean ischecked){
        SharedPreferences preferences = context.getSharedPreferences(Constants.SYSTEM_SETTING_PRIVACY, 0);
        preferences.edit().putBoolean(Constants.PRIVACY_RECOMMENDCONTACTSFRIEND,ischecked).commit();
    }
    public void SetSystemSettingNotice(boolean ischecked){
        SharedPreferences preferences = context.getSharedPreferences(Constants.SYSTEM_SETTING_NOTICE, 0);
        preferences.edit().putBoolean(Constants.SYSTEM_SETTING_NOTICE,ischecked).commit();
    }
    public void SetNoticeSound(boolean ischecked){
        SharedPreferences preferences = context.getSharedPreferences(Constants.SYSTEM_SETTING_NOTICE, 0);
        preferences.edit().putBoolean(Constants.NOTICE_SOUND,ischecked).commit();
    }
    public void SetNoticeShock(boolean ischecked){
        SharedPreferences preferences = context.getSharedPreferences(Constants.SYSTEM_SETTING_NOTICE, 0);
        preferences.edit().putBoolean(Constants.NOTICE_SHOCK,ischecked).commit();
    }
}
