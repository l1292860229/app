package com.example.administrator.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.activity.FriensLoopActivity;
import com.example.administrator.entity.CommentUser;
import com.example.administrator.entity.FriendsLoopItem;
import com.example.administrator.entity.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.enumset.GetDataType;
import com.example.administrator.interfaceview.IUFriensLoopView;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.loopj.AsyncHttpClient;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.administrator.enumset.GetDataType.INITDATA;

/**
 * Created by Administrator on 2017/1/27.
 */

public class FriensLoopPresenter {
    Context context;
    IUFriensLoopView friensLoopView;
    private AsyncHttpClient client = NetworkUtil.instanceAsyncHttpClient();
    UserInfo userInfo;
    public FriensLoopPresenter(Context context,IUFriensLoopView friensLoopView){
        this.context = context;
        this.friensLoopView = friensLoopView;
        userInfo = GetDataUtil.getUserInfo(context);
    }
    public void getData(String type, final GetDataType getDataType, int page){
        RequestParams params = new RequestParams();
        if(!StringUtil.isNull(type)){
            params.put("type", type);
        }
        params.put("page", page);
        params.put("uid", userInfo.getUid());
        //安全较验
        NetworkUtil.safeDate(params);
        if(getDataType==INITDATA){
            friensLoopView.showLoading();
        }
        client.post(UrlConstants.FRIEND_SHARELIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(data);
                            FriendsLoopItem[] mlist = GsonUtil.parseJsonWithGson(json.getString("data"),FriendsLoopItem[].class);
                           switch (getDataType){
                               case INITDATA:
                                   friensLoopView.hideLoading();
                                   friensLoopView.init(new ArrayList<>(Arrays.asList(mlist)));
                                   break;
                               case LOADDATA:
                                   friensLoopView.loadsuccess(new ArrayList<>(Arrays.asList(mlist)));
                                   break;
                               case REFRESHDATA:
                                   friensLoopView.refreshsuccess(new ArrayList<>(Arrays.asList(mlist)));
                                   break;
                           }
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
    public void setImageBg(final String imagePath){
        RequestParams params = new RequestParams();
        if(!StringUtil.isNull(imagePath)){
            try {
                params.put("pic", new File(imagePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        params.put("uid", userInfo.getUid());
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.FRIEND_SETCOVER, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(data);
                            json = json.getJSONObject("data");
                            userInfo.setCover(json.getString("cover"));
                            GetDataUtil.setUserInfo(context,userInfo);
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
    public void sentReply(final ArrayList<FriendsLoopItem> dataList, final int position
            ,final String toUid,final  String toName, final String content){
        RequestParams params = new RequestParams();
        params.put("uid", userInfo.getUid());
        params.put("content", content);
        if(!StringUtil.isNull(toUid)){
            params.put("fuid", toUid);
        }
        params.put("fsid", dataList.get(position).getId());
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.FRIEND_SHAREREPLY, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        ArrayList<CommentUser> commentUsers = new ArrayList<CommentUser>(Arrays.asList(dataList.get(position).getReplylist()));
                        commentUsers.add(new CommentUser(userInfo.getUid(),userInfo.getNickname(),
                                toUid,toName,content));
                        CommentUser[] tempCommentUser = new CommentUser[commentUsers.size()];
                        commentUsers.toArray(tempCommentUser);
                        dataList.get(position).setReplylist(tempCommentUser);
                        friensLoopView.refreshsuccess(dataList);
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
    public void setSharePraise(final ArrayList<FriendsLoopItem> dataList, final int position
            ,final String toUid,final  String toName,final boolean isZan){
        RequestParams params = new RequestParams();
        params.put("uid", userInfo.getUid());
        params.put("fsid", dataList.get(position).getId());
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.FRIEND_SHAREPRAISE, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        ArrayList<CommentUser> commentUsers = new ArrayList<CommentUser>(Arrays.asList(dataList.get(position).getPraiselist()));
                        //如果赞过就取消赞，否则就添加赞
                        if(isZan){
                            commentUsers.add(new CommentUser(userInfo.getUid(),userInfo.getNickname(),
                                    toUid,toName,null));
                            dataList.get(position).setIspraise(1);
                        }else{
                            for (int i = commentUsers.size() - 1; i >= 0; i--) {
                                if (commentUsers.get(i).getUid().equals(userInfo.getUid())) {
                                    commentUsers.remove(i);
                                    break;
                                }
                            }
                            dataList.get(position).setIspraise(0);
                        }
                        CommentUser[] tempCommentUser = new CommentUser[commentUsers.size()];
                        commentUsers.toArray(tempCommentUser);
                        dataList.get(position).setPraiselist(tempCommentUser);
                        friensLoopView.refreshsuccess(dataList);
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
    public void delData(int fsid){
        RequestParams params = new RequestParams();
        params.put("uid", userInfo.getUid());
        params.put("fsid", fsid);
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.FRIEND_SHAREPRAISE_DEL, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        context.sendBroadcast(new Intent(FriensLoopActivity.REFRESH_FRIENSLOOP_DATA));
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
