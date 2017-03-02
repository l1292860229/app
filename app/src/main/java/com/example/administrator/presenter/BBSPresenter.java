package com.example.administrator.presenter;

import android.content.Context;
import android.util.Log;

import com.example.administrator.entity.Bbs;
import com.example.administrator.entity.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUBBSView;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
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
 * Created by Administrator on 2017/3/2.
 */

public class BBSPresenter {
    public final static int BBS = 1;
    public final static int INDUSTRY = 2;
    private Context context;
    private IUBBSView bbsView;
    private AsyncHttpClient client = NetworkUtil.instanceAsyncHttpClient();
    private UserInfo userInfo;
    public BBSPresenter(Context context,IUBBSView bbsView){
        this.context = context;
        this.bbsView = bbsView;
        userInfo = GetDataUtil.getUserInfo(context);
    }
    public void init(int type,boolean isprivate){
        RequestParams params = new RequestParams();
        params.put("uid",userInfo.getUid());
        switch (type){
            case BBS:
                params.put("quid", userInfo.getQuid());
                break;
            case INDUSTRY:
                if(isprivate){
                    params.put("quid", userInfo.getPhone());
                }
                break;
        }
        params.put("type", (type-1));
        //安全较验
        NetworkUtil.safeDate(params);
        bbsView.showLoading();
        client.post(UrlConstants.BBS_BBSLIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2).replace("(","").replace(")","");
                        bbsView.hideLoading();
                        Log.e("init","data="+data);
                        try {
                            JSONObject json = new JSONObject(data);
                            bbsView.init(new ArrayList<>(Arrays.asList( GsonUtil.parseJsonWithGson(json.getString("data"),Bbs[].class))));
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"获取失败");
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        bbsView.hideLoading();
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
