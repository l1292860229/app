package com.example.administrator.presenter;

import com.example.administrator.activity.RegisterActivity;
import com.example.administrator.entity.constant.Constants;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.interfaceview.IUPublicView;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.StringUtil;
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

public class RegisterPresenter {
    private RegisterActivity context;
    private AsyncHttpClient client = NetworkUtil.instanceAsyncHttpClient();
    private IUPublicView publicView;
    public RegisterPresenter(RegisterActivity context,IUPublicView publicView){
        this.context = context;
        this.publicView = publicView;
    }
    public void register(String nickname,String tjr,String phone,String password){
        //这里是做较验
        boolean isnull = UIUtil.isNullMessage(context,nickname,"请输入昵称");
        isnull = UIUtil.isNullMessage(context,tjr,"请输入推荐人ID")&&isnull;
        isnull = UIUtil.isNullMessage(context,phone,"请输入手机号")&&isnull;
        isnull = UIUtil.isNullMessage(context,password,"请输入密码")&&isnull;
        if(!StringUtil.isMobileNum(phone)){
            UIUtil.showMessage(context,"请输入正确的手机号码");
            isnull = true;
        }
        if (isnull) {
            return;
        }
        //判断网络是否可用
        if(!NetworkUtil.verifyNetwork(context)){
            UIUtil.showMessage(context,"请检查网络");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("username", phone);
        params.put("name", nickname);
        params.put("tjr", tjr);
        params.put("password", password);
        params.add("id", Constants.ID);
        params.put("name", nickname);
        //安全较验
        NetworkUtil.safeDate(params);
        publicView.showLoading();
        client.post(UrlConstants.USER_REGIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        publicView.hideLoading();
                        String data = new String(arg2).replace("(","").replace(")","");
                        try {
                            JSONObject json = new JSONObject(data);
                            String success = json.getString("success");
                            UIUtil.showMessage(context,json.getString("msg"));
                            if(success.equals("yes")){
                                context.finish();
                            }
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"注册失败");
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        publicView.hideLoading();
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
