package com.example.administrator.adapter;

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

import com.example.administrator.R;
import com.example.administrator.activity.ImagePagerActivity;
import com.example.administrator.databinding.PictureItemBinding;
import com.example.administrator.entity.Picture;
import com.example.administrator.util.ImageUitl;

/**
 * Created by Administrator on 2017/2/4.
 */

public class ImageAdapter extends BaseAdapter {
    private  Picture[] pictures;
    private Context context;
    public ImageAdapter(Context context,Picture[] pictures){
        this.pictures = pictures;
        this.context = context;
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
        return binding.getRoot();
    }
}
