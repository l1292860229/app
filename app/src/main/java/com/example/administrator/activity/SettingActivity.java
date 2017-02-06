package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.databinding.SettingBinding;
import com.example.administrator.entity.Constants;
import com.example.administrator.util.GetDataUtil;

/**
 * Created by Administrator on 2017/1/24.
 */

public class SettingActivity extends AppCompatActivity {
    SettingBinding binding;
    Context context;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SettingActivity.this;
        binding =  DataBindingUtil.setContentView(this, R.layout.setting);
        binding.setBehavior(this);
        ((TextView)binding.titleLayout.findViewById(R.id.titlecontext)).setText("设置");
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
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
