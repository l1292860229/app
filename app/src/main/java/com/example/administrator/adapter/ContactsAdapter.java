package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.R;
import com.example.administrator.databinding.ContactItemBinding;
import com.example.administrator.entity.UserInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/22.
 */

public class ContactsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<UserInfo> list;
    public ContactsAdapter(Context context, ArrayList<UserInfo> list) {
        this.context = context;
        this.list = list;
    }
    public void setData( ArrayList<UserInfo> list){
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
        ContactItemBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.contact_item, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        UserInfo userInfo = list.get(position);
        binding.setNickname(userInfo.getNickname());
        binding.setHeadsmall(userInfo.getHeadsmall());
        return binding.getRoot();
    }
}
