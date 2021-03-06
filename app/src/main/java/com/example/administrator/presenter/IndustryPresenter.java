package com.example.administrator.presenter;

import android.util.Log;

import com.example.administrator.activity.BaseActivity;
import com.example.administrator.entity.Bbs;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.enumset.GetDataType;
import com.example.administrator.interfaceview.IUIndustryView;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.google.gson.reflect.TypeToken;
import com.smartandroid.sa.loopj.AsyncHttpResponseHandler;
import com.smartandroid.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.administrator.enumset.GetDataType.INITDATA;

/**
 * Created by Administrator on 2017/3/2.
 */

public class IndustryPresenter extends BasePresenter {
    private IUIndustryView industryView;
    public IndustryPresenter(BaseActivity context, IUIndustryView industryView){
        super(context);
        this.industryView = industryView;
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
                        try {
                            JSONObject json = new JSONObject(data);
                            ArrayList<Bbs> mlist = GsonUtil.parseJsonWithGsonObject(json.getString("data") ,new TypeToken<ArrayList<Bbs>>(){}.getType());
                            if(mlist==null){
                                mlist = new ArrayList<>();
                            }
                            switch (dataType){
                                case INITDATA:
                                    industryView.hideLoading();
                                    industryView.init(mlist);
                                    break;
                                case REFRESHDATA:
                                    industryView.refreshsuccess(mlist);
                                    break;
                                case LOADDATA:
                                    industryView.loadsuccess(mlist);
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
