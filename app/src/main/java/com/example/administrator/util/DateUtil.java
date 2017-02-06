package com.example.administrator.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/24.
 */

public class DateUtil {
    private static final int ONE_MINUTE = 60; // Seconds
    private static final int ONE_HOUR = 60 * ONE_MINUTE;
    private static final int ONE_DAY = 24 * ONE_HOUR;
    public static String calculaterReleasedTime(Context context, Date date, long time, long lastMsgTime) {
        Date currentDate = new Date();
        long duration = (currentDate.getTime() - date.getTime()) / 1000; // Seconds
        if(lastMsgTime!=0){
            long duration1 = (time - lastMsgTime) / 1000;
            if(duration1 <=3*ONE_MINUTE){
                return "";
            }
        }
        try {
            if(isYeaterday(date, currentDate) ==0){
                SimpleDateFormat format =null;
                if(lastMsgTime == 0){
                    format =new SimpleDateFormat("MM月dd日"); //new SimpleDateFormat("HH:mm:ss");
                }else{
                    format =new SimpleDateFormat("HH:mm"); //new SimpleDateFormat("HH:mm:ss");
                }
                return format.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Not normal
        if (currentDate.before(date)) {
            if (Math.abs(duration) < ONE_MINUTE * 5) {//不足一分钟
                return "刚刚";
            } else {
                return getDateString(context, date,
                        currentDate.getYear() != date.getYear(),lastMsgTime!=0?true:false);
            }
        }
        if (duration >= ONE_DAY) {
            return getDateString(context, date,
                    currentDate.getYear() != date.getYear(),lastMsgTime!=0?true:false);
			/*return getTime(time);*/
        }else if (duration >= ONE_HOUR) {
			/*return duration / ONE_HOUR + context.getString(R.string.hour)
					+ context.getString(R.string.before);*/
            SimpleDateFormat	format =new SimpleDateFormat("HH:mm");
            return format.format(date);
            //return getTime(time,false);
        } else if (duration >= ONE_MINUTE) {
            return duration / ONE_MINUTE + "分钟"
                    + "前";
        } else {
            return duration + "秒"
                    + "前";
        }
    }
    public static int isYeaterday(Date oldTime,Date newTime) throws ParseException{
        if(newTime==null){
            newTime=new Date();
        }
        //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(newTime);
        Date today = format.parse(todayStr);
        //昨天 86400000=24*60*60*1000 一天
        if((today.getTime()-oldTime.getTime())>0 && (today.getTime()-oldTime.getTime())<=86400000) {
            return 0;
        }
        else if((today.getTime()-oldTime.getTime())<=0){ //至少是今天
            return -1;
        }
        else{ //至少是前天
            return 1;
        }
    }

    /**
     * 将日期按照指定格式转换
     * @param context
     * @param date
     * @param withYearString
     * @param isShowMM
     * @return
     */
    public static String getDateString(Context context, Date date,
                                       boolean withYearString,boolean isShowMM) {
        String timeString = "";
        SimpleDateFormat format = null;
        if (withYearString) {
            if(isShowMM){
                format = new SimpleDateFormat("yyyy.MM.dd HH:mm");

            }else{
                format = new SimpleDateFormat("yyyy.MM.dd");
            }

        }else{
            if(isShowMM){
                format = new SimpleDateFormat("MM月dd日 HH:mm");
            }else{
                format = new SimpleDateFormat("MM月dd日 ");
            }
        }
        return format.format(date);
    }
}
