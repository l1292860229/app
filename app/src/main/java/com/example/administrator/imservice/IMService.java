package com.example.administrator.imservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.entity.UserInfo;
import com.example.administrator.util.GetDataUtil;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dell on 2017/4/27.
 */

public class IMService extends Service {
    static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    public static final String LOGTAG = "IMService";
    private static final String HOST ="139.224.57.105";
    private static final int PORT = 5222;
    public static final String XMPP_RESOURCE_NAME =HOST;
    XMPPConnection connection;
    private String username;
    private String password;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ConnectionConfiguration connConfig = new ConnectionConfiguration(
                HOST, PORT);
        // connConfig.setSecurityMode(SecurityMode.disabled);
        connConfig.setReconnectionAllowed(true);
        connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled); // 设置安全模式
        connConfig.setSASLAuthenticationEnabled(false); // 不启用sasl 认证启用
        connConfig.setCompressionEnabled(false); // 压缩不启用
        connection = new XMPPConnection(connConfig);
        UserInfo userInfo = GetDataUtil.getUserInfo(getApplicationContext());
        password = userInfo.getPassword();
        username = userInfo.getPhone();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        connectionService();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(LOGTAG,"onDestroy");
        if(connection!=null && connection.isConnected()){
            connection.disconnect();
        }
        super.onDestroy();
        System.gc();
        System.exit(0);
    }

    /**
     * 链接openfire服务器
     */
    private void connectionService(){
        if(!(connection==null || connection.isConnected())){
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.connect();
                        Log.d(LOGTAG,"The connection successfully -- 连接成功");
                        LoginService();
                    } catch (XMPPException e) {
                        e.printStackTrace();
                        Log.e(LOGTAG,"The connection error -- 连接失败");
                        try {
                            Thread.sleep(1000);
                            connectionService();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }else{
            Log.d(LOGTAG,"The connection is already open -- 连接已经打开");
        }
    }
    private void LoginService(){
        if(connection!=null && connection.isConnected()){
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.login(username,password,XMPP_RESOURCE_NAME);
                        Log.d(LOGTAG,"The login successfully  -- 登录成功");
                        setListener();
                    } catch (XMPPException e) {
                        e.printStackTrace();
                        Log.e(LOGTAG,"The login error -- 登录失败");
                        try {
                            Thread.sleep(1000);
                            LoginService();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }else{
            Log.e(LOGTAG,"The connection not open -- 连接未打开");
        }
    }
    private void setListener(){
        Log.e(LOGTAG,"设置监听");
        connection.addConnectionListener(new IMConnectionListener(getApplication(),connection));
        connection.getChatManager().addChatListener(new IMChatManagerListener());
    }
}
