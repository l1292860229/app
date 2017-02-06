package com.example.administrator.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.adapter.FriensLoopAdapter;
import com.example.administrator.databinding.FriendsLoopHeaderBinding;
import com.example.administrator.databinding.FriensLoopBinding;
import com.example.administrator.entity.FriendsLoopItem;
import com.example.administrator.interfaceview.IUFriensLoopView;
import com.example.administrator.presenter.FriensLoopPresenter;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.ImageUitl;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.bv.BelowView;
import com.tandong.sa.view.AutoReFreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/25.
 */

public class FriensLoopActivity extends AppCompatActivity implements IUFriensLoopView {
    private AutoReFreshListView mListView;
    FriensLoopAdapter friensLoopAdapter;
    FriensLoopBinding binding;
    FriensLoopPresenter friensLoopPresenter;
    Context context;
    String type;
    private Button[] mTabs;
    private int page=1;
    private ArrayList<FriendsLoopItem> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = FriensLoopActivity.this;
        ImageUitl.init(context);
        friensLoopPresenter = new FriensLoopPresenter(context,this);
        binding =  DataBindingUtil.setContentView(this,R.layout.friens_loop);
        mListView = binding.mListView;
        initView();
        friensLoopPresenter.init(null);
    }
    public void init(ArrayList<FriendsLoopItem> mlist) {
        friensLoopAdapter = new FriensLoopAdapter(context,mlist);
        dataList = mlist;
        mListView.setAdapter(friensLoopAdapter);
        mListView.setOnRefreshListener(new AutoReFreshListView.OnRefreshListener() {// 上拉刷新
            public void onRefresh() {
                friensLoopPresenter.refreshData(type);
            }
        });
        mListView.setOnLoadListener(new AutoReFreshListView.OnLoadMoreListener() {// 下拉加载更多
            @Override
            public void onLoadMore() {
                friensLoopPresenter.loadData(++page,type);
            }
        });
    }

    @Override
    public void initHeader() {
        FriendsLoopHeaderBinding friendsLoopHeaderBinding =  DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.friends_loop_header,null,false);
        friendsLoopHeaderBinding.setBehavior(this);
        friendsLoopHeaderBinding.setUserinfo(GetDataUtil.getUserInfo(context));
        mListView.addHeaderView(friendsLoopHeaderBinding.getRoot());
    }

    @Override
    public void loadsuccess(ArrayList<FriendsLoopItem> mlist) {
        mListView.onLoadMoreComplete();
        dataList.addAll(mlist);
        friensLoopAdapter.setData(dataList);
        friensLoopAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshsuccess(List<FriendsLoopItem> mlist) {
        mListView.onRefreshComplete();
        friensLoopAdapter.setData(mlist);
        friensLoopAdapter.notifyDataSetChanged();
    }

    private void initView(){
        ((TextView)binding.titleLayout.findViewById(R.id.titlecontext)).setText("商机圈");
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriensLoopActivity.this.finish();
            }
        });
        TextView tvRight = ((TextView)binding.titleLayout.findViewById(R.id.right_text));
        tvRight.setText("商机发布");
        tvRight.setOnClickListener(new View.OnClickListener() {
            BelowView blv = new BelowView(FriensLoopActivity.this, R.layout.send_shanji);
            @Override
            public void onClick(View view) {
                blv.showBelowView(view, true, 30, 0);
                Log.e("onClick","BelowView blvonClick");
            }
        });
        mTabs = new Button[5];
        mTabs[0] = binding.live;
        mTabs[1] = binding.qiye;
        mTabs[2] = binding.btnZhuye;
        mTabs[3] = binding.weishan;
        mTabs[4] = binding.huodong;
        mTabs[2].setSelected(true);
        initHeader();
    }
    private int index,currentTabIndex=2;
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.live:
                index = 0;
                type="生活";
                break;
            case R.id.qiye:
                index = 1;
                type="企业";
                break;
            case R.id.btn_zhuye:
                index = 2;
                type="";
                break;
            case R.id.weishan:
                index = 3;
                type="微商";
                break;
            case R.id.huodong:
                index = 4;
                type="活动";
                break;
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
        friensLoopPresenter.init(type);
    }
    @Override
    public void showLoading() {
        UIUtil.showLoading(this,"加载中...");
    }
    @Override
    public void hideLoading() {
        UIUtil.hideLoading(this);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return super.dispatchKeyEvent(event);
    }
}
