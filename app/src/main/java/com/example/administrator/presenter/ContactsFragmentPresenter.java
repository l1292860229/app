package com.example.administrator.presenter;

import android.app.Activity;

import com.example.administrator.dbDao.UserInfoTableDao;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.enumset.GetDataType;
import com.example.administrator.interfaceview.IUContactsFragmentView;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.UIUtil;
import com.google.gson.reflect.TypeToken;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.administrator.enumset.GetDataType.INITDATA;

/**
 * Created by Administrator on 2017/2/28.
 */

public class ContactsFragmentPresenter extends BaseFragmentPresenter {
    private IUContactsFragmentView contactsFragmentView;
    private UserInfoTableDao userInfoTableDao;
    public ContactsFragmentPresenter(Activity context, IUContactsFragmentView contactsFragmentView){
        super(context);
        this.contactsFragmentView = contactsFragmentView;
        userInfoTableDao = new UserInfoTableDao();
    }
    public void getDate(final int page, final int o, final GetDataType getDataType){
        //先查询本地数据库是否缓存
        if(getDataType==INITDATA){
           ArrayList<UserInfo> userInfos =  userInfoTableDao.select(userInfo.getUid(),o);
            if(userInfos!=null&& userInfos.size()>0){
                contactsFragmentView.init(userInfos);
                return;
            }
        }
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
                            ArrayList<UserInfo> mlist = GsonUtil.parseJsonWithGsonObject(json.getString("data"),new TypeToken<ArrayList<UserInfo>>() {}.getType());
                            userInfoTableDao.insert(userInfo.getUid(),o,mlist);
                            mlist = userInfoTableDao.select(userInfo.getUid(),o);
                            switch (getDataType){
                                case INITDATA:
                                    contactsFragmentView.hideLoading();
                                    contactsFragmentView.init(mlist);
                                    break;
                                case LOADDATA:
                                    contactsFragmentView.loadsuccess(mlist);
                                    break;
                                case REFRESHDATA:
                                    contactsFragmentView.refreshsuccess(mlist);
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
