package com.example.administrator.presenter;

import com.example.administrator.activity.BaseActivity;
import com.example.administrator.entity.Bbs;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.interfaceview.IUBBSView;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.google.gson.reflect.TypeToken;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/2.
 */

public class BBSPresenter extends BasePresenter {
    private IUBBSView bbsView;
    public BBSPresenter(BaseActivity context, IUBBSView bbsView){
        super(context);
        this.bbsView = bbsView;
    }
    public void init(){
        RequestParams params = new RequestParams();
        params.put("uid",userInfo.getUid());
        params.put("quid", userInfo.getQuid());
        params.put("type", Bbs.Bbstype.MILLION.ordinal());
        //安全较验
        NetworkUtil.safeDate(params);
        bbsView.showLoading();
        client.post(UrlConstants.BBS_BBSLIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2).replace("(","").replace(")","");
                        bbsView.hideLoading();
                        try {
                            JSONObject json = new JSONObject(data);
                            bbsView.init(GsonUtil.<ArrayList<Bbs>>parseJsonWithGsonObject(json.getString("data") ,new TypeToken<ArrayList<Bbs>>(){}.getType()));
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"数据异常");
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
