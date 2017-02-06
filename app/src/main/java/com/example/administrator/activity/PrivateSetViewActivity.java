package com.example.administrator.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.R;
import com.example.administrator.databinding.PrivateSetBinding;
import com.example.administrator.interfaceview.IUPrivateSetView;
import com.example.administrator.presenter.PrivateSetPresenter;

/**
 * Created by Administrator on 2017/1/24.
 */

public class PrivateSetViewActivity extends AppCompatActivity implements IUPrivateSetView {
    PrivateSetBinding binding;
    PrivateSetPresenter privateSetPresenter;
    Context context;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = PrivateSetViewActivity.this;
        privateSetPresenter = new PrivateSetPresenter(context,getIntent(),PrivateSetViewActivity.this);
        binding =  DataBindingUtil.setContentView(this, R.layout.private_set);
        binding.setBehavior(this);
        privateSetPresenter.init();
    }

    @Override
    public void init(String title) {
        ((TextView)binding.titleLayout.findViewById(R.id.titlecontext)).setText(title);
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrivateSetViewActivity.this.finish();
            }
        });
    }

    @Override
    public void showPrivacy(boolean addFriendVerify,boolean recommendContactsFriend) {
        init("隐私设置");
        binding.firstLayout.setVisibility(View.VISIBLE);
        binding.recommendContactLayout.setVisibility(View.VISIBLE);
        binding.tglloop.setChecked(addFriendVerify);
        binding.tglRecommendFriend.setChecked(recommendContactsFriend);
    }

    @Override
    public void showNotice(boolean systemSettingNotice,boolean noticeSound,boolean noticeShock) {
        init("新消息通知");
        binding.acceptNewMsgLayout.setVisibility(View.VISIBLE);
        binding.tglAcceptNewMsg.setChecked(systemSettingNotice);
        if(systemSettingNotice){
            binding.voiceLayout.setVisibility(View.VISIBLE);
            binding.shakeLayout.setVisibility(View.VISIBLE);
        }
        binding.tglVoice.setChecked(noticeSound);
        binding.tglShake.setChecked(noticeShock);
    }

    /**
     * 将设置保存
     * @param view
     */
    public void onChecked(View view){
        if(view instanceof ToggleButton){
            ToggleButton tb =  (ToggleButton)view;
            switch (tb.getId()){
                case R.id.tglloop:
                    privateSetPresenter.SetPrivacyAddfriendverify(tb.isChecked());
                    break;
                case R.id.tgl_recommend_friend:
                    privateSetPresenter.SetPrivacyRecommendcontactsfriend(tb.isChecked());
                    break;
                case R.id.tgl_accept_new_msg:
                    privateSetPresenter.SetSystemSettingNotice(tb.isChecked());
                    if(tb.isChecked()){
                        binding.voiceLayout.setVisibility(View.VISIBLE);
                        binding.shakeLayout.setVisibility(View.VISIBLE);
                    }else{
                        binding.voiceLayout.setVisibility(View.GONE);
                        binding.shakeLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.tgl_voice:
                    privateSetPresenter.SetNoticeSound(tb.isChecked());
                    break;
                case R.id.tgl_shake:
                    privateSetPresenter.SetNoticeShock(tb.isChecked());
                    break;
            }
        }
    }
}
