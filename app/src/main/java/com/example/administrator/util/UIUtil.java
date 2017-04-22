package com.example.administrator.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.example.administrator.R;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.yydcdut.sdlv.MenuItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

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
    public static void openImagePicker(Activity context,boolean isshowCamera){

        ImageConfig.Builder ib= new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(context.getResources().getColor(R.color.black))
                .titleBgColor(context.getResources().getColor(R.color.black))
                .titleSubmitTextColor(context.getResources().getColor(R.color.white))
                .titleTextColor(context.getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/temp/picture")
                //单选自定义裁剪
                .crop();
        if(isshowCamera){
            // 开启拍照功能 （默认关闭）
            ib.showCamera();
        }
        ImageConfig imageConfig= ib.build();
        ImageSelector.open(context, imageConfig);   // 开启图片选择器
    }

    /**
     * 多张相片选择器
     * @param context
     */
    public static void openImagePickers(Activity context,int max,boolean isshowCamera){
        ImageConfig.Builder ib= new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(context.getResources().getColor(R.color.black))
                .titleBgColor(context.getResources().getColor(R.color.black))
                .titleSubmitTextColor(context.getResources().getColor(R.color.white))
                .titleTextColor(context.getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .mutiSelect()
                .mutiSelectMaxSize(max)
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/temp/picture")
                //单选自定义裁剪
                .crop();
        if(isshowCamera){
            // 开启拍照功能 （默认关闭）
            ib.showCamera();
        }
        ImageConfig imageConfig= ib.build();
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

    /**
     * 获取百度地图位置的图片地址
     * @param lng
     * @param lat
     * @return
     */
    public static String getMapUrl(Double lng,Double lat){
        return "http://api.map.baidu.com/staticimage?center="+lng+","+lat
                + "&width=200&height=120&zoom=16&markers="+lng+","+lat+"&markerStyles=s";
    }

    /**
     * 获取本地视频的图片
     * @param filePath
     * @return
     */
    public static Bitmap createVideoThumbnail(String filePath) {
        // MediaMetadataRetriever is available on API Level 8
        // but is hidden until API Level 10
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);
            // The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                return  (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    return BitmapFactory.decodeByteArray(data, 0, data.length);
                }else{
                    return (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
                }
            }
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } catch (InstantiationException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (InvocationTargetException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (ClassNotFoundException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (NoSuchMethodException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (IllegalAccessException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        }finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * 获取本地视频图片的地址
     * @param filePath
     * @return
     */
    public static String createVideoThumbnailImagePath(String filePath) {
        // MediaMetadataRetriever is available on API Level 8
        // but is hidden until API Level 10
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);
            Bitmap bitmap=null;
            // The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                bitmap =  (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                }else{
                    bitmap =  (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
                }
            }
            if(bitmap!=null){
                String path = Environment.getExternalStorageDirectory()+ File.separator + System.currentTimeMillis() + ".jpg";
                FileOutputStream fos = new FileOutputStream(path);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                fos.close();
                return path;
            }
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } catch (InstantiationException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (InvocationTargetException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (ClassNotFoundException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (NoSuchMethodException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (IllegalAccessException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * 获取视频的时长
     * @param mUri
     * @return
     */
    public static String getRingDuring(String mUri){
        String duration="0";
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();

        try {
            if (mUri != null) {
                HashMap<String, String> headers=null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            }
            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mmr.release();
        }
        return duration;
    }
    /**
     * 设置listView滑动时的菜单
     * @param title
     * @return
     */
    public static  MenuItem getMenu(String title, int bgcolor,int width,int textColor,int textSize,int direction){
        return new MenuItem.Builder().setWidth(width)
                .setText(title)
                .setBackground(new ColorDrawable(bgcolor))
                .setTextColor(textColor)
                .setTextSize(textSize)
                .setDirection(direction)
                .build();
    }
}
