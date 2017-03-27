package com.example.administrator.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.R;
import com.example.administrator.databinding.UserInfoBinding;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUUserInfoView;
import com.example.administrator.presenter.UserInfoPresenter;
import com.example.administrator.util.ImageUitl;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.bv.BelowView;

import static com.example.administrator.activity.UserInfoActivity.Write.WRITE_BAIZHU;

/**
 * Created by Administrator on 2017/1/24.
 */

public class UserInfoActivity extends BaseActivity implements IUUserInfoView {
     enum Write{
        WRITE_BAIZHU
    }
    public final static String ID = "id";
    public final static String UID = "uid";
    public final static String KAI6ID = "kai6id";
    public final static String USERINFO = "userinfo";
    UserInfoBinding binding;
    UserInfoActivity context;
    UserInfoPresenter userInfoPresenter;
    UserInfo  mUserinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = UserInfoActivity.this;
        userInfoPresenter = new UserInfoPresenter(context,this);
        ImageUitl.init(context);
        binding =  DataBindingUtil.setContentView(this, R.layout.user_info);
        userInfoPresenter.init();
        blv = new BelowView(context, R.layout.user_operating);
    }
    @Override
    public void init(UserInfo userInfo) {
        mUserinfo  = userInfo;
        binding.setUserinfo(userInfo);
        binding.setBehavior(this);
        binding.titleLayout.setBehavior(this);
        //初始化值
        //设置名字
        if(!StringUtil.isNull(userInfo.getRemark())){
            binding.name.setText(userInfo.getRemark());
            binding.descName.setText("昵称:"+userInfo.getNickname());
        }else{
            binding.name.setText(userInfo.getNickname());
        }
        //设置性别
        if(userInfo.getGender()== UserInfo.SexType.GIRL){
            binding.sexImage.setImageDrawable(getResources().getDrawable(R.mipmap.girl));
        }
        //设置个性签名
        if(!StringUtil.isNull(userInfo.getSign())){
            binding.signLayout.setVisibility(View.VISIBLE);
            binding.signContent.setText(userInfo.getSign());
        }
        //设置关系链
        binding.guanxiContent.setText("我--"+userInfo.getLinkname()+"--"+userInfo.getNickname());
        //设置地区
        binding.addrContent.setText(userInfo.getProvince()+" "+userInfo.getCity());
        //设置商机圈
        if(userInfo.getPicturelist()!=null && userInfo.getPicturelist().length!=0){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    StringUtil.dip2px(context, 60), StringUtil.dip2px(context, 60));
            params.gravity = Gravity.CENTER_VERTICAL;
            int margin = StringUtil.dip2px(context, 5);
            params.setMargins(0,0,margin,0);
            for (String s : userInfo.getPicturelist()) {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(params);
                imageView.setPadding(margin,margin,margin,margin);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageUitl.setImage(imageView,s);
                binding.newPhotoLayout.addView(imageView);
            }
        }
        //这个是设置标题
        binding.titleLayout.titlecontext.setText("详细资料");
        ImageView ivRight = binding.titleLayout.rightBtn;
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.more_btn);
    }

    @Override
    public void right_btn(View view) {
        blv.showBelowView(view, true, 30, 0);
        View v =  blv.getBelowView();
        //设置备注
        v.findViewById(R.id.beizhu_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name",mUserinfo.getRemark());
                intent.putExtra("title",mUserinfo.getRemark());
                intent.putExtra("type",WriteNameActivity.SINGLE);
                intent.putExtra("verify",false);
                intent.setClass(context, WriteNameActivity.class);
                startActivityForResult(intent,WRITE_BAIZHU.ordinal());
                blv.dismissBelowView();
            }
        });
        //发送该名片
        v.findViewById(R.id.send_card_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtil.showMessage(context,"发送该名片");
                blv.dismissBelowView();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            UserInfoActivity.Write write = UserInfoActivity.Write.values()[requestCode];
            switch (write){
                case WRITE_BAIZHU:
                    String name = data.getStringExtra("name");
                    if(StringUtil.isNull(name)){
                        binding.name.setText(mUserinfo.getNickname());
                        binding.descName.setText("");
                    }else{
                        binding.name.setText(data.getStringExtra("name"));
                    }
                    mUserinfo.setRemark(name);
                    break;
            }
        }
    }
}
