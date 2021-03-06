package com.example.administrator.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.example.administrator.R;
import com.example.administrator.activity.ImagePagerActivity;
import com.example.administrator.activity.SendFriensLoopActivity;
import com.example.administrator.databinding.PictureItemBinding;
import com.example.administrator.entity.Picture;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.administrator.entity.Picture.PictureType.DRAWABLE_TYPE;

/**
 * Created by Administrator on 2017/2/4.
 */

public class ImageAdapter extends BaseAdapter {
    private  Picture[] pictures;
    private Activity activity;
    private Context context;
    public ImageAdapter(Activity context, List<Picture> pictures){
        this.pictures= new Picture[pictures.size()];
        pictures.toArray(this.pictures);
        this.context = context;
        this.activity = context;
    }
    public ImageAdapter(Context context,  List<Picture> pictures){
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
                showLocalImage(binding,position);
                break;
            case DRAWABLE_TYPE://如果是资源图片
                showDrawableImage(binding,pictures[pictures.length-1-position].getSmallUrl(),
                        pictures[pictures.length-1-position].getOnClickListener());
                break;
            case URL_NOTCLICK_TYPE://如果是网络图片,但不可点击
                showUrlNotClickImage(binding,position);
                break;
        }
        return binding.getRoot();
    }
    /**
     * 显示网络上的图片
     * @param binding
     * @param position
     */
    public void  showUrlNotClickImage(PictureItemBinding binding,final int position){
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, StringUtil.dip2px(context,17));
        binding.pic.setLayoutParams(param);
        UIUtil.showUrlImage(binding.pic,pictures[pictures.length-1-position].getSmallUrl(),null,null);
    }
    /**
     * 显示网络上的图片
     * @param binding
     * @param position
     */
    public void  showUrlImage(PictureItemBinding binding,final int position){
        UIUtil.showUrlImage(binding.pic,pictures[pictures.length-1-position].getSmallUrl(),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.IMAGES,pictures);
                        intent.putExtra(ImagePagerActivity.IMAGE_POSITION,position);
                        context.startActivity(intent);
                    }
                },new View.OnLongClickListener() {
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
    public void showLocalImage(PictureItemBinding binding,final int position){
        UIUtil.showLocalImage(binding.pic,pictures[pictures.length-1-position].getSmallUrl(),new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //移除Drawable上的图片
                ArrayList<Picture> pictureArrayList = new ArrayList<>(Arrays.asList(pictures));
                for (int i = pictureArrayList.size() - 1; i >= 0; i--) {
                    if (pictureArrayList.get(i).getType()==DRAWABLE_TYPE) {
                        pictureArrayList.remove(i);
                    }
                }
                Picture[] images = new Picture[pictureArrayList.size()];
                pictureArrayList.toArray(images);
                Intent intent = new Intent(activity,ImagePagerActivity.class);
                intent.putExtra(ImagePagerActivity.IMAGES,images);
                intent.putExtra(ImagePagerActivity.IS_DELETE,true);
                intent.putExtra(ImagePagerActivity.IMAGE_POSITION,position);
                activity.startActivityForResult(intent, SendFriensLoopActivity.IMAGEBACK);
            }
        });
    }
    /**
     * 显示drawable的图片，并做相应的点击事件
     */
    public void showDrawableImage(PictureItemBinding binding, String s, View.OnClickListener onClickListener){
        UIUtil.showDrawableImage(binding.pic,s, onClickListener);
    }
}
