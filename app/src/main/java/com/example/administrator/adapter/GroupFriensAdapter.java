package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.R;
import com.example.administrator.UIView.LQRNineGridImageViewAdapter;
import com.example.administrator.databinding.ContactItemBinding;
import com.example.administrator.entity.Group;
import com.example.administrator.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class GroupFriensAdapter extends BaseAdapter {
    private Context context;
    private List<Group> list;
    public GroupFriensAdapter(Context context, List<Group> list) {
        this.context = context;
        this.list = list;
    }
    public void setData( List<Group> list){
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
        Group group = list.get(position);
        binding.headerimage.setVisibility(View.GONE);
        binding.groupHeader.setVisibility(View.VISIBLE);
        //填充要显示的群聊头像
        List<String> strList = new ArrayList<>();
        for (UserInfo userInfo : group.getList()) {
            strList.add(userInfo.getHeadsmall());
            if(strList.size()>=9){
                break;
            }
        }
        binding.groupHeader.setAdapter(new LQRNineGridImageViewAdapter());
        binding.groupHeader.setImagesData(strList);
        binding.setNickname(group.getName());
        return binding.getRoot();
    }
}
