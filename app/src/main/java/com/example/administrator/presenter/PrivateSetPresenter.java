package com.example.administrator.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.entity.Constants;
import com.example.administrator.interfaceview.IUPrivateSetView;
import com.example.administrator.util.GetDataUtil;

/**
 * Created by Administrator on 2017/1/24.
 */

public class PrivateSetPresenter {
    private Context context;
    private Intent intent;
    private IUPrivateSetView privateSet;
    private String sspdb = Constants.SYSTEM_SETTING_PRIVACY;
    private String ssndb = Constants.SYSTEM_SETTING_NOTICE;
    public PrivateSetPresenter(){}
    public PrivateSetPresenter(Context context,Intent intent,IUPrivateSetView privateSet){
        this.context = context;
        this.intent = intent;
        this.privateSet = privateSet;
    }
    public void init(){
        String systemSetting =  intent.getStringExtra(Constants.SYSTEM_SETTING);
        //判断那边传过来是要设置什么类型隐私或通知
        if (Constants.SYSTEM_SETTING_PRIVACY.equals(systemSetting)) {
            //隐私
            boolean addFriendVerify = (boolean) GetDataUtil.get(context,sspdb,Constants.PRIVACY_ADDFRIENDVERIFY,true);
            boolean recommendContactsFriend = (boolean) GetDataUtil.get(context,sspdb,Constants.PRIVACY_RECOMMENDCONTACTSFRIEND,true);
            privateSet.showPrivacy(addFriendVerify,recommendContactsFriend);
        }else if(Constants.SYSTEM_SETTING_NOTICE.equals(systemSetting)){
            //通知
            boolean systemSettingNotice = (boolean) GetDataUtil.get(context,ssndb,Constants.SYSTEM_SETTING_NOTICE,true);
            boolean noticeSound = (boolean) GetDataUtil.get(context,ssndb,Constants.NOTICE_SOUND,true);
            boolean noticeShock = (boolean) GetDataUtil.get(context,ssndb,Constants.NOTICE_SHOCK,true);
            privateSet.showNotice(systemSettingNotice,noticeSound,noticeShock);
        }
    }
    public void SetPrivacyAddfriendverify(boolean ischecked){
        GetDataUtil.put(context,sspdb,Constants.PRIVACY_ADDFRIENDVERIFY,ischecked);
    }
    public void SetPrivacyRecommendcontactsfriend(boolean ischecked){
        GetDataUtil.put(context,sspdb,Constants.PRIVACY_RECOMMENDCONTACTSFRIEND,ischecked);
    }
    public void SetSystemSettingNotice(boolean ischecked){
        GetDataUtil.put(context,ssndb,Constants.SYSTEM_SETTING_NOTICE,ischecked);
    }
    public void SetNoticeSound(boolean ischecked){
        GetDataUtil.put(context,ssndb,Constants.NOTICE_SOUND,ischecked);
    }
    public void SetNoticeShock(boolean ischecked){
        GetDataUtil.put(context,ssndb,Constants.NOTICE_SHOCK,ischecked);
    }
}
