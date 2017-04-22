package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.databinding.ChatsItemBinding;
import com.example.administrator.entity.Session;
import com.example.administrator.enumset.MessageType;
import com.example.administrator.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class ChatFragmentAdapter extends BaseAdapter {
    private Context context;
    private List<Session> mlist;
    public ChatFragmentAdapter(Context context, List<Session> mlist) {
        this.context = context;
        this.mlist = mlist;
    }
    public void setData(List<Session> mlist){
        this.mlist = mlist;
    }
    public List<Session> getData(){
        return this.mlist;
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

    /**
     * 跟据不同的状态选择不同的菜单栏
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mlist.get(position).itemViewType();
    }

    /**
     * 菜单栏个数
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 4;
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
        if (session.getIsTop()!=0) {
            binding.llContent.setBackgroundColor(Color.rgb(254,249,233));
        }
        binding.content.setText(getContent(session.getMessageType(),session.getContent()));
        binding.releasetime.setText(DateUtil.calculaterReleasedTime(context,new Date(session.getCreatetime()),session.getCreatetime(),0));
        return convertView;
    }

    /**
     * 获取会话内容
     */
    public String getContent(MessageType messageType, String content){
        switch(messageType){
            case PICTURE:
                return "[图片]";
            case VOICE:
                return "[语音]";
            case MAP:
                return "[定位]";
            case REDPACKET:
                return "[红包]";
            case SHAREURL:
                return "[链接]";
            case CARD:
                return "[名片]";
            case INVITE:
                return "[邀请]";
            case VIDEO:
                return "[小视频]";
            default:
                return content;
        }
    }
}
