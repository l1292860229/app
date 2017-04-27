package com.example.administrator.imservice;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.util.GetDataUtil;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

import static com.example.administrator.activity.BaseActivity.CLOSE_ACTIVITY;

/**
 * Created by dell on 2017/4/27.
 */

public class IMConnectionListener implements ConnectionListener {
    private static final String LOGTAG = "IMConnectionListener";
    XMPPConnection connection;
    private Context context;
    public IMConnectionListener(Context context,XMPPConnection connection){
        this.context = context;
        this.connection = connection;
    }
    @Override
    public void connectionClosed() {
        Log.e(LOGTAG, "connectionClosed()...");
    }
    @Override
    public void connectionClosedOnError(Exception e) {
        Log.e(LOGTAG, "connectionClosedOnError...");
        //是不是在其它地方登录过
        if(e.getMessage().contains("stream:error (conflict)")){
            //清楚通知栏所有的通知
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            GetDataUtil.removeIsRemember(context);
            context.sendBroadcast(new Intent(CLOSE_ACTIVITY));
            connection.disconnect();
            Intent serviceIntent = new Intent(context, IMService.class);
            context.stopService(serviceIntent);
        }else{
            connection.disconnect();
        }
    }
    @Override
    public void reconnectingIn(int i) {
        Log.e(LOGTAG, "reconnectingIn...");
    }

    @Override
    public void reconnectionSuccessful() {
        Log.e(LOGTAG, "reconnectionSuccessful...");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        Log.e(LOGTAG, "reconnectionFailed...");
    }
}
