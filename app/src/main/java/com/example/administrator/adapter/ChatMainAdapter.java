package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.databinding.ChatMainLeftBinding;
import com.example.administrator.databinding.ChatMainRightBinding;
import com.example.administrator.entity.IMMessage;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.entity.db.MessageTable;
import com.example.administrator.util.DateUtil;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.ImageUitl;
import com.example.administrator.util.UIUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class ChatMainAdapter extends BaseAdapter {
    private Context context;
    private List<MessageTable> list;
    private UserInfo userInfo;
    public ChatMainAdapter(Context context, List<MessageTable> list) {
        this.context = context;
        this.list = list;
        userInfo = GetDataUtil.getUserInfo(context);
    }
    public void setData( List<MessageTable> list){
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
        ViewDataBinding binding = null;
        IMMessage imMessage = list.get(position).getIMMessage();
        if (convertView == null) {
            if (imMessage.getFrom().getId().equals(userInfo.getPhone())) {
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.chat_main_right, parent, false);
            }else{
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.chat_main_left, parent, false);
            }
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        hideAllLayout(binding,imMessage);
        switch (imMessage.getTypefile()) {
            case TEXT:
                setAllTextLayout(binding,imMessage);
                break;
            case PICTURE:
                setALLPictureLayout(binding,imMessage);
                break;
            case VOICE:
                setAllVoiceLayout(binding,imMessage,list.get(position));
                break;
            case MAP:
                setAllMapLayout(binding,imMessage);
                break;
            case REDPACKET:
                setAllRedpacketLayout(binding,imMessage);
                break;
            case SHAREURL:
                setAllShareurlLayout(binding,imMessage);
                break;
            case CARD:
                setAllCardLayout(binding,imMessage);
                break;
            case INVITE:
                setInviteLayout(binding,imMessage);
                break;
            case VIDEO:
                setAllVideoLayout(binding,imMessage);
                break;
            default:
                break;
        }
        binding.setVariable(BR.userinfo,imMessage);
        return binding.getRoot();
    }
    public void hideAllLayout(ViewDataBinding binding,IMMessage imMessage){
        if (binding instanceof ChatMainLeftBinding) {
            ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
            chatMainLeftBinding.txtTime.setText(DateUtil.calculaterReleasedTime(context,new Date(imMessage.getTime()),imMessage.getTime(),0));
            ImageUitl.setImage(chatMainLeftBinding.imgHead,imMessage.getFrom().getUrl());
            leftHideAllLayout(chatMainLeftBinding);
        }else if(binding instanceof ChatMainRightBinding){
            ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
            chatMainRightBinding.txtTime.setText(DateUtil.calculaterReleasedTime(context,new Date(imMessage.getTime()),imMessage.getTime(),0));
            ImageUitl.setImage(chatMainRightBinding.imgHead,imMessage.getFrom().getUrl());
            rightHideAllLayout(chatMainRightBinding);
        }
    }
    public void setAllTextLayout(ViewDataBinding binding,IMMessage imMessage){
        if (binding instanceof ChatMainLeftBinding) {
            ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
            setLeftTextLayout(chatMainLeftBinding,imMessage);
        }else if(binding instanceof ChatMainRightBinding){
            ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
            setRightTextLayout(chatMainRightBinding,imMessage);
        }
    }
    public void setALLPictureLayout(ViewDataBinding binding,IMMessage imMessage){
        if (binding instanceof ChatMainLeftBinding) {
            ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
            setLeftPictureLayout(chatMainLeftBinding,imMessage);
        }else if(binding instanceof ChatMainRightBinding){
            ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
            setRightPictureLayout(chatMainRightBinding,imMessage);
        }
    }
    public void setAllVoiceLayout(ViewDataBinding binding,IMMessage imMessage,MessageTable messageTable){
        if (binding instanceof ChatMainLeftBinding) {
            ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
            setLeftVoiceLayout(chatMainLeftBinding,imMessage,messageTable);
        }else if(binding instanceof ChatMainRightBinding){
            ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
            setRightVoiceLayout(chatMainRightBinding,imMessage);
        }
    }
    public void setAllMapLayout(ViewDataBinding binding,IMMessage imMessage){
        if (binding instanceof ChatMainLeftBinding) {
            ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
            setLeftMapLayout(chatMainLeftBinding,imMessage);
        }else if(binding instanceof ChatMainRightBinding){
            ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
            setRightMapLayout(chatMainRightBinding,imMessage);
        }
    }
    public void setAllRedpacketLayout(ViewDataBinding binding,IMMessage imMessage){
        if (binding instanceof ChatMainLeftBinding) {
            ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
            setLeftRedpacketLayout(chatMainLeftBinding,imMessage);
        }else if(binding instanceof ChatMainRightBinding){
            ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
            setRightRedpacketLayout(chatMainRightBinding,imMessage);
        }
    }
    public void setAllShareurlLayout(ViewDataBinding binding,IMMessage imMessage){
        if (binding instanceof ChatMainLeftBinding) {
            ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
            setLeftShareurlLayout(chatMainLeftBinding,imMessage);
        }else if(binding instanceof ChatMainRightBinding){
            ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
            setRightShareurlLayout(chatMainRightBinding,imMessage);
        }
    }
    public void setAllCardLayout(ViewDataBinding binding,IMMessage imMessage){
        if (binding instanceof ChatMainLeftBinding) {
            ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
            setLeftCardLayout(chatMainLeftBinding,imMessage);
        }else if(binding instanceof ChatMainRightBinding){
            ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
            setRightCardLayout(chatMainRightBinding,imMessage);
        }
    }
    public void setInviteLayout(ViewDataBinding binding,IMMessage imMessage){
        if (binding instanceof ChatMainLeftBinding) {
            ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
            setLeftInviteLayout(chatMainLeftBinding,imMessage);
        }else if(binding instanceof ChatMainRightBinding){
            ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
            setRightInviteLayout(chatMainRightBinding,imMessage);
        }
    }
    public void setAllVideoLayout(ViewDataBinding binding,IMMessage imMessage){
        if (binding instanceof ChatMainLeftBinding) {
            ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
            setLeftVideoLayout(chatMainLeftBinding,imMessage);
        }else if(binding instanceof ChatMainRightBinding){
            ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
            setRightVideoLayout(chatMainRightBinding,imMessage);
        }
    }



    //=====================================下面是左边===========================================//
    public void leftHideAllLayout(ChatMainLeftBinding chatMainLeftBinding){
        chatMainLeftBinding.msgVoiceLayout.setVisibility(View.GONE);
        chatMainLeftBinding.msgPhotoLayout.setVisibility(View.GONE);
        chatMainLeftBinding.msgVideoLayout.setVisibility(View.GONE);
        chatMainLeftBinding.msgTextLayout.setVisibility(View.GONE);
        chatMainLeftBinding.msgMapLayout.setVisibility(View.GONE);
        chatMainLeftBinding.msgUrlLayout.setVisibility(View.GONE);
        chatMainLeftBinding.msgInviteLayout.setVisibility(View.GONE);
        chatMainLeftBinding.msgRedpacketLayout.setVisibility(View.GONE);
        chatMainLeftBinding.cardLayout.setVisibility(View.GONE);
        chatMainLeftBinding.chatTalkMsgProgressBar.setVisibility(View.GONE);
        chatMainLeftBinding.xitong.setVisibility(View.GONE);
    }
    public void setLeftTextLayout(ChatMainLeftBinding chatMainLeftBinding,IMMessage imMessage){
        chatMainLeftBinding.msgTextLayout.setVisibility(View.VISIBLE);
        chatMainLeftBinding.msgText.setText(imMessage.getContent());
    }
    public void setLeftPictureLayout(ChatMainLeftBinding chatMainLeftBinding,IMMessage imMessage){
        chatMainLeftBinding.msgPhotoLayout.setVisibility(View.VISIBLE);
        ImageUitl.setImage(chatMainLeftBinding.msgPhoto,imMessage.getImage().getUrlsmall());
    }
    public void setLeftVoiceLayout(ChatMainLeftBinding chatMainLeftBinding,IMMessage imMessage,MessageTable messageTable){
        chatMainLeftBinding.msgVoiceLayout.setVisibility(View.VISIBLE);
        chatMainLeftBinding.msgVoiceTime.setText(imMessage.getVoice().getTime()+"\"");
        if (messageTable.getReadState()==1) {
            chatMainLeftBinding.voiceRead.setVisibility(View.GONE);
        }else{
            chatMainLeftBinding.voiceRead.setVisibility(View.VISIBLE);
        }
    }
    public void setLeftMapLayout(ChatMainLeftBinding chatMainLeftBinding,IMMessage imMessage){
        chatMainLeftBinding.msgMapLayout.setVisibility(View.VISIBLE);
        chatMainLeftBinding.msgMap.setText(imMessage.getLocation().getAddress());
        ImageUitl.setImage(chatMainLeftBinding.mapIcon, UIUtil.getMapUrl(imMessage.getLocation().getLng(),imMessage.getLocation().getLat()));
    }
    public void setLeftRedpacketLayout(ChatMainLeftBinding chatMainLeftBinding,IMMessage imMessage){
        chatMainLeftBinding.msgRedpacketLayout.setVisibility(View.VISIBLE);
    }
    public void setLeftShareurlLayout(ChatMainLeftBinding chatMainLeftBinding,IMMessage imMessage){
        chatMainLeftBinding.msgUrlLayout.setVisibility(View.VISIBLE);
        //ImageUitl.setImage(chatMainLeftBinding.imageUrl,imMessage.get);
    }
    public void setLeftCardLayout(ChatMainLeftBinding chatMainLeftBinding,IMMessage imMessage){
        chatMainLeftBinding.cardLayout.setVisibility(View.VISIBLE);
        //ImageUitl.setImage(chatMainLeftBinding.imageUrl,imMessage.get);
    }
    public void setLeftInviteLayout(ChatMainLeftBinding chatMainLeftBinding,IMMessage imMessage){
        chatMainLeftBinding.msgInviteLayout.setVisibility(View.VISIBLE);
        //ImageUitl.setImage(chatMainLeftBinding.imageUrl,imMessage.get);
    }
    public void setLeftVideoLayout(ChatMainLeftBinding chatMainLeftBinding,IMMessage imMessage){
        chatMainLeftBinding.msgVideoLayout.setVisibility(View.VISIBLE);
        //ImageUitl.setImage(chatMainLeftBinding.imageUrl,imMessage.getV);
    }




    //=====================================下面是右边===========================================//
    public void rightHideAllLayout(ChatMainRightBinding chatMainRightBinding){
        chatMainRightBinding.msgVoiceLayout.setVisibility(View.GONE);
        chatMainRightBinding.msgPhotoLayout.setVisibility(View.GONE);
        chatMainRightBinding.msgVideoLayout.setVisibility(View.GONE);
        chatMainRightBinding.msgTextLayout.setVisibility(View.GONE);
        chatMainRightBinding.msgMapLayout.setVisibility(View.GONE);
        chatMainRightBinding.msgUrlLayout.setVisibility(View.GONE);
        chatMainRightBinding.msgInviteLayout.setVisibility(View.GONE);
        chatMainRightBinding.msgRedpacketLayout.setVisibility(View.GONE);
        chatMainRightBinding.cardLayout.setVisibility(View.GONE);
        chatMainRightBinding.chatTalkMsgProgressBar.setVisibility(View.GONE);
        chatMainRightBinding.xitong.setVisibility(View.GONE);
    }
    public void setRightTextLayout(ChatMainRightBinding chatMainRightBinding,IMMessage imMessage){
        chatMainRightBinding.msgTextLayout.setVisibility(View.VISIBLE);
        chatMainRightBinding.msgText.setText(imMessage.getContent());
    }
    public void setRightPictureLayout(ChatMainRightBinding chatMainRightBinding,IMMessage imMessage){
        chatMainRightBinding.msgPhotoLayout.setVisibility(View.VISIBLE);
        ImageUitl.setImage(chatMainRightBinding.msgPhoto,imMessage.getImage().getUrlsmall());
    }
    public void setRightVoiceLayout(ChatMainRightBinding chatMainRightBinding,IMMessage imMessage){
        chatMainRightBinding.msgVoiceLayout.setVisibility(View.VISIBLE);
        chatMainRightBinding.msgVoiceTime.setText(imMessage.getVoice().getTime()+"\"");
    }
    public void setRightMapLayout(ChatMainRightBinding chatMainRightBinding,IMMessage imMessage){
        chatMainRightBinding.msgMapLayout.setVisibility(View.VISIBLE);
        chatMainRightBinding.msgMap.setText(imMessage.getLocation().getAddress());
        ImageUitl.setImage(chatMainRightBinding.mapIcon, UIUtil.getMapUrl(imMessage.getLocation().getLng(),imMessage.getLocation().getLat()));
    }
    public void setRightRedpacketLayout(ChatMainRightBinding chatMainRightBinding,IMMessage imMessage){
        chatMainRightBinding.msgRedpacketLayout.setVisibility(View.VISIBLE);
    }
    public void setRightShareurlLayout(ChatMainRightBinding chatMainRightBinding,IMMessage imMessage){
        chatMainRightBinding.msgUrlLayout.setVisibility(View.VISIBLE);
        //ImageUitl.setImage(chatMainLeftBinding.imageUrl,imMessage.get);
    }
    public void setRightCardLayout(ChatMainRightBinding chatMainRightBinding,IMMessage imMessage){
        chatMainRightBinding.cardLayout.setVisibility(View.VISIBLE);
        //ImageUitl.setImage(chatMainLeftBinding.imageUrl,imMessage.get);
    }
    public void setRightInviteLayout(ChatMainRightBinding chatMainRightBinding,IMMessage imMessage){
        chatMainRightBinding.msgInviteLayout.setVisibility(View.VISIBLE);
        //ImageUitl.setImage(chatMainLeftBinding.imageUrl,imMessage.get);
    }
    public void setRightVideoLayout(ChatMainRightBinding chatMainRightBinding,IMMessage imMessage){
        chatMainRightBinding.msgVideoLayout.setVisibility(View.VISIBLE);
        //ImageUitl.setImage(chatMainLeftBinding.imageUrl,imMessage.getV);
    }
}
