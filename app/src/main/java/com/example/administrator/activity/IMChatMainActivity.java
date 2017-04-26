package com.example.administrator.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.administrator.R;
import com.example.administrator.UIView.AudioRecorderButton;
import com.example.administrator.adapter.IMChatMainAdapter;
import com.example.administrator.entity.FavoriteItem;
import com.example.administrator.entity.IMMessage;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.entity.db.MessageTable;
import com.example.administrator.enumset.MessageType;
import com.example.administrator.enumset.TypeChat;
import com.example.administrator.fragment.ChatFragment;
import com.example.administrator.interfaceview.IUIMChatMainView;
import com.example.administrator.presenter.IMChatMainPresenter;
import com.example.administrator.util.MediaManager;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/14.
 */

public class IMChatMainActivity extends ChatMainActivity implements IUIMChatMainView {
    //intent key值
    public final static String TYPECHAT="typechat";
    IMChatMainAdapter IMChatMainAdapter;
    IMChatMainPresenter IMChatMainPresenter;
    List<MessageTable> mMessageTables;
    //全局全变量
    private String mToId;
    private String mToName;
    private String mTohead;
    private TypeChat mTypeChat;
    private SendMessage sendMessage;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendMessage = new SendMessage();
        IMChatMainPresenter = new IMChatMainPresenter(this,this);
        binding.chatMainListMsg.setCanLoadMore(false);
        IMChatMainPresenter.init();
    }

    @Override
    public void init(List<MessageTable> messageTables,String name,String toid,String tohead,TypeChat typeChat) {
        mToId = toid;
        mTypeChat = typeChat;
        mToName = name;
        mTohead = tohead;
        binding.titleLayout.titlecontext.setText(name);
        mMessageTables = messageTables;
        IMChatMainAdapter = new IMChatMainAdapter(context,mMessageTables);
        binding.chatMainListMsg.setAdapter(IMChatMainAdapter);
        if (messageTables.size()>5) {
            binding.chatMainListMsg.setStackFromBottom(true);
        }
        binding.chatMainListMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mMessageTables.get(position-1).getTypefile()== MessageType.VOICE.getValue()) {
                    final ImageView animView = (ImageView) view.findViewById(R.id.msg_voice);
                    animView.setImageResource(R.drawable.play_anim);
                    // 播放录音
                    MediaManager.playSound(mMessageTables.get(position-1).getIMMessage().getVoice().getUrl(),new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            animView.setImageResource(R.mipmap.lvoice3);
                        }
                    }, animView);
                }
            }
        });
        //录音按钮
        binding.chatBox.chatBoxBtnVoice.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                //发送语音
                sendMessage.initialize()
                        .setTypefile(MessageType.VOICE).setContent("[语音]").setVoice((int)seconds,filePath)
                        .send();
            }
        });
    }
    /**
     * 发送文字消息
     * @param view
     */
    @Override
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
        }
    }

    /**
     * 发送消息的内部类
     */
    public  class SendMessage{
         IMMessage imMessage;
        public  SendMessage initialize(){
            imMessage = new IMMessage();
            return this;
        }
        public SendMessage setContent(String  content){
            imMessage.setContent(content);
            return this;
        }
        public SendMessage setVoice(int time, String url){
            imMessage.setVoice(imMessage.new Voice(time,url));
            return this;
        }
        public SendMessage setImage(String imagePath){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, options);
            imMessage.setImage(imMessage.new Image(imagePath,imagePath,options.outWidth,options.outHeight));
            return this;
        }
        public SendMessage setLocation(double lat,double lng,String address){
            imMessage.setLocation(imMessage.new Location(lat,lng,address));
            return this;
        }
        public SendMessage setTypefile(MessageType messageType){
            imMessage.setTypefile(messageType);
            return this;
        }
        public SendMessage setCard(String uid, String headsmall, String nickname, String content){
            imMessage.setCard(imMessage.new Card(uid,headsmall,nickname,content));
            return this;
        }
        public void send(){
            imMessage.setId(userInfo.getUid()+"_"+mToId+"_"+System.currentTimeMillis());
            imMessage.setFrom(imMessage.new UserInfo(userInfo.getPhone(),userInfo.getNickname(),userInfo.getHeadsmall()));
            imMessage.setTo(imMessage.new UserInfo(mToId,mToName,mTohead));
            imMessage.setTypechat(mTypeChat);
            imMessage.setTime(System.currentTimeMillis());
            imMessage.setTag(UUID.randomUUID().toString());
            IMChatMainPresenter.sendMessage(imMessage);
            mMessageTables.add(imMessage.getMessageTable(userInfo.getUid()));
            IMChatMainAdapter.setData(mMessageTables);
            IMChatMainAdapter.notifyDataSetChanged();
            if (imMessage.getTypefile() == MessageType.TEXT) {
                binding.chatBox.chatBoxEditKeyword.setText("");
            }
            sendBroadcast(new Intent(ChatFragment.REFRESH_SESSION_DATA));
        }
    }
}
