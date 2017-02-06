package com.example.administrator.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.databinding.LoginBinding;
import com.example.administrator.entity.User;
import com.example.administrator.interfaceview.IUloginView;
import com.example.administrator.presenter.LoginPresenter;
import com.example.administrator.util.UIUtil;

/**
 * Created by Administrator on 2017/1/21.
 */

public class LoginActivity extends AppCompatActivity implements IUloginView {
    LoginBinding loginBinding;
    LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding =  DataBindingUtil.setContentView(this, R.layout.login);
        loginBinding.setBehavior(LoginActivity.this);
        loginPresenter = new LoginPresenter(LoginActivity.this,LoginActivity.this);
        loginPresenter.init();
    }

    @Override
    public void init(String username,String password) {
        User user  = new User(username,password);
        loginBinding.setUser(user);
        ((TextView)loginBinding.titleLayout.findViewById(R.id.titlecontext)).setText("登录");
    }
    @Override
    public String getUserName() {
        return loginBinding.username.getText().toString();
    }
    @Override
    public String getPassword() {
        return loginBinding.password.getText().toString();
    }
    @Override
    public void showLoading() {
        UIUtil.showLoading(this,"正在登录,请稍候");
    }
    @Override
    public void hideLoading() {
        UIUtil.hideLoading(this);
    }

    /**
     * 进行登录
     */
    public void login(View view){
        loginPresenter.login();
    }

    /**
     * 进行跳转到注册界面
     * @param view
     */
    public void register(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
