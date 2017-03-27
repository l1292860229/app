package com.example.administrator.presenter;

import com.example.administrator.activity.BaseActivity;
import com.example.administrator.entity.Room;
import com.example.administrator.entity.UrlConstants;
import com.example.administrator.interfaceview.IUGroupFriensView;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/3/2.
 */

public class GroupFriensPresenter extends BasePresenter {
    private IUGroupFriensView groupFriensView;
    public GroupFriensPresenter(BaseActivity context, IUGroupFriensView groupFriensView){
        super(context);
        this.groupFriensView = groupFriensView;
    }
    public void init(){
        RequestParams params = new RequestParams();
        params.put("uid",userInfo.getUid());
        params.put("openid",userInfo.getPhone());
        //安全较验
        NetworkUtil.safeDate(params);
        groupFriensView.showLoading();
        client.post(UrlConstants.SESSION_SESSIONLIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2).replace("(","").replace(")","");
                        groupFriensView.hideLoading();
                        try {
                            JSONObject json = new JSONObject(data);
                            groupFriensView.init(new ArrayList<>(Arrays.asList( GsonUtil.parseJsonWithGson(json.getString("data"),Room[].class))));
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"获取失败");
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        groupFriensView.hideLoading();
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}