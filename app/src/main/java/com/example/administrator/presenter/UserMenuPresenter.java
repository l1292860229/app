package com.example.administrator.presenter;

import com.example.administrator.activity.UserMenuActivity;
import com.example.administrator.entity.UserMenu;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.interfaceview.IUUserMenuView;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by dell on 2017/4/19.
 */

public class UserMenuPresenter extends BasePresenter  {
    private IUUserMenuView userMenuView;
    public UserMenuPresenter(UserMenuActivity context, IUUserMenuView userMenuView) {
        super(context);
        this.userMenuView = userMenuView;
    }
    public void init(){
        ArrayList<UserMenu>  usermenu =  userInfo.getUsermenulist();
        userMenuView.init(usermenu);
    }
    public void delete(final String id){
        RequestParams params = new RequestParams();
        params.put("uid", userInfo.getUid());
        params.put("openid", userInfo.getPhone());
        params.put("id", id);
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.USER_DELUSERMENU, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        for (int i = userInfo.getUsermenulist().size() - 1; i >= 0; i--) {
                            if (userInfo.getUsermenulist().get(i).getId().equals(id)) {
                                userInfo.getUsermenulist().remove(i);
                                break;
                            }
                        }
                        GetDataUtil.setUserInfo(context,userInfo);
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        UIUtil.showMessage(context,"网络异常");
                    }
        });
    }
}
