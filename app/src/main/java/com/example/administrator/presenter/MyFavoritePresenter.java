package com.example.administrator.presenter;

import com.example.administrator.activity.MyFavoriteActivity;
import com.example.administrator.entity.FavoriteItem;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.enumset.GetDataType;
import com.example.administrator.interfaceview.IUMyFavoriteView;
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

import static com.example.administrator.enumset.GetDataType.INITDATA;

/**
 * Created by dell on 2017/4/17.
 */

public class MyFavoritePresenter extends BasePresenter {
    IUMyFavoriteView favoriteView;
    public MyFavoritePresenter(MyFavoriteActivity context, IUMyFavoriteView favoriteView){
        super(context);
        this.favoriteView = favoriteView;
    }
    public void getData(int page,final GetDataType getDataType){
        RequestParams params = new RequestParams();
        params.put("uid", userInfo.getUid());
        params.put("page", page);
        params.put("count", "20");
        //安全较验
        NetworkUtil.safeDate(params);
        if(getDataType==INITDATA){
            favoriteView.showLoading();
        }
        client.post(UrlConstants.USER_FAVORITELIST, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2).replace("(","").replace(")","");
                        favoriteView.hideLoading();
                        try {
                            JSONObject json = new JSONObject(data);
                            ArrayList<FavoriteItem> mlist = GsonUtil.parseJsonWithGsonObject(json.getString("data"),new TypeToken<ArrayList<FavoriteItem>>() {}.getType());
                            if(mlist==null){
                                mlist = new ArrayList<>();
                            }
                            switch (getDataType){
                                case INITDATA:
                                    favoriteView.hideLoading();
                                    favoriteView.init(mlist);
                                    break;
                                case LOADDATA:
                                    favoriteView.loadsuccess(mlist);
                                    break;
                                case REFRESHDATA:
                                    favoriteView.refreshsuccess(mlist);
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
                        favoriteView.hideLoading();
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
