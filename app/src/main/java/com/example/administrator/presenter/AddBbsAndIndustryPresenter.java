package com.example.administrator.presenter;

import com.example.administrator.activity.AddBbsAndIndustryActivity;
import com.example.administrator.entity.Bbs;
import com.example.administrator.entity.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUPublicView;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.loopj.AsyncHttpClient;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2017/3/9.
 */

public class AddBbsAndIndustryPresenter {
    private AddBbsAndIndustryActivity context;
    private IUPublicView iuPublicView;
    private AsyncHttpClient client = NetworkUtil.instanceAsyncHttpClient();
    private UserInfo userInfo;
    public AddBbsAndIndustryPresenter(AddBbsAndIndustryActivity context,IUPublicView iuPublicView){
        this.context = context;
        this.iuPublicView = iuPublicView;
        userInfo= GetDataUtil.getUserInfo(context);
    }
    public void add(String imagepath,String title,String content,String money,final Bbs.Bbstype type){
        RequestParams params = new RequestParams();
        params.put("uid",userInfo.getUid());
        if(!StringUtil.isNull(imagepath)){
            try {
                params.put("pic", new File(imagepath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        params.put("type", type.ordinal());
        if(type== Bbs.Bbstype.INDUSTRY){
            params.put("quid", userInfo.getPhone());
        }else if(type== Bbs.Bbstype.MILLION){
            params.put("quid", userInfo.getQuid());
        }
        params.put("title", title);
        params.put("content", content);
        if(!StringUtil.isNull(money)){
            params.put("money", money);
        }
        params.put("status", 1);
        //安全较验
        NetworkUtil.safeDate(params);
        iuPublicView.showLoading();
        client.post(UrlConstants.BBS_BBSADD, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        if(type== Bbs.Bbstype.INDUSTRY){
                            UIUtil.showMessage(context,"添加成功，等待管理员审核");
                        }else if(type== Bbs.Bbstype.MILLION){
                            UIUtil.showMessage(context,"添加成功");
                        }
                        context.finish();
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        iuPublicView.hideLoading();
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
}
