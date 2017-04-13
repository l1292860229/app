package com.example.administrator.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.R;
import com.example.administrator.activity.EditProfileActivity;
import com.example.administrator.activity.SettingActivity;
import com.example.administrator.activity.WebViewActivity;
import com.example.administrator.databinding.FragmentProfileBinding;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.ImageUitl;

import static android.app.Activity.RESULT_OK;
import static com.example.administrator.entity.constant.UrlConstants.BAIWAN_EWEIMA;
import static com.example.administrator.entity.constant.UrlConstants.BAIWAN_HUIYUANSHENJI;
import static com.example.administrator.entity.constant.UrlConstants.BAIWAN_QIANBAO;

/**
 * Created by Administrator on 2017/1/22.
 */

public class ProfileFragment extends Fragment  {
    private static final int UPDATEUSERINFO = 1;
    FragmentProfileBinding binding;
    Context context;
    UserInfo userInfo;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        context = ProfileFragment.this.getActivity();
        userInfo = GetDataUtil.getUserInfo(context);
        //图片工具的初始化
        ImageUitl.init(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false);
        init();
        return binding.getRoot();
    }

    public void init() {
        binding.setUsername("人脉号:"+GetDataUtil.getUserName(context));
        binding.setUserinfo(GetDataUtil.getUserInfo(context));
        binding.setBehavior(this);
    }

    /**
     * 打开钱包
     * @param view
     */
    public void openQianBao(View view){
        openWindow(BAIWAN_QIANBAO+"?id="+ userInfo.getYpid()+"&token="+userInfo.getToken());
    }
    /**
     * 打开推广二维码
     * @param view
     */
    public void openTuiGuanEWeiMa(View view){
        openWindow(BAIWAN_EWEIMA+"?id="+ userInfo.getYpid()+"&fromusername="+userInfo.getKa6id());
    }
    /**
     * 打开会员升级
     * @param view
     */
    public void openHuiYuanShenJi(View view){
        openWindow(BAIWAN_HUIYUANSHENJI+"?id="+ userInfo.getYpid()+"&k=%E7%89%B9%E6%9D%83%E4%BC%9A%E5%91%98&token="+userInfo.getToken());
    }
    public void openWindow(String url){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.URL,url);
        startActivity(intent);
    }
    /**
     * 打开修改个人资料页面
     * @param view
     */
    public void openEditProfile(View view){
        Intent intent = new Intent();
        intent.setClass(context, EditProfileActivity.class);
        startActivityForResult(intent,UPDATEUSERINFO);
    }

    /**
     * 打开设置页面
     * @param view
     */
    public void openSetting(View view){
        Intent intent = new Intent();
        intent.setClass(context, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case UPDATEUSERINFO:
                    init();
                    break;
            }
        }
    }
}
