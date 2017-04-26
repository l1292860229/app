package com.example.administrator.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.smartandroid.sa.zUImageLoader.cache.disc.naming.Md5FileNameGenerator;
import com.smartandroid.sa.zUImageLoader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.smartandroid.sa.zUImageLoader.core.DisplayImageOptions;
import com.smartandroid.sa.zUImageLoader.core.ImageLoader;
import com.smartandroid.sa.zUImageLoader.core.ImageLoaderConfiguration;
import com.smartandroid.sa.zUImageLoader.core.assist.QueueProcessingType;
import com.smartandroid.sa.zUImageLoader.core.display.RoundedBitmapDisplayer;
import com.smartandroid.sa.zUImageLoader.core.download.BaseImageDownloader;

/**
 * Created by Administrator on 2017/1/22.
 */

public class ImageUitl {
    public static void init(Context context){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }
    @BindingAdapter({"bind:image"})
    public static void imageLoader(ImageView imageView, String url) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .build();
        ImageLoader.getInstance().displayImage(url, imageView,options);
    }
    public static void setImage(ImageView imageView, String url){
            ImageLoader.getInstance().displayImage(url, imageView);
    }
}
