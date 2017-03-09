package com.example.administrator.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.example.administrator.R;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;

import java.lang.reflect.Field;

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

    /**
     * 单张相片选择器
     * @param context
     */
    public static void openImagePicker(Activity context){
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(context.getResources().getColor(R.color.black))
                .titleBgColor(context.getResources().getColor(R.color.black))
                .titleSubmitTextColor(context.getResources().getColor(R.color.white))
                .titleTextColor(context.getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/temp/picture")
                //单选自定义裁剪
                .crop()
                .build();
        ImageSelector.open(context, imageConfig);   // 开启图片选择器
    }

    /**
     * 多张相片选择器
     * @param context
     */
    public static void openImagePickers(Activity context,int max){
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(context.getResources().getColor(R.color.black))
                .titleBgColor(context.getResources().getColor(R.color.black))
                .titleSubmitTextColor(context.getResources().getColor(R.color.white))
                .titleTextColor(context.getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .mutiSelect()
                .mutiSelectMaxSize(max)
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/temp/picture")
                //单选自定义裁剪
                .crop()
                .build();
        ImageSelector.open(context, imageConfig);   // 开启图片选择器
    }
    /**
     * 显示drawable的图片，并做相应的点击事件
     * @param s
     * @param clickListener
     */
    public static void showDrawableImage(ImageView imageView, String s,View.OnClickListener clickListener){
        Field field = null;
        try {
            field =R.drawable.class.getDeclaredField(s);
            int resId = field.getInt(null);
            imageView.setImageResource(resId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        imageView.setOnClickListener(clickListener);
    }
    /**
     * 显示Mipmap的图片，并做相应的点击事件
     * @param s
     * @param clickListener
     */
    public static void showMipmapImage(ImageView imageView, String s,View.OnClickListener clickListener){
        Field field = null;
        try {
            field =R.mipmap.class.getDeclaredField(s);
            int resId = field.getInt(null);
            imageView.setImageResource(resId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        imageView.setOnClickListener(clickListener);
    }

    /**
     * 显示本地上的图片
     */
    public static void showLocalImage(ImageView imageView, String s,View.OnClickListener clickListener){
        imageView.setImageBitmap(BitmapFactory.decodeFile(s));
        imageView.setOnClickListener(clickListener);
    }
    /**
     * 显示网络上的图片
     */
    public static void  showUrlImage(ImageView imageView, String s, View.OnClickListener clickListener, View.OnLongClickListener longClickListener){
        ImageUitl.setImage(imageView,s);
        imageView.setOnLongClickListener(longClickListener);
        imageView.setOnClickListener(clickListener);
    }
}
