package com.example.administrator.presenter;

import com.example.administrator.activity.IMChatMainActivity;
import com.example.administrator.dbDao.MessageTableDao;
import com.example.administrator.entity.IMMessage;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.entity.db.MessageTable;
import com.example.administrator.enumset.MessageType;
import com.example.administrator.enumset.TypeChat;
import com.example.administrator.interfaceview.IUIMChatMainView;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.smartandroid.sa.loopj.AsyncHttpResponseHandler;
import com.smartandroid.sa.loopj.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by dell on 2017/4/22.
 */

public class IMChatMainPresenter extends  BasePresenter {
    private IUIMChatMainView chatMainView;
    private MessageTableDao messagetableDao;
    public IMChatMainPresenter(IMChatMainActivity context, IUIMChatMainView chatMainView) {
        super(context);
        this.chatMainView = chatMainView;
        messagetableDao = new MessageTableDao();
    }

    public void init() {
        String name = context.getIntent().getStringExtra(IMChatMainActivity.NAME);
        String toid = context.getIntent().getStringExtra(IMChatMainActivity.TOID);
        String tohead = context.getIntent().getStringExtra(IMChatMainActivity.TOHEAD);
        TypeChat typeChat  = (TypeChat) context.getIntent().getSerializableExtra(IMChatMainActivity.TYPECHAT);
        chatMainView.init(messagetableDao.select(userInfo.getUid(),toid),name,toid,tohead,typeChat);
    }
    public void sendMessage(IMMessage imMessage){
        final MessageTable messageTable = imMessage.getMessageTable(userInfo.getUid());
        messagetableDao.insert(messageTable);

        RequestParams params = new RequestParams();
        params.put("typechat", imMessage.getTypechat().getValue());
        params.put("fromname", imMessage.getFrom().getName());
        params.put("fromid", imMessage.getFrom().getId());
        params.put("fromurl", imMessage.getFrom().getUrl());
        params.put("toid", imMessage.getTo().getId());
        params.put("toname", imMessage.getTo().getName());
        params.put("tourl", imMessage.getTo().getUrl());
        params.put("typefile", imMessage.getTypefile().getValue());
        params.put("content", imMessage.getContent());
        params.put("tag", imMessage.getTag());
        if (imMessage.getTypefile()== MessageType.PICTURE && imMessage.getImage()!=null) {
            if(imMessage.getImage().getUrllarge().startsWith("http")){
                params.put("image",messageTable.getImage());
            }else{
                try {
                    params.put("pic",new File(imMessage.getImage().getUrllarge()));
                    params.put("width", imMessage.getImage().getWidth());
                    params.put("height", imMessage.getImage().getHeight());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    UIUtil.showMessage(context,"发送图片不存在");
                    return;
                }
            }
        }
        if (imMessage.getTypefile()== MessageType.VOICE&& imMessage.getVoice()!=null) {
            if(imMessage.getVoice().getUrl().startsWith("http")){
                params.put("voice",messageTable.getVoice());
            }else{
                try {
                    params.put("pic",new File(imMessage.getVoice().getUrl()));
                    params.put("voicetime",imMessage.getVoice().getTime());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    UIUtil.showMessage(context,"发送语音不存在");
                    return;
                }
            }
        }
        if (imMessage.getTypefile()== MessageType.REDPACKET && imMessage.getRedpacket()!=null) {
                params.put("redpackettitle",imMessage.getRedpacket().getRedpackettitle());
                params.put("redpacketurl",imMessage.getRedpacket().getRedpacketurl());
        }
        if (imMessage.getTypefile()== MessageType.MAP && imMessage.getLocation()!=null) {
            params.put("lat",imMessage.getLocation().getLat());
            params.put("lng",imMessage.getLocation().getLng());
            params.put("address",imMessage.getLocation().getAddress());
        }
        params.put("uid",userInfo.getUid());
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.USER_SENDMESSAGE, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        messageTable.setSendState(0);
                        messagetableDao.insert(messageTable);
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
