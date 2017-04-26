package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.entity.BBSMessage;

import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class BBSChatMainAdapter extends ChatMainAdapter {
    public BBSChatMainAdapter(Context context, List<BBSMessage> list) {
        super(context,list);
    }
    public void setData( List<BBSMessage> list){
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(list.get(position) instanceof BBSMessage){
            ViewDataBinding binding;
            BBSMessage bbsMessage = ((BBSMessage)list.get(position));
            if (bbsMessage.getUid().equals(userInfo.getUid())) {
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.chat_main_right, parent, false);
            }else{
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.chat_main_left, parent, false);
                if (bbsMessage.getUid().equals("0")) {
                    ViewHolder viewHolder = new ViewHolder(binding);
                    viewHolder.setXitongView(bbsMessage.getContent());
                    return binding.getRoot();
                }
            }
            ViewHolder viewHolder = new ViewHolder(binding);
            viewHolder.setheadAndTimeView(bbsMessage.getHeadsmall(),bbsMessage.getTime());
            viewHolder.setView(bbsMessage);
            binding.setVariable(BR.userinfo,bbsMessage);
            return binding.getRoot();
        }
        return convertView;
    }
}
