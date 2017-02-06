package com.example.administrator.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/1/21.
 */

public class NetworkUtil {
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
}
