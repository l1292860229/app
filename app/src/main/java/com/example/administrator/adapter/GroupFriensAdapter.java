package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.R;
import com.example.administrator.databinding.ContactItemBinding;
import com.example.administrator.entity.Picture;
import com.example.administrator.entity.Room;
import com.example.administrator.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.entity.Picture.PictureType.URL_NOTCLICK_TYPE;

/**
 * Created by Administrator on 2017/1/22.
 */

public class GroupFriensAdapter extends BaseAdapter {
    private Context context;
    private List<Room> list;
    public GroupFriensAdapter(Context context, List<Room> list) {
        this.context = context;
        this.list = list;
    }
    public void setData( List<Room> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.contact_item, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        Room room = list.get(position);
        binding.headerimage.setVisibility(View.GONE);
        binding.groupHeader.setVisibility(View.VISIBLE);
        List<Picture> pictureList = new ArrayList<>();
        for (UserInfo userInfo : room.getList()) {
            pictureList.add(new Picture(userInfo.getHeadsmall(),userInfo.getHeadsmall(),URL_NOTCLICK_TYPE));
            if(pictureList.size()>=9){
                break;
            }
        }
        binding.groupHeader.setAdapter(new ImageAdapter(context,pictureList));
        binding.setNickname(room.getName());
        return binding.getRoot();
    }
}
