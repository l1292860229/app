package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.databinding.MainBinding;
import com.example.administrator.fragment.ChatFragment;
import com.example.administrator.fragment.FatherContactsFragment;
import com.example.administrator.fragment.FoundFragment;
import com.example.administrator.fragment.ProfileFragment;
import com.example.administrator.interfaceview.IUMainView;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.bv.BelowView;

public class MainActivity extends AppCompatActivity implements IUMainView {
    Context context;
    MainBinding mainBinding;
    Button[] mTabs;
    Fragment[] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MainActivity.this;
        mainBinding = DataBindingUtil.setContentView(this, R.layout.main);
        init();
    }

    @Override
    public void init() {
        ((TextView)mainBinding.titleLayout.findViewById(R.id.titlecontext)).setText(context.getResources().getString(R.string.app_name));
        ImageView mSearchBtn = (ImageView) mainBinding.titleLayout.findViewById(R.id.right_btn);
        ImageView mAddBtn = (ImageView) mainBinding.titleLayout.findViewById(R.id.right_btn2);
        mSearchBtn.setBackground(ContextCompat.getDrawable(context,R.drawable.search_icon_btn));
        mSearchBtn.setVisibility(View.VISIBLE);
        mAddBtn.setBackground(ContextCompat.getDrawable(context,R.drawable.add_icon_btn));
        mAddBtn.setVisibility(View.VISIBLE);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            BelowView blv = new BelowView(MainActivity.this, R.layout.addxml);
            @Override
            public void onClick(View view) {
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
        });
        //初始化底部按钮
        mTabs = new Button[4];
        mTabs[0] = mainBinding.btnConversation;
        mTabs[1] = mainBinding.btnAddressList;
        mTabs[2] = mainBinding.btnFind;
        mTabs[3] = mainBinding.btnProfile;
        mTabs[0].setSelected(true);
        fragments = new Fragment[]{new ChatFragment(), new FatherContactsFragment(), new FoundFragment(), new ProfileFragment()};
        if (!fragments[0].isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragments[0])
                    .show(fragments[0]).commit();
        }
        mainBinding.btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WebViewActivity.class);
                //intent.putExtra(WebViewActivity.URL,"http://www.baidu.com");
                intent.putExtra(WebViewActivity.URL,"http://music.163.com/#/song?id=26270153");

                startActivity(intent);
            }
        });
    }

    /**
     * 底部tab选择项
     */
    private int index,currentTabIndex;
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_conversation:
                index = 0;
                break;
            case R.id.btn_address_list:
                index = 1;
                break;
            case R.id.btn_find:
                index = 2;
                break;
            case R.id.btn_profile:
                index = 3;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }
}
