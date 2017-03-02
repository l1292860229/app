package com.example.administrator.presenter;

import android.content.Intent;

import com.example.administrator.activity.FriensLoopActivity;
import com.example.administrator.activity.SendFriensLoopActivity;
import com.example.administrator.entity.Picture;
import com.example.administrator.entity.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUSendFriensLoopView;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.loopj.AsyncHttpClient;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Administrator on 2017/2/18.
 */

public class SendFriensLoopPresenter {
    private SendFriensLoopActivity context;
    private AsyncHttpClient client = NetworkUtil.instanceAsyncHttpClient();
    private IUSendFriensLoopView sendFriensLoopView;
    UserInfo userInfo;
    public SendFriensLoopPresenter(SendFriensLoopActivity context,IUSendFriensLoopView sendFriensLoopView){
        this.context = context;
        this.sendFriensLoopView = sendFriensLoopView;
        userInfo = GetDataUtil.getUserInfo(context);
    }
    public void sendFriensLoop(String content,String lng,String lat,String address,
    String type,String bid,String isShow,List<Picture> pictureList){
        RequestParams params = new RequestParams();
        params.put("uid", userInfo.getUid());
        if(pictureList.size()>0){
            try {
                for (Picture picture : pictureList) {
                    params.put("pic", new File(picture.getSmallUrl()));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        params.put("content", content);
        params.put("lng", lng);
        params.put("lat", lat);
        params.put("address", address);
        params.put("type",type);
        params.put("bid", bid);
        params.put("isshow", isShow);
        //安全较验
        NetworkUtil.safeDate(params);
        sendFriensLoopView.showLoading();
        client.post(UrlConstants.FRIEND_SHAREPRAISE_ADD, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        sendFriensLoopView.hideLoading();
                        context.finish();
                        //通知朋友圈进行刷新
                        context.sendBroadcast(new Intent(FriensLoopActivity.REFRESH_FRIENSLOOP_DATA));
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        sendFriensLoopView.hideLoading();
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
