package com.example.administrator.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.administrator.R;
import com.example.administrator.entity.Constants;
import com.example.administrator.util.StringUtil;


/**
 * Created by Administrator on 2017/1/21.
 */

public class WelcomeActivity extends AppCompatActivity {
    private final String TAG="WelcomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences mPreferences = WelcomeActivity.this.getSharedPreferences(Constants.USERINFO, 0);
                String userInfo = mPreferences.getString(Constants.USERINFO,"");
                Intent intent;
                //如果没有记住密码就跳到登录页面，否则就进入app
                if(StringUtil.isNull(userInfo)){
                    intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                }else{
                    intent = new Intent(WelcomeActivity.this,MainActivity.class);
                }
                WelcomeActivity.this.startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 2000);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            this.finish();
            System.exit(0);
        }
        return super.dispatchKeyEvent(event);
    }
}
