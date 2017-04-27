package com.example.administrator.imservice;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.entity.constant.Constants;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.StringUtil;

import java.util.List;

/**
 * Created by dell on 2017/4/27.
 */

public class IMServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("IMServiceReceiver","onReceive -- 拉起即时通讯服务");
        String userInfo = (String) GetDataUtil.get(context, Constants.USERINFO, Constants.USERINFO,"");
        if(!StringUtil.isNull(userInfo)){
            if(!isServiceWork(context, "com.example.administrator.imservice.IMService")){
                Intent it=new Intent(context, IMService.class);
                context.startService(it);
            }
        }
    }
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
