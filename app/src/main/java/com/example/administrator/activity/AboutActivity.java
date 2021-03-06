package com.example.administrator.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.R;
import com.example.administrator.databinding.AboutBinding;
import com.smartandroid.sa.activity.SmartActivity;
import com.smartandroid.sa.appInfo.AppInfo;

/**
 * Created by Administrator on 2017/1/24.
 */

public class AboutActivity extends SmartActivity {
    private AppInfo appInfo;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutBinding binding =  DataBindingUtil.setContentView(this, R.layout.about);
        appInfo = new AppInfo(this);
        binding.titleLayout.titlecontext.setText("关于我们");
        ImageView leftbtn = binding.titleLayout.leftIcon;
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.this.finish();
            }
        });
        binding.version.setText(getResources().getString(R.string.app_name)+" "+appInfo.getVerName(this)+" "+appInfo.getVerCode(this));
        binding.copyright.setText(" 百万人脉版权所有 深圳酷盈科技有限公司 ");
        binding.websiteHint.setText(" 官方网站 http://www.winchat.com.cn ");
    }
}
