package com.example.administrator.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.entity.constant.Constants;
import com.example.administrator.entity.Province;
import com.example.administrator.entity.UserInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.example.administrator.util.GsonUtil.gson;

/**
 * Created by Administrator on 2017/1/22.
 */

public class GetDataUtil {
    public static String getUserName(Context context) {
        String username = (String) get(context,Constants.REMEMBER_USERNAME_PASSWORD,Constants.USERNAME,"");
        return username;
    }

    public static UserInfo getUserInfo(Context context) {
        return GsonUtil.parseJsonWithGson((String) get(context,Constants.USERINFO,Constants.USERINFO,""), UserInfo.class);
    }

    public static void setUserInfo(Context context, String userInfo) {
        put(context,Constants.USERINFO,Constants.USERINFO,userInfo);
        //SharedPreferences userinfoPreferences = context.getSharedPreferences(Constants.USERINFO, 0);
    }

    public static void setUserInfo(Context context, UserInfo userInfo) {
        setUserInfo(context, GsonUtil.objectToJson(userInfo));
    }

    public static void removeIsRemember(Context context) {
        remove(context,Constants.USERINFO,Constants.USERINFO);
    }

    /**
     * 获得地区
     *
     * @param context
     * @return
     */
    public static Province[] getAreaCode(Context context) {
        Province[] provinces = gson.fromJson(getAssestsFile(context, "AreaCode"), Province[].class);
        return provinces;
    }

    /**
     * 获得Assests目录下面的文件
     *
     * @param context
     * @param string
     * @return
     */
    public static String getAssestsFile(Context context, String string) {
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

    /**
     * 复制文本内容
     *
     * @param context
     * @param string
     */
    public static void copy(Context context, String string) {
        ClipboardManager c = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        c.setPrimaryClip(ClipData.newPlainText("copytext", string));
    }

    public static void put(Context context, String db, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(db,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String db, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(db,
                Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String db, String key) {
        SharedPreferences sp = context.getSharedPreferences(db,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context, String db) {
        SharedPreferences sp = context.getSharedPreferences(db,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String db, String key) {
        SharedPreferences sp = context.getSharedPreferences(db,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context, String db) {
        SharedPreferences sp = context.getSharedPreferences(db,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
}
