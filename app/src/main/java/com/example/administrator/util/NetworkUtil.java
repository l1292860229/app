package com.example.administrator.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tandong.sa.loopj.AsyncHttpClient;
import com.tandong.sa.loopj.RequestParams;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/1/21.
 */

public class NetworkUtil {
    private static MessageDigest md;
    private static StringBuilder sb;
    private static AsyncHttpClient client;
    public static boolean verifyNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo n:networkInfo){
            if (n != null&& n.isConnected()) {
                return true;
            }
        }
        return false;
    }
    public static void safeDate(RequestParams requestParams) {
        String key ="1Zxm^*s7ZowzjR3@PRA^/www.winchat.com.cn-2012-2016wei20.com";
        long time = System.currentTimeMillis();
        try {
            initMD5();
            md.reset();
            md.update(("android|"+key+"|"+time).getBytes());
            byte encrypt[] = md.digest();
            for (byte t : encrypt) {
                String s = Integer.toHexString(t & 0xFF);
                if (s.length() == 1) {
                    s = "0" + s;
                }
                sb.append(s);
            }
            requestParams.put("esign",sb.toString());
            requestParams.put("timestamp",time);
            requestParams.put("devicetype","android");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private static void initMD5() throws NoSuchAlgorithmException {
        if(md==null){
            md = MessageDigest.getInstance("MD5");
        }
        if(sb==null){
            sb = new StringBuilder();
        }else{
            sb.delete(0,sb.length());
        }
    }
    public static AsyncHttpClient instanceAsyncHttpClient(){
        if (client==null) {
            client =  new AsyncHttpClient();
        }
        return client;
    }
}
