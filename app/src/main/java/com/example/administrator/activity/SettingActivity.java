package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.R;
import com.example.administrator.databinding.SettingBinding;
import com.example.administrator.entity.Constants;
import com.example.administrator.entity.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.util.GetDataUtil;

/**
 * Created by Administrator on 2017/1/24.
 */

public class SettingActivity extends BaseActivity {
    SettingBinding binding;
    Context context;
    UserInfo userInfo;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SettingActivity.this;
        userInfo = GetDataUtil.getUserInfo(context);
        binding =  DataBindingUtil.setContentView(this, R.layout.setting);
        binding.setBehavior(this);
        binding.titleLayout.setBehavior(this);
        binding.titleLayout.titlecontext.setText("设置");
    }
    /**
     * 打开修改密码
     * @param view
     */
    public void openUpdatePassword(View view){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.URL, UrlConstants.BAIWAN_UPDATEPASSWORD+"?id="+
                userInfo.getYpid()+"&tid="+userInfo.getKa6id()+"&m=ptbbs&token="+userInfo.getToken());
        startActivity(intent);
    }
    /**
     * 打开关于我们的界面
     * @param view
     */
    public void openAbout(View view){
        Intent intent = new Intent();
        intent.setClass(context, AboutActivity.class);
        startActivity(intent);
    }

    /**
     * 退出登录
     * @param view
     */
    public void loginout(View view){
        GetDataUtil.removeIsRemember(context);
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * 打开新消息通知
     * @param view
     */
    public void openPrivateSetNotice(View view){
        Intent intent = new Intent();
        intent.putExtra(Constants.SYSTEM_SETTING,Constants.SYSTEM_SETTING_NOTICE);
        intent.setClass(context, PrivateSetViewActivity.class);
        startActivity(intent);
    }
    /**
     * 打开隐私
     * @param view
     */
    public void openPrivateSetPrivacy(View view){
        Intent intent = new Intent();
        intent.putExtra(Constants.SYSTEM_SETTING,Constants.SYSTEM_SETTING_PRIVACY);
        intent.setClass(context, PrivateSetViewActivity.class);
        startActivity(intent);
    }

    /**
     * 打开意见反馈
     * @param view
     */
    public void openFeedBack(View view){
        Intent intent = new Intent();
        intent.setClass(context, FeedBackActivity.class);
        startActivity(intent);
    }
}
