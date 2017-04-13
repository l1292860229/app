package com.example.administrator.presenter;

import android.util.Log;

import com.example.administrator.activity.BaseActivity;
import com.example.administrator.activity.UserInfoActivity;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUUserInfoView;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/10.
 */

public class UserInfoPresenter extends BasePresenter {
    private IUUserInfoView userInfoView;
    public UserInfoPresenter(BaseActivity context, IUUserInfoView userInfoView){
        super(context);
        this.userInfoView = userInfoView;
    }
    public void init(){
        String id =  context.getIntent().getStringExtra(UserInfoActivity.ID);
        String uid = context.getIntent().getStringExtra(UserInfoActivity.UID);
        String kai6id = context.getIntent().getStringExtra(UserInfoActivity.KAI6ID);
        final UserInfo oldUserInfo = (UserInfo) context.getIntent().getSerializableExtra(UserInfoActivity.USERINFO);
        if(oldUserInfo!=null){
            uid = oldUserInfo.getUid();
            kai6id = oldUserInfo.getKa6id();
        }
        RequestParams params = new RequestParams();
        if(!StringUtil.isNull(id)){
            params.put("id",id);
            params.put("uid",uid);
            params.put("kai6id",userInfo.getKa6id());
        }else{
            if(!StringUtil.isNull(uid)){
                params.put("uid",uid);
            }
            if(!StringUtil.isNull(kai6id)){
                params.put("kai6id",kai6id);
            }
        }
        params.put("userId", userInfo.getUid());
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.BBS_DETAILFOROTHER, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2);
                        Log.e("init","data="+data);
                        JSONObject json;
                        try {
                            json = new JSONObject(data);
                            UserInfo userInfodata = GsonUtil.parseJsonWithGson(json.getString("data"), UserInfo.class);
                            if(oldUserInfo!=null){
                                userInfodata.setRemark(oldUserInfo.getRemark());
                            }
                            userInfoView.init(userInfodata);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            UIUtil.showMessage(context,"数据异常");
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
