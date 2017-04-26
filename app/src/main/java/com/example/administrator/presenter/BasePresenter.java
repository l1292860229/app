package com.example.administrator.presenter;

import com.example.administrator.activity.BaseActivity;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.NetworkUtil;
import com.smartandroid.sa.loopj.AsyncHttpClient;

/**
 * Created by Administrator on 2017/3/10.
 */

public class BasePresenter {
    BaseActivity context;
    AsyncHttpClient client;
    static UserInfo userInfo;
    public BasePresenter(BaseActivity context){
        this.context = context;
        this.client = NetworkUtil.instanceAsyncHttpClient();
        if(userInfo==null){
            userInfo = GetDataUtil.getUserInfo(context);
        }
    }
}
