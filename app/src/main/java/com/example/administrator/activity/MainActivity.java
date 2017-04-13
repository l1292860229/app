package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.R;
import com.example.administrator.adapter.FragmentManagerAdapter;
import com.example.administrator.databinding.MainBinding;
import com.example.administrator.fragment.ChatFragment;
import com.example.administrator.fragment.FatherContactsFragment;
import com.example.administrator.fragment.FoundFragment;
import com.example.administrator.fragment.ProfileFragment;
import com.example.administrator.interfaceview.IUMainView;
import com.example.administrator.util.UIUtil;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.tandong.sa.bv.BelowView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements IUMainView {
    Context context;
    MainBinding mainBinding;
    @Titles
    private static final String[] mTitles = {"通讯录","通讯录","通讯录","通讯录"};
    @SeleIcons
    private static final int[] mSeleIcons = {R.mipmap.fx_conversation_selected,R.mipmap.fx_contact_list_selected,R.mipmap.fx_find_pressed,R.mipmap.fx_profile_pressed};
    @NorIcons
    private static final int[] mNormalIcons = {R.mipmap.fx_conversation_normal, R.mipmap.fx_contact_list_normal, R.mipmap.fx_find_normal, R.mipmap.fx_profile_normal};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MainActivity.this;
        mainBinding = DataBindingUtil.setContentView(this, R.layout.main);
        mainBinding.setBehavior(this);
        mainBinding.titleLayout.setBehavior(this);
        blv = new BelowView(MainActivity.this, R.layout.addxml);
        init();
    }

    @Override
    public void init() {
        mainBinding.titleLayout.titlecontext.setText(context.getResources().getString(R.string.app_name));
        mainBinding.titleLayout.leftBtn.setVisibility(View.GONE);
        ImageView mSearchBtn = mainBinding.titleLayout.rightBtn;
        ImageView mAddBtn = mainBinding.titleLayout.rightBtn2;
        mSearchBtn.setImageResource(R.drawable.search_icon_btn);
        mSearchBtn.setVisibility(View.VISIBLE);
        mAddBtn.setImageResource(R.drawable.add_icon_btn);
        mAddBtn.setVisibility(View.VISIBLE);
        mainBinding.tabbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WebViewActivity.class);
                //intent.putExtra(WebViewActivity.URL,"http://www.baidu.com");
                intent.putExtra(WebViewActivity.URL,"http://music.163.com/#/song?id=26270153");
                startActivity(intent);
            }
        });
        List<Fragment> list = new ArrayList<>();
        list.add(new ChatFragment());
        list.add(new FatherContactsFragment());
        list.add(new FoundFragment());
        list.add(new ProfileFragment());
        mainBinding.fragmentContainer.setAdapter(new FragmentManagerAdapter(getSupportFragmentManager(),list));
        mainBinding.tabbar.setContainer(mainBinding.fragmentContainer);
    }
    @Override
    public void right_btn2(View view) {
        blv.showBelowView(view, true, 30, 0);
        View v =  blv.getBelowView();
        //发起群聊
        v.findViewById(R.id.chat_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtil.showMessage(context,"发起群聊");
                blv.dismissBelowView();
            }
        });
        //添加朋友
        v.findViewById(R.id.add_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtil.showMessage(context,"添加朋友");
                blv.dismissBelowView();
            }
        });
        //扫一扫
        v.findViewById(R.id.shao_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, ScanActivity.class);
                startActivity(intent);
                blv.dismissBelowView();
            }
        });
        //意见返馈
        v.findViewById(R.id.feedback_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, FeedBackActivity.class);
                startActivity(intent);
                blv.dismissBelowView();
            }
        });
    }
}
