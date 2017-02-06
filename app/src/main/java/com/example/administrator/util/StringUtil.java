package com.example.administrator.util;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/1/21.
 */

public class StringUtil {
    public static boolean isNull(String str){
        if(str==null || str.equals("") || str.toLowerCase().equals("null")){
            return true;
        }
        return false;
    }

    /**
     * 判断是否是正确的手机号码
     * @param mobiles
     * @return
     */
    public static boolean isMobileNum(String mobiles){
        Pattern pattern = Pattern.compile("^(1(3|5|8|7)[0-9]{9})$");
        Matcher m = pattern.matcher(mobiles);
        // Log.e("手机格式验证",""+ m.matches());
        if(m.matches() && mobiles.length()==11){
            return true;
        }
        return false;
    }
    public static int dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
