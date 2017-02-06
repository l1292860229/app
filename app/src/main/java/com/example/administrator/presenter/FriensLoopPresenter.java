package com.example.administrator.presenter;

import android.content.Context;

import com.example.administrator.entity.FriendsLoopItem;
import com.example.administrator.entity.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUFriensLoopView;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.loopj.AsyncHttpClient;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/1/27.
 */

public class FriensLoopPresenter {
    Context context;
    IUFriensLoopView friensLoopView;
    private AsyncHttpClient client = new AsyncHttpClient();
    public FriensLoopPresenter(Context context,IUFriensLoopView friensLoopView){
        this.context = context;
        this.friensLoopView = friensLoopView;
    }
    public void init(String type){
        UserInfo userInfo = GetDataUtil.getUserInfo(context);
        RequestParams params = new RequestParams();
        if(type!=null && !type.equals("")){
            params.put("type", type);
        }
        params.put("uid", userInfo.getUid());
        friensLoopView.showLoading();
        client.post(UrlConstants.FRIEND_SHARELIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        friensLoopView.hideLoading();
                        String data = new String(arg2);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(data);
                            FriendsLoopItem[] mlist = GsonUtil.parseJsonWithGson(json.getString("data"),FriendsLoopItem[].class);
                            friensLoopView.init(new ArrayList<FriendsLoopItem>(Arrays.asList(mlist)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            UIUtil.showMessage(context,"数据异常");
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        friensLoopView.hideLoading();
                        friensLoopView.init(new ArrayList<FriendsLoopItem>());
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
    public void loadData(int page,String type){
        UserInfo userInfo = GetDataUtil.getUserInfo(context);
        RequestParams params = new RequestParams();
        if(type!=null && !type.equals("")){
            params.put("type", type);
        }
        params.put("page", page);
        params.put("uid", userInfo.getUid());
        client.post(UrlConstants.FRIEND_SHARELIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(data);
                            FriendsLoopItem[] mlist = GsonUtil.parseJsonWithGson(json.getString("data"),FriendsLoopItem[].class);
                            friensLoopView.loadsuccess(new ArrayList<FriendsLoopItem>(Arrays.asList(mlist)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
    public void refreshData(String type){
        UserInfo userInfo = GetDataUtil.getUserInfo(context);
        RequestParams params = new RequestParams();
        if(type!=null && !type.equals("")){
            params.put("type", type);
        }
        params.put("uid", userInfo.getUid());
        client.post(UrlConstants.FRIEND_SHARELIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(data);
                            FriendsLoopItem[] mlist = GsonUtil.parseJsonWithGson(json.getString("data"),FriendsLoopItem[].class);
                            friensLoopView.refreshsuccess(Arrays.asList(mlist));
                        } catch (JSONException e) {
                            e.printStackTrace();
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
