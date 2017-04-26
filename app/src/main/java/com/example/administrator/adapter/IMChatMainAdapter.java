package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.entity.IMMessage;
import com.example.administrator.entity.db.MessageTable;

import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class IMChatMainAdapter extends ChatMainAdapter {
    public IMChatMainAdapter(Context context, List<MessageTable> list) {
        super(context,list);
    }
    public void setData( List<MessageTable> list){
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(list.get(position) instanceof MessageTable){
            ViewDataBinding binding;
            IMMessage imMessage = ((MessageTable)list.get(position)).getIMMessage();
            if (imMessage.getFrom().getId().equals(userInfo.getPhone())) {
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.chat_main_right, parent, false);
            }else{
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.chat_main_left, parent, false);
            }
            ViewHolder viewHolder = new ViewHolder(binding);
            viewHolder.setheadAndTimeView(imMessage.getFrom().getUrl(),imMessage.getTime());
            viewHolder.setView(imMessage);
            binding.setVariable(BR.userinfo,imMessage);
            return binding.getRoot();
        }
        return convertView;
    }
}
