package com.example.administrator.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.R;
import com.example.administrator.activity.ImagePagerActivity;
import com.example.administrator.databinding.PictureItemBinding;
import com.example.administrator.entity.Picture;
import com.example.administrator.util.ImageUitl;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2017/2/4.
 */

public class ImageAdapter extends BaseAdapter {
    final public static int URL_TYPE=1;
    final public static int LOCAL_TYPE=2;
    final public static int DRAWABLE_TYPE=3;
    private  Picture[] pictures;
    private Context context;
    public ImageAdapter(Context context, List<Picture> pictures){
        this.pictures= new Picture[pictures.size()];
        pictures.toArray(this.pictures);
        this.context = context;
    }
    public ImageAdapter(Context context, Picture[] pictures){
        this.pictures = pictures;
        this.context = context;
    }
    public void setData(Picture[] pictures){
        this.pictures = pictures;
    }
    public void setData(List<Picture> pictures){
        this.pictures= new Picture[pictures.size()];
        pictures.toArray(this.pictures);
    }
    @Override
    public int getCount() {
        return pictures.length;
    }
    @Override
    public Object getItem(int position) {
        return pictures[position];
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PictureItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.picture_item, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        switch (pictures[pictures.length-1-position].getType()){
            case URL_TYPE://如果是网络图片
                showUrlImage(binding,position);
                break;
            case LOCAL_TYPE://如果是本地图片
                showLocalImage(binding,pictures[pictures.length-1-position].getSmallUrl());
                break;
            case DRAWABLE_TYPE://如果是资源图片
                showDrawableImage(binding,pictures[pictures.length-1-position].getSmallUrl(),
                        pictures[pictures.length-1-position].getOnClickListener());
                break;
        }
        return binding.getRoot();
    }

    /**
     * 显示网络上的图片
     * @param binding
     * @param position
     */
    public void  showUrlImage(PictureItemBinding binding,final int position){
        ImageUitl.setImage(binding.pic,pictures[pictures.length-1-position].getSmallUrl());
        binding.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ImagePagerActivity.class);
                intent.putExtra(ImagePagerActivity.IMAGES,pictures);
                intent.putExtra(ImagePagerActivity.IMAGE_POSITION,position);
                context.startActivity(intent);
            }
        });
        binding.pic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setItems(new String[]{"收藏"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e("setOnLongClickListener","i="+i);
                    }
                }).show();
                return false;
            }
        });
    }

    /**
     * 显示本地上的图片
     */
    public void showLocalImage(PictureItemBinding binding,String s){
        binding.pic.setImageBitmap(BitmapFactory.decodeFile(s));
        binding.pic.setOnClickListener(null);
    }

    /**
     * 显示drawable的图片，并做相应的点击事件
     * @param binding
     * @param s
     * @param clickListener
     */
    public void showDrawableImage(PictureItemBinding binding, String s, View.OnClickListener clickListener){
        Field field = null;
        try {
            field =R.drawable.class.getDeclaredField(s);
            int resId = field.getInt(null);
            binding.pic.setImageResource(resId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        binding.pic.setOnClickListener(clickListener);
    }
}
