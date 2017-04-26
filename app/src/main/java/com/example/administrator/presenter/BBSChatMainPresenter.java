package com.example.administrator.presenter;

import android.util.Log;

import com.example.administrator.activity.BBSChatMainActivity;
import com.example.administrator.activity.ChatMainActivity;
import com.example.administrator.dbDao.MessageTableDao;
import com.example.administrator.entity.BBSMessage;
import com.example.administrator.entity.Bbs;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.entity.db.MessageTable;
import com.example.administrator.enumset.MessageType;
import com.example.administrator.interfaceview.IUBBSChatMainView;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.google.gson.reflect.TypeToken;
import com.smartandroid.sa.loopj.AsyncHttpResponseHandler;
import com.smartandroid.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.util.GsonUtil.parseJsonWithGsonObject;

/**
 * Created by dell on 2017/4/22.
 */

public class BBSChatMainPresenter extends  BasePresenter {
    private IUBBSChatMainView chatMainView;
    private MessageTableDao messagetableDao;
    public BBSChatMainPresenter(BBSChatMainActivity context, IUBBSChatMainView chatMainView) {
        super(context);
        this.chatMainView = chatMainView;
        this.messagetableDao = new MessageTableDao();
    }

    public void init() {
        String bid =  context.getIntent().getStringExtra(ChatMainActivity.TOID);
        final String name=  context.getIntent().getStringExtra(ChatMainActivity.NAME);
        final String head=  context.getIntent().getStringExtra(ChatMainActivity.TOHEAD);
        final Bbs.Bbstype bbstype = (Bbs.Bbstype) context.getIntent().getSerializableExtra(BBSChatMainActivity.BBSTYPE);
        if (bid.contains("bbs_")) {
            bid = bid.substring(bid.indexOf("bbs_")+"bbs_".length());
        }
        RequestParams params = new RequestParams();
        params.put("uid",userInfo.getUid());
        params.put("bid",bid);
        params.put("max",0);
        params.put("showlouzu",0);
        //安全较验
        NetworkUtil.safeDate(params);
        final String finalBid = bid;
        client.post(UrlConstants.BBS_BBSREPLYLIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(data);
                            Log.e("onSuccess","data="+data);
                            chatMainView.init((List<BBSMessage>) parseJsonWithGsonObject(json.getString("data"),new TypeToken<ArrayList<BBSMessage>>() {}.getType())
                                    , finalBid, name, head, bbstype,json.getInt("max"),json.getInt("min"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            UIUtil.showMessage(context,"数据异常");
                        }catch(IllegalStateException e){
                            e.printStackTrace();
                            UIUtil.showMessage(context,"数据异常");
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        //messagetableDao.insert(messageTable);
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
    public void sendMessage(final BBSMessage bbsMessage,Bbs.Bbstype bbstype,String bid,String title,String headsmall){
        if (bbstype == Bbs.Bbstype.INDUSTRY) {
            final MessageTable messageTable = bbsMessage.getMessageTable(userInfo.getUid());
            messageTable.setTo(GsonUtil.objectToJson(bbsMessage.new UserInfo(bid,title,headsmall)));
            messagetableDao.insert(messageTable);
        }
        RequestParams params = new RequestParams();
        params.put("bid", bbsMessage.getBid());
        params.put("nickname", bbsMessage.getNickname());
        params.put("headsmall", bbsMessage.getHeadsmall());
        params.put("typefile", bbsMessage.getTypefile().getValue());
        params.put("content", bbsMessage.getContent());
        if (bbsMessage.getTypefile()== MessageType.PICTURE && bbsMessage.getImage()!=null) {
            if(bbsMessage.getImage().getUrllarge().startsWith("http")){
                params.put("image",bbsMessage.getImage());
            }else{
                try {
                    params.put("pic",new File(bbsMessage.getImage().getUrllarge()));
                    params.put("width", bbsMessage.getImage().getWidth());
                    params.put("height", bbsMessage.getImage().getHeight());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    UIUtil.showMessage(context,"发送图片不存在");
                    return;
                }
            }
        }
        if (bbsMessage.getTypefile()== MessageType.VOICE&& bbsMessage.getVoice()!=null) {
            if(bbsMessage.getVoice().getUrl().startsWith("http")){
                params.put("voice", GsonUtil.objectToJson(bbsMessage.getVoice()));
            }else{
                try {
                    params.put("pic",new File(bbsMessage.getVoice().getUrl()));
                    params.put("voicetime",bbsMessage.getVoice().getTime());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    UIUtil.showMessage(context,"发送语音不存在");
                    return;
                }
            }
        }
        if (bbsMessage.getTypefile()== MessageType.REDPACKET && bbsMessage.getRedpacket()!=null) {
                params.put("redpackettitle",bbsMessage.getRedpacket().getRedpackettitle());
                params.put("redpacketurl",bbsMessage.getRedpacket().getRedpacketurl());
        }
        if (bbsMessage.getTypefile()== MessageType.MAP && bbsMessage.getLocation()!=null) {
            params.put("lat",bbsMessage.getLocation().getLat());
            params.put("lng",bbsMessage.getLocation().getLng());
            params.put("address",bbsMessage.getLocation().getAddress());
        }
        params.put("uid",userInfo.getUid());
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.BBS_BBSREPLYADD, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(data);
                            Log.e("onSuccess","data="+data);
                            ArrayList<BBSMessage> bbsMessages= GsonUtil.parseJsonWithGsonObject(json.getString("data"),new TypeToken<ArrayList<BBSMessage>>() {}.getType());
                            if (bbsMessages.size()>0) {
                                bbsMessage.setId(bbsMessages.get(0).getId());
                                chatMainView.add(bbsMessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            UIUtil.showMessage(context,"数据异常");
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
