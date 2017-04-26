package com.example.administrator.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.administrator.adapter.BBSChatMainAdapter;
import com.example.administrator.entity.BBSMessage;
import com.example.administrator.entity.Bbs;
import com.example.administrator.entity.FavoriteItem;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.enumset.MessageType;
import com.example.administrator.enumset.TypeChat;
import com.example.administrator.fragment.ChatFragment;
import com.example.administrator.interfaceview.IUBBSChatMainView;
import com.example.administrator.presenter.BBSChatMainPresenter;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/14.
 */

public class BBSChatMainActivity extends ChatMainActivity implements IUBBSChatMainView {
    //intent key值
    public final static String BBSTYPE="BBSTYPE";
    BBSChatMainPresenter bbsChatMainPresenter;
    BBSChatMainAdapter bbsChatMainAdapter;
    String mBid,mName,mHead;
    Bbs.Bbstype mBbstype;
    int max,min;
    SendMessage sendMessage;
    List<BBSMessage> mBbsMessageList;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendMessage = new SendMessage();
        bbsChatMainPresenter = new BBSChatMainPresenter(this,this);
        binding.chatBox.qiehuan.setVisibility(View.VISIBLE);
        binding.chatBox.menu.setVisibility(View.VISIBLE);
        binding.chatBox.noramlKeyboradLayout.setVisibility(View.GONE);
        binding.chatBox.qiehuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.chatBox.menu.setVisibility(View.GONE);
                    binding.chatBox.noramlKeyboradLayout.setVisibility(View.VISIBLE);
                }else{
                    binding.chatBox.menu.setVisibility(View.VISIBLE);
                    binding.chatBox.noramlKeyboradLayout.setVisibility(View.GONE);
                }
            }
        });
        bbsChatMainPresenter.init();
    }

    @Override
    public void init(List<BBSMessage> bbsMessageList,String bid, String name, String head, Bbs.Bbstype bbstype,int max,int min) {
        Collections.reverse(bbsMessageList);
        mBbsMessageList = bbsMessageList;
        mBid = bid;
        mName = name;
        mHead = head;
        mBbstype = bbstype;
        this.max = max;
        this.min =min;
        bbsChatMainAdapter  = new BBSChatMainAdapter(this,mBbsMessageList);
        binding.titleLayout.titlecontext.setText(mName);
        binding.chatMainListMsg.setAdapter(bbsChatMainAdapter);
        if (bbsMessageList.size()>5) {
            binding.chatMainListMsg.setSelection(mBbsMessageList.size());
        }
    }

    @Override
    public void add(BBSMessage bbsMessage) {
        for (int i = mBbsMessageList.size() - 1; i >= 0; i--) {
            if(mBbsMessageList.get(i).getId().equals(bbsMessage.getId())){
                mBbsMessageList.remove(i);
                mBbsMessageList.add(bbsMessage);
                bbsChatMainAdapter.setData(mBbsMessageList);
                bbsChatMainAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * 发送文字消息
     * @param view
     */
    public void sentTextMessage(View view){
        sendMessage.initialize()
                .setTypefile(MessageType.TEXT).setContent(binding.chatBox.chatBoxEditKeyword.getText().toString())
                .send();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (data == null && requestCode!=GET_IMAGE_BY_CAMERA) {
               return;
            }
            //返回时,会变成true
            binding.chatMainListMsg.setCanLoadMore(false);
            switch (requestCode){
                case ImageSelector.IMAGE_REQUEST_CODE:
                    // 获取选中的图片路径列表 Get Images Path List 发送相册中的图片
                    List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                    for (String s : pathList) {
                        sendMessage.initialize()
                                .setTypefile(MessageType.PICTURE).setContent("[图片]")
                                .setImage(s)
                                .send();
                    }
                    break;
                case GET_IMAGE_BY_CAMERA://从相机中获得图片
                    //发送拍照的图片
                    sendMessage.initialize()
                            .setTypefile(MessageType.PICTURE).setContent("[图片]")
                            .setImage(CameraImagePath)
                            .send();
                    break;
                case GET_MAP://获得定位
                    String address =  data.getStringExtra(BaiduMapActivity.ADDRESS);
                    double lat =  data.getDoubleExtra(BaiduMapActivity.LAT,0);
                    double lon =  data.getDoubleExtra(BaiduMapActivity.LON,0);
                    sendMessage.initialize()
                            .setTypefile(MessageType.MAP).setContent("[定位]")
                            .setLocation(lat,lon,address)
                            .send();
                    break;
                case GET_MYFAVORITE://获得收藏上的东西
                    FavoriteItem favoriteitem = (FavoriteItem) data.getSerializableExtra(MyFavoriteActivity.FAVORITEITEM);
                    FavoriteItem.FavoriteItemContent ffContext = favoriteitem.getFavoriteItemContent();
                    switch (ffContext.getTypefile()){
                        case TEXT://发送文本
                            sendMessage.initialize()
                                    .setTypefile(MessageType.TEXT).setContent(ffContext.getContent())
                                    .send();
                            break;
                        case PICTURE://发送图片
                            sendMessage.initialize()
                                    .setTypefile(MessageType.PICTURE).setImage(ffContext.getUrlsmall())
                                    .send();
                            break;
                        case VIDEO://发送语音
                            sendMessage.initialize()
                                    .setTypefile(MessageType.VIDEO)
                                    .setVoice(Integer.parseInt(ffContext.getTime()),ffContext.getUrl())
                                    .send();
                            break;
                        case MAP://发送地图
                            sendMessage.initialize()
                                    .setTypefile(MessageType.MAP)
                                    .setLocation(ffContext.getLat(),ffContext.getLng(),ffContext.getAddress())
                                    .send();
                            break;
                    }
                    break;
                case GET_USERINFO:
                    UserInfo userInfo = (UserInfo) data.getSerializableExtra(ChooseUserActivity.DATA);
                    sendMessage.initialize()
                            .setTypefile(MessageType.CARD).setCard(userInfo.getPhone(),userInfo.getHeadsmall(),userInfo.getNickname(),null)
                            .send();
                    break;
            }
            binding.chatBox.chatBoxBtnAdd.setChecked(false);
        }
    }

    /**
     * 发送消息的内部类
     */
    public  class SendMessage{
         BBSMessage bbsMessage;
        public  SendMessage initialize(){
            bbsMessage = new BBSMessage();
            return this;
        }
        public SendMessage setContent(String  content){
            bbsMessage.setContent(content);
            return this;
        }
        public SendMessage setVoice(int time, String url){
            bbsMessage.setVoice(bbsMessage.new Voice(time,url));
            return this;
        }
        public SendMessage setImage(String imagePath){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, options);
            bbsMessage.setImage(bbsMessage.new Image(imagePath,imagePath,options.outWidth,options.outHeight));
            return this;
        }
        public SendMessage setLocation(double lat,double lng,String address){
            bbsMessage.setLocation(bbsMessage.new Location(lat,lng,address));
            return this;
        }
        public SendMessage setTypefile(MessageType messageType){
            bbsMessage.setTypefile(messageType);
            return this;
        }
        public SendMessage setCard(String uid, String headsmall, String nickname, String content){
            bbsMessage.setCard(bbsMessage.new Card(uid,headsmall,nickname,content));
            return this;
        }
        public void send(){
            bbsMessage.setId(userInfo.getUid()+"_"+mBid+"_"+System.currentTimeMillis());
            bbsMessage.setBid(mBid);
            bbsMessage.setUid(userInfo.getUid());
            bbsMessage.setNickname(userInfo.getNickname());
            bbsMessage.setHeadsmall(userInfo.getHeadsmall());
            bbsMessage.setTypechat(TypeChat.BBS);
            bbsMessage.setTime(System.currentTimeMillis());
            bbsMessage.setTag(UUID.randomUUID().toString());
            if (bbsMessage.getTypefile() == MessageType.TEXT) {
                binding.chatBox.chatBoxEditKeyword.setText("");
            }
            mBbsMessageList.add(bbsMessage);
            bbsChatMainAdapter.setData(mBbsMessageList);
            bbsChatMainAdapter.notifyDataSetChanged();
            bbsChatMainPresenter.sendMessage(bbsMessage,mBbstype,mBid,mName,mHead);
            sendBroadcast(new Intent(ChatFragment.REFRESH_SESSION_DATA));
        }
    }
}
