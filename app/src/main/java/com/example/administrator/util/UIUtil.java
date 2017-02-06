package com.example.administrator.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.example.administrator.R;

/**
 * Created by Administrator on 2017/1/21.
 */

public class UIUtil {
    private static Handler handler = new Handler();

    /**
     * 判断字段是是否为空
     * @param context
     * @param str
     * @param message
     * @return
     */
    public static boolean isNullMessage(final Context context,String str,final String message){
        if(StringUtil.isNull(str)){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }
        return false;
    }

    /**
     * Toast 弹出框
     * @param context
     * @param message
     */
    public static void showMessage(final Context context,final String message){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示加载的视图
     * @param context
     * @param msg
     */
    public static void showLoading(Context context,String msg) {
        AbDialogUtil.showLoadDialog(context, R.mipmap.ic_load, msg);
    }

    /**
     * 隐藏加载的视图
     * @param context
     */
    public static void hideLoading(Context context) {
        AbDialogUtil.removeDialog(context);
    }
}
