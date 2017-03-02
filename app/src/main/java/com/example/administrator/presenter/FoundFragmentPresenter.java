package com.example.administrator.presenter;

import android.content.Context;

import com.example.administrator.entity.Menu;
import com.example.administrator.entity.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUFoundFragmentView;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.StringUtil;
import com.tandong.sa.loopj.AsyncHttpClient;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.administrator.util.GetDataUtil.getUserInfo;

/**
 * Created by Administrator on 2017/1/25.
 */

public class FoundFragmentPresenter {
    private Context context;
    private IUFoundFragmentView foundFragmentView;
    private AsyncHttpClient client =NetworkUtil.instanceAsyncHttpClient();
    public FoundFragmentPresenter(Context context,IUFoundFragmentView foundFragmentView){
        this.context = context;
        this.foundFragmentView = foundFragmentView;
    }
    public void init(){
        final List<Menu> list = new ArrayList<>();
        list.add(new Menu("商机圈","",""));
        list.add(new Menu("扫一扫","",""));
        list.add(new Menu("附近的店","",""));
        list.add(new Menu("购物","",""));
        list.add(new Menu("游戏","",""));
        foundFragmentView.init(list);
        UserInfo userInfo = getUserInfo(context);
        RequestParams params = new RequestParams();
        params.put("kai6id", userInfo.getKa6id());
        params.put("token", userInfo.getToken());
        params.put("id", userInfo.getYpid());
        //安全较验
        NetworkUtil.safeDate(params);
        client.post(UrlConstants.USER_GETMENU, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        String data = new String(arg2);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(data);
                            if(StringUtil.isNull(json.getString("data"))){
                                return;
                            }
                            Menu[] menus = GsonUtil.parseJsonWithGson(json.getString("data"),Menu[].class);
                            list.addAll(Arrays.asList(menus));
                            foundFragmentView.updateData(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                    }
                });
    }
}
