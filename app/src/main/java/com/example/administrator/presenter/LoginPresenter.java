package com.example.administrator.presenter;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.administrator.activity.LoginActivity;
import com.example.administrator.activity.MainActivity;
import com.example.administrator.entity.Constants;
import com.example.administrator.entity.UrlConstants;
import com.example.administrator.interfaceview.IUloginView;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.loopj.AsyncHttpClient;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/1/21.
 */

public class LoginPresenter {
    private IUloginView loginView;
    private LoginActivity context;
    private AsyncHttpClient client = NetworkUtil.instanceAsyncHttpClient();
    public LoginPresenter(LoginActivity context,IUloginView loginView){
        this.loginView = loginView;
        this.context = context;
    }
    public void init(){
        SharedPreferences mPreferences = context.getSharedPreferences(Constants.REMEMBER_USERNAME_PASSWORD, 0);
        String username = mPreferences.getString(Constants.USERNAME,"");
        String password = mPreferences.getString(Constants.PASSWORD,"");
        loginView.init(username,password);
    }
    public void login(){
        final String username = loginView.getUserName();
        final String password = loginView.getPassword();
        boolean isnull = UIUtil.isNullMessage(context,username,"请输入手机号/人脉号");
        isnull = isnull&&UIUtil.isNullMessage(context,username,"请输入密码");
        if (isnull) {
            return;
        }
        RequestParams params = new RequestParams();
        params.put("phone", username);
        params.put("password", password);
        //安全较验
        NetworkUtil.safeDate(params);
        loginView.showLoading();
        client.post(UrlConstants.USER_LOGIN, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2).replace("(","").replace(")","");
                        loginView.hideLoading();
                        try {
                            JSONObject json = new JSONObject(data);
                            JSONObject state = json.getJSONObject("state");
                            //如果code==1说明登录失败,输出错误信息
                            if("1".equals(state.getString("code"))){
                                UIUtil.showMessage(context,state.getString("msg"));
                            }else{
                                //保存用户信息
                                GetDataUtil.setUserInfo(context,json.getString("data"));
                                //保存用户用户名和密码
                                SharedPreferences userPreferences = context.getSharedPreferences(Constants.REMEMBER_USERNAME_PASSWORD, 0);
                                SharedPreferences.Editor userEditor = userPreferences.edit();
                                userEditor.putString(Constants.USERNAME, username);
                                userEditor.putString(Constants.PASSWORD, password);
                                userEditor.commit();
                                //跳转到主页面
                                Intent intent = new Intent();
                                intent.setClass(context, MainActivity.class);
                                context.startActivity(intent);
                                context.finish();
                            }
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"登录失败");
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        loginView.hideLoading();
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
