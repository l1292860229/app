package com.example.administrator.UIView;

import android.content.Context;
import android.widget.ImageView;

import com.example.administrator.util.ImageUitl;

/**
 * Created by Administrator on 2017/3/31.
 */

public class LQRNineGridImageViewAdapter extends com.lqr.ninegridimageview.LQRNineGridImageViewAdapter<String> {

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, String str) {
        ImageUitl.setImage(imageView,str);
    }
    //重写该方法自定义生成ImageView方式，用于九宫格头像中的一个个图片控件，可以设置ScaleType等属性
    @Override
    protected ImageView generateImageView(Context context) {
        return super.generateImageView(context);
    }
}
