package com.example.administrator.presenter;

import com.example.administrator.activity.BaseActivity;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.NetworkUtil;
import com.tandong.sa.loopj.AsyncHttpClient;

/**
 * Created by Administrator on 2017/3/10.
 */

public class BasePresenter {
    BaseActivity context;
    AsyncHttpClient client = NetworkUtil.instanceAsyncHttpClient();
    UserInfo userInfo;
    public BasePresenter(BaseActivity context){
        this.context = context;
        userInfo = GetDataUtil.getUserInfo(context);
    }
}
