package com.example.administrator.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.entity.Constants;
import com.example.administrator.entity.Province;
import com.example.administrator.entity.UserInfo;

import java.io.IOException;
import java.io.InputStream;

import static com.example.administrator.util.GsonUtil.gson;

/**
 * Created by Administrator on 2017/1/22.
 */

public class GetDataUtil {
    public static String getUserName(Context context){
        SharedPreferences mPreferences = context.getSharedPreferences(Constants.REMEMBER_USERNAME_PASSWORD, 0);
        String username = mPreferences.getString(Constants.USERNAME,"");
        return username;
    }
    public static UserInfo getUserInfo(Context context){
        SharedPreferences mPreferences = context.getSharedPreferences(Constants.USERINFO, 0);
        return GsonUtil.parseJsonWithGson(mPreferences.getString(Constants.USERINFO,""), UserInfo.class);
    }
    public static void setUserInfo(Context context,String userInfo){
        SharedPreferences userinfoPreferences = context.getSharedPreferences(Constants.USERINFO, 0);
        SharedPreferences.Editor userinfoEditor = userinfoPreferences.edit();
        userinfoEditor.putString(Constants.USERINFO, userInfo);
        userinfoEditor.commit();
    }
    public static void setUserInfo(Context context,UserInfo userInfo){
        setUserInfo(context,GsonUtil.objectToJson(userInfo));
    }
    public static void removeIsRemember(Context context){
        SharedPreferences mPreferences = context.getSharedPreferences(Constants.USERINFO, 0);
        mPreferences.edit().remove(Constants.USERINFO).commit();
    }
    public static Province[] getAreaCode(Context context){
        Province[] provinces =  gson.fromJson(getAssestsFile(context,"AreaCode"), Province[].class);
        return provinces;
    }
    public static String getAssestsFile(Context context,String string) {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getResources().getAssets().open(string);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "UTF-8");
            return text;
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }
    public static void copy(Context context,String string) {
        ClipboardManager c = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        c.setPrimaryClip(ClipData.newPlainText("copytext", string));
    }
}
