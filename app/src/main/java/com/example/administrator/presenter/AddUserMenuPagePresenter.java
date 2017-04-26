package com.example.administrator.presenter;

import com.example.administrator.activity.AddUserMenuPageAcitivty;
import com.example.administrator.activity.BaseActivity;
import com.example.administrator.entity.UserMenu;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.interfaceview.IUAddUserMenuPageView;
import com.example.administrator.util.GetDataUtil;
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

/**
 * Created by dell on 2017/4/19.
 */

public class AddUserMenuPagePresenter extends  BasePresenter {
    private IUAddUserMenuPageView addUserMenuPageView;
    public AddUserMenuPagePresenter(BaseActivity context,IUAddUserMenuPageView addUserMenuPageView) {
        super(context);
        this.addUserMenuPageView = addUserMenuPageView;
    }
    public void init(){
        UserMenu userMenu = (UserMenu) context.getIntent().getSerializableExtra(AddUserMenuPageAcitivty.DATAKEY);
        addUserMenuPageView.init(userMenu);
    }
    public void add(String menuname,String menuurl){
        RequestParams params = new RequestParams();
        params.put("uid", userInfo.getUid());
        params.put("openid", userInfo.getPhone());
        params.put("menuname", menuname);
        params.put("menuurl", menuurl);
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.USER_ADDUSERMENU, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2).replace("(","").replace(")","");
                        try {
                            JSONObject json = new JSONObject(data);
                            ArrayList<UserMenu> userMenulist = GsonUtil.parseJsonWithGsonObject(json.getString("data"),new TypeToken<ArrayList<UserMenu>>() {}.getType()) ;
                            userInfo.setUsermenulist(userMenulist);
                            GetDataUtil.setUserInfo(context,userInfo);
                            context.setResult(context.RESULT_OK);
                            context.finish();
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"获取失败");
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
    public void update(final UserMenu userMenu){
        RequestParams params = new RequestParams();
        params.put("uid", userInfo.getUid());
        params.put("openid", userInfo.getPhone());
        params.put("id", userMenu.getId());
        params.put("menuname", userMenu.getMenuname());
        params.put("menuurl", userMenu.getMenuurl());
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.USER_UPDATEUSERMENU, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2).replace("(","").replace(")","");
                        try {
                            JSONObject json = new JSONObject(data);
                            JSONObject jsonObject =  json.getJSONObject("state");
                            if(!jsonObject.isNull("code")){
                                int code = jsonObject.getInt("code");
                                if(code==0){
                                    for (int i = 0; i < userInfo.getUsermenulist().size(); i++) {
                                        if (userInfo.getUsermenulist().get(i).getId().equals(userMenu.getId())) {
                                            userInfo.getUsermenulist().set(i,userMenu);
                                            GetDataUtil.setUserInfo(context,userInfo);
                                            break;
                                        }
                                    }
                                    context.setResult(context.RESULT_OK);
                                    context.finish();
                                    return;
                                }
                            }
                            UIUtil.showMessage(context,"修改失败");
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"获取失败");
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
