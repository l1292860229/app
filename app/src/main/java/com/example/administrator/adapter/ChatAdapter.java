package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.databinding.ChatsItemBinding;
import com.example.administrator.entity.Session;
import com.example.administrator.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<Session> mlist;
    public ChatAdapter(Context context, List<Session> mlist) {
        this.context = context;
        this.mlist = mlist;
    }
    @Override
    public int getCount() {
        return mlist == null ? 0 : mlist.size();
    }
    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatsItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.chats_item, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ChatsItemBinding) convertView.getTag();
        }
        binding.setVariable(BR.session, mlist.get(position));
        Session session = mlist.get(position);
        if (session.getUnreadcount()==0) {
            binding.messageCount.setVisibility(View.GONE);
        }
        if(session.getmMessageInfo()!=null){
            binding.content.setText(session.getmMessageInfo().getContent());
            binding.releasetime.setText(DateUtil.calculaterReleasedTime(context,new Date(session.getmMessageInfo().getTime()),session.getmMessageInfo().getTime(),0));
        }
        return convertView;
    }
}
