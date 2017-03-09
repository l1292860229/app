package com.example.administrator.presenter;

import android.content.Context;

import com.example.administrator.entity.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.enumset.GetDataType;
import com.example.administrator.interfaceview.IUContactsFragmentView;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.loopj.AsyncHttpClient;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.administrator.enumset.GetDataType.INITDATA;

/**
 * Created by Administrator on 2017/2/28.
 */

public class ContactsFragmentPresenter {
    private Context context;
    private IUContactsFragmentView contactsFragmentView;
    private AsyncHttpClient client = NetworkUtil.instanceAsyncHttpClient();
    private UserInfo userInfo;
    public ContactsFragmentPresenter(Context context,IUContactsFragmentView contactsFragmentView){
        this.contactsFragmentView = contactsFragmentView;
        this.context = context;
        userInfo = GetDataUtil.getUserInfo(context);
    }
    public void getDate(int page,int o,final GetDataType getDataType){
        RequestParams params = new RequestParams();
        params.put("id",userInfo.getYpid());
        params.put("uid",userInfo.getUid());
        params.put("ka6_id",userInfo.getKa6id());
        params.put("page",page);
        params.put("o",o);
        //安全较验
        NetworkUtil.safeDate(params);
        if(getDataType==INITDATA){
            contactsFragmentView.showLoading();
        }
        client.post(UrlConstants.FRIEND_FRIENDSLIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2).replace("(","").replace(")","");
                        try {
                            JSONObject json = new JSONObject(data);
                            UserInfo[] mlist = GsonUtil.parseJsonWithGson(json.getString("data"),UserInfo[].class);
                            switch (getDataType){
                                case INITDATA:
                                    contactsFragmentView.hideLoading();
                                    contactsFragmentView.init(new ArrayList<>(Arrays.asList(mlist)));
                                    break;
                                case LOADDATA:
                                    contactsFragmentView.loadsuccess(new ArrayList<>(Arrays.asList(mlist)));
                                    break;
                                case REFRESHDATA:
                                    contactsFragmentView.refreshsuccess(new ArrayList<>(Arrays.asList(mlist)));
                                    break;
                            }
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"获取失败");
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        contactsFragmentView.hideLoading();
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
