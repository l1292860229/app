package com.example.administrator.presenter;

import android.content.Intent;

import com.example.administrator.activity.IMChatMainActivity;
import com.example.administrator.activity.ChooseUserActivity;
import com.example.administrator.activity.GroupFriensActivity;
import com.example.administrator.dbDao.UserInfoTableDao;
import com.example.administrator.entity.Group;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.enumset.TypeChat;
import com.example.administrator.interfaceview.IUChooseUserView;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.smartandroid.sa.loopj.AsyncHttpResponseHandler;
import com.smartandroid.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/4/25.
 */

public class ChooseUserPresenter extends BasePresenter {
    IUChooseUserView iuChooseUserView;
    UserInfoTableDao userInfoTableDao;
    public ChooseUserPresenter(ChooseUserActivity context, IUChooseUserView iuChooseUserView) {
        super(context);
        this.iuChooseUserView = iuChooseUserView;
        userInfoTableDao = new UserInfoTableDao();
    }
    public void init(){
        boolean isMultiple =  context.getIntent().getBooleanExtra(ChooseUserActivity.MULTIPLE,false);
        ArrayList<UserInfo> userInfos =  userInfoTableDao.select(userInfo.getUid());
        //利用map的特性去重
        Map<String,UserInfo> userInfoMap = new HashMap<>();
        for (UserInfo info : userInfos) {
            userInfoMap.put(info.getPhone(),info);
        }
        userInfos.clear();
        userInfos.addAll(userInfoMap.values());
        iuChooseUserView.init(userInfos,isMultiple);
    }
    public void createGroup(ArrayList<UserInfo> userInfos){
        StringBuffer name=new StringBuffer();
        StringBuffer uids=new StringBuffer();
        for (UserInfo info : userInfos) {
            name.append(info.getNickname()).append(",");
            uids.append(info.getPhone()).append(",");
        }
        name.delete(name.lastIndexOf(","),name.length());
        uids.delete(uids.lastIndexOf(","),uids.length());
        RequestParams params = new RequestParams();
        params.put("uid",userInfo.getUid());
        params.put("name",name.toString());
        params.put("uids",uids.toString());
        params.put("openid",userInfo.getPhone());
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.SESSION_ADD, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2).replace("(","").replace(")","");
                        try {
                            JSONObject json = new JSONObject(data);
                            Group group = GsonUtil.parseJsonWithGson(json.getString("data"),Group.class);
                            Intent intent = new Intent(context, IMChatMainActivity.class);
                            intent.putExtra(IMChatMainActivity.NAME, group.getName());
                            intent.putExtra(IMChatMainActivity.TOID, group.getId());
                            StringBuffer headsmall=new StringBuffer();
                            ArrayList<UserInfo> userinfos = group.getList();
                            for (int i = 0; (i < userinfos.size() && i<9); i++) {
                                headsmall.append(userinfos.get(i).getHeadsmall()).append(",");
                            }
                            if(headsmall.length()>0){
                                headsmall.delete(headsmall.lastIndexOf(","),headsmall.length());
                            }
                            intent.putExtra(IMChatMainActivity.TOHEAD,headsmall.toString());
                            intent.putExtra(IMChatMainActivity.TYPECHAT, TypeChat.GROUP);
                            context.startActivity(intent);
                            context.finish();
                            context.sendBroadcast(new Intent(GroupFriensActivity.REFRESH_GROUPFRIENS_DATA));
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"获取失败");
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
