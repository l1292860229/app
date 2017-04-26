package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.databinding.ChatMainLeftBinding;
import com.example.administrator.databinding.ChatMainRightBinding;
import com.example.administrator.entity.BaseMessage;
import com.example.administrator.entity.UserInfo;
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
    protected Context context;
    protected List list;
    protected UserInfo userInfo;
    public ChatMainAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
        userInfo = GetDataUtil.getUserInfo(context);
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
        return convertView;
    }
    public class ViewHolder{
        private TextView txtTime,msgText,msgVoiceTime,msgMap,cardName,xitongtextview;
        private ImageView imgHead,msgPhoto,voiceRead,mapIcon,cardHeader;
        private RelativeLayout msgVoiceLayout,msgPhotoLayout,msgVideoLayout,msgTextLayout,
                msgMapLayout,msgUrlLayout,msgInviteLayout,msgRedpacketLayout,
                xitong,msg;
        private LinearLayout cardLayout;
        private ProgressBar chatTalkMsgProgressBar;
        public ViewHolder(ViewDataBinding binding){
            if (binding instanceof ChatMainLeftBinding) {
                ChatMainLeftBinding chatMainLeftBinding = (ChatMainLeftBinding)binding;
                initLeft(chatMainLeftBinding);
            }else if(binding instanceof ChatMainRightBinding){
                ChatMainRightBinding chatMainRightBinding = (ChatMainRightBinding)binding;
                initRigjt(chatMainRightBinding);
            }
        }
        public void initLeft(ChatMainLeftBinding chatMainLeftBinding){
            txtTime =                chatMainLeftBinding.txtTime;
            msgText =                chatMainLeftBinding.msgText;
            msgVoiceTime =           chatMainLeftBinding.msgVoiceTime;
            msgMap =                 chatMainLeftBinding.msgMap;
            cardName =               chatMainLeftBinding.cardName;
            imgHead =                chatMainLeftBinding.imgHead;
            msgPhoto =               chatMainLeftBinding.msgPhoto;
            voiceRead =              chatMainLeftBinding.voiceRead;
            mapIcon =                chatMainLeftBinding.mapIcon;
            cardHeader =             chatMainLeftBinding.cardHeader;
            msgVoiceLayout =         chatMainLeftBinding.msgVoiceLayout;
            msgPhotoLayout =         chatMainLeftBinding.msgPhotoLayout;
            msgVideoLayout =         chatMainLeftBinding.msgVideoLayout;
            msgTextLayout =          chatMainLeftBinding.msgTextLayout;
            msgMapLayout =           chatMainLeftBinding.msgMapLayout;
            msgUrlLayout =           chatMainLeftBinding.msgUrlLayout;
            msgInviteLayout=         chatMainLeftBinding.msgInviteLayout;
            msgRedpacketLayout =     chatMainLeftBinding.msgRedpacketLayout;
            cardLayout =             chatMainLeftBinding.cardLayout;
            chatTalkMsgProgressBar = chatMainLeftBinding.chatTalkMsgProgressBar;
            xitong =                 chatMainLeftBinding.xitong;
            xitongtextview =         chatMainLeftBinding.xitongtextview;
            msg =                    chatMainLeftBinding.msg;
        }
        public void initRigjt(ChatMainRightBinding chatMainRightBinding){
            txtTime =                chatMainRightBinding.txtTime;
            msgText =                chatMainRightBinding.msgText;
            msgVoiceTime =           chatMainRightBinding.msgVoiceTime;
            msgMap =                 chatMainRightBinding.msgMap;
            cardName =               chatMainRightBinding.cardName;
            imgHead =                chatMainRightBinding.imgHead;
            msgPhoto =               chatMainRightBinding.msgPhoto;
            mapIcon =                chatMainRightBinding.mapIcon;
            cardHeader =             chatMainRightBinding.cardHeader;
            msgVoiceLayout =         chatMainRightBinding.msgVoiceLayout;
            msgPhotoLayout =         chatMainRightBinding.msgPhotoLayout;
            msgVideoLayout =         chatMainRightBinding.msgVideoLayout;
            msgTextLayout =          chatMainRightBinding.msgTextLayout;
            msgMapLayout =           chatMainRightBinding.msgMapLayout;
            msgUrlLayout =           chatMainRightBinding.msgUrlLayout;
            msgInviteLayout=         chatMainRightBinding.msgInviteLayout;
            msgRedpacketLayout =     chatMainRightBinding.msgRedpacketLayout;
            cardLayout =             chatMainRightBinding.cardLayout;
            chatTalkMsgProgressBar = chatMainRightBinding.chatTalkMsgProgressBar;
            xitong =                 chatMainRightBinding.xitong;
            xitongtextview =         chatMainRightBinding.xitongtextview;
            msg =                    chatMainRightBinding.msg;
        }
        public void hideAllLayout(){
            msgVoiceLayout.setVisibility(View.GONE);
            msgPhotoLayout.setVisibility(View.GONE);
            msgVideoLayout.setVisibility(View.GONE);
            msgTextLayout.setVisibility(View.GONE);
            msgMapLayout.setVisibility(View.GONE);
            msgUrlLayout.setVisibility(View.GONE);
            msgInviteLayout.setVisibility(View.GONE);
            msgRedpacketLayout.setVisibility(View.GONE);
            cardLayout.setVisibility(View.GONE);
            chatTalkMsgProgressBar.setVisibility(View.GONE);
            xitong.setVisibility(View.GONE);
        }
        public void setTextLayout(BaseMessage imMessage){
            msgTextLayout.setVisibility(View.VISIBLE);
            msgText.setText(imMessage.getContent());
        }
        public void setPictureLayout(BaseMessage imMessage){
            msgPhotoLayout.setVisibility(View.VISIBLE);
            if(imMessage.getImage().getUrlsmall().contains("http")){
                msgPhoto.setLayoutParams(new RelativeLayout.LayoutParams(imMessage.getImage().getWidth(),imMessage.getImage().getHeight()));
                ImageUitl.setImage(msgPhoto,imMessage.getImage().getUrlsmall());
            }else{
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imMessage.getImage().getUrlsmall(),options);
                options.inSampleSize = calculateInSampleSize(options);
                // 使用获取到的inSampleSize值再次解析图片
                options.inJustDecodeBounds = false;
                msgPhoto.setImageBitmap(BitmapFactory.decodeFile(imMessage.getImage().getUrlsmall(),options));
            }
        }
        public void setVoiceLayout(BaseMessage imMessage){
            msgVoiceLayout.setVisibility(View.VISIBLE);
            msgVoiceTime.setText(imMessage.getVoice().getTime()+"\"");
        }
        public void setMapLayout(BaseMessage imMessage){
            msgMapLayout.setVisibility(View.VISIBLE);
            msgMap.setText(imMessage.getLocation().getAddress());
            ImageUitl.setImage(mapIcon, UIUtil.getMapUrl(imMessage.getLocation().getLng(),imMessage.getLocation().getLat()));
        }
        public void setRedpacketLayout(BaseMessage imMessage){
            msgRedpacketLayout.setVisibility(View.VISIBLE);
        }
        public void setShareurlLayout(BaseMessage imMessage){
            msgUrlLayout.setVisibility(View.VISIBLE);
        }
        public void setCardLayout(BaseMessage imMessage){
            cardLayout.setVisibility(View.VISIBLE);
            BaseMessage.Card card = imMessage.getCard();
            ImageUitl.setImage(cardHeader,card.getHeadsmall());
            cardName.setText(card.getNickname());
        }
        public void setInviteLayout(BaseMessage imMessage){
            msgInviteLayout.setVisibility(View.VISIBLE);
        }
        public void setVideoLayout(BaseMessage imMessage){
            msgVideoLayout.setVisibility(View.VISIBLE);
        }
        public void setheadAndTimeView(String head,long time){
            txtTime.setText(DateUtil.calculaterReleasedTime(context,new Date(time),time,0));
            ImageUitl.setImage(imgHead,head);
        }
        public void setXitongView(String message){
            xitong.setVisibility(View.VISIBLE);
            msg.setVisibility(View.GONE);
            xitongtextview.setText(message);
        }
        public void setView(BaseMessage baseMessage){
            hideAllLayout();
            switch (baseMessage.getTypefile()) {
                case TEXT:
                    setTextLayout(baseMessage);
                    break;
                case PICTURE:
                    setPictureLayout(baseMessage);
                    break;
                case VOICE:
                    setVoiceLayout(baseMessage);
                    break;
                case MAP:
                    setMapLayout(baseMessage);
                    break;
                case REDPACKET:
                    setRedpacketLayout(baseMessage);
                    break;
                case SHAREURL:
                    setShareurlLayout(baseMessage);
                    break;
                case CARD:
                    setCardLayout(baseMessage);
                    break;
                case INVITE:
                    setInviteLayout(baseMessage);
                    break;
                case VIDEO:
                    setVideoLayout(baseMessage);
                    break;
                default:
                    break;
            }
        }
    }
    /**
     * 压缩图片
     * @param options
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > 120 || width > 120) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) 120);
            final int widthRatio = Math.round((float) width / (float) 120);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
