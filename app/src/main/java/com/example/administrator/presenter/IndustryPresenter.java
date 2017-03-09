package com.example.administrator.presenter;

import android.content.Context;
import android.util.Log;

import com.example.administrator.entity.Bbs;
import com.example.administrator.entity.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.enumset.GetDataType;
import com.example.administrator.interfaceview.IUIndustryView;
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

import static com.example.administrator.enumset.GetDataType.INITDATA;

/**
 * Created by Administrator on 2017/3/2.
 */

public class IndustryPresenter {
    private Context context;
    private IUIndustryView industryView;
    private AsyncHttpClient client = NetworkUtil.instanceAsyncHttpClient();
    private UserInfo userInfo;
    public IndustryPresenter(Context context, IUIndustryView industryView){
        this.context = context;
        this.industryView = industryView;
        userInfo = GetDataUtil.getUserInfo(context);
    }
    public void getData(boolean isprivate, int page,final GetDataType dataType){
        RequestParams params = new RequestParams();
        params.put("uid",userInfo.getUid());
        if(isprivate){
            params.put("quid", userInfo.getPhone());
        }
        params.put("type", Bbs.Bbstype.INDUSTRY.ordinal());
        params.put("page", page);
        //安全较验
        NetworkUtil.safeDate(params);
        if(dataType==INITDATA){
            industryView.showLoading();
        }
        client.post(UrlConstants.BBS_BBSLIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2).replace("(","").replace(")","");
                        Log.e("init","data="+data);
                        try {
                            JSONObject json = new JSONObject(data);
                            switch (dataType){
                                case INITDATA:
                                    industryView.hideLoading();
                                    industryView.init(new ArrayList<>(Arrays.asList( GsonUtil.parseJsonWithGson(json.getString("data"),Bbs[].class))));
                                    break;
                                case REFRESHDATA:
                                    industryView.refreshsuccess(new ArrayList<>(Arrays.asList( GsonUtil.parseJsonWithGson(json.getString("data"),Bbs[].class))));
                                    break;
                                case LOADDATA:
                                    industryView.loadsuccess(new ArrayList<>(Arrays.asList( GsonUtil.parseJsonWithGson(json.getString("data"),Bbs[].class))));
                                    break;
                            }
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"获取失败");
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        industryView.hideLoading();
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
