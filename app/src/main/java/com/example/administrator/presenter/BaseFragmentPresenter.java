package com.example.administrator.presenter;

import android.app.Activity;

import com.example.administrator.entity.UserInfo;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.NetworkUtil;
import com.tandong.sa.loopj.AsyncHttpClient;

/**
 * Created by dell on 2017/4/21.
 */

public class BaseFragmentPresenter {
    Activity context;
    AsyncHttpClient client;
    static UserInfo userInfo;
    public BaseFragmentPresenter(Activity activity){
        this.context = activity;
        this.client = NetworkUtil.instanceAsyncHttpClient();
        if(userInfo==null){
            userInfo = GetDataUtil.getUserInfo(context);
        }
    }
}
