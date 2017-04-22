package com.example.administrator.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.administrator.R;
import com.example.administrator.adapter.MyFavoriteAdapter;
import com.example.administrator.databinding.MyfavoriteBinding;
import com.example.administrator.entity.FavoriteItem;
import com.example.administrator.enumset.GetDataType;
import com.example.administrator.interfaceview.IUMyFavoriteView;
import com.example.administrator.presenter.MyFavoritePresenter;
import com.tandong.sa.view.AutoReFreshListView;

import java.util.ArrayList;


/**
 * Created by dell on 2017/4/17.
 */

public class MyFavoriteActivity extends BaseActivity implements IUMyFavoriteView {
    MyfavoriteBinding binding;
    MyFavoritePresenter presenter;
    Context context;
    ArrayList<FavoriteItem> mList = new ArrayList<>();
    private int page=1;
    private MyFavoriteAdapter myFavoriteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding =  DataBindingUtil.setContentView(this, R.layout.myfavorite);
        presenter = new MyFavoritePresenter(this,this);
        binding.titleLayout.setBehavior(this);
        //设置标题
        binding.titleLayout.titlecontext.setText("收藏");
        presenter.getData(page, GetDataType.INITDATA);
        binding.favoriteList.setOnRefreshListener(new AutoReFreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.getData(page, GetDataType.REFRESHDATA);
            }
        });
        binding.favoriteList.setOnLoadListener(new AutoReFreshListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                presenter.getData(page, GetDataType.LOADDATA);
            }
        });
    }

    @Override
    public void showLoading() {
        super.showLoading("加载中...");
    }
    @Override
    public void init(ArrayList<FavoriteItem> mlist) {
        mList = mlist;
        myFavoriteAdapter = new MyFavoriteAdapter(context,mList);
        binding.favoriteList.setAdapter(myFavoriteAdapter);
    }

    @Override
    public void loadsuccess(ArrayList<FavoriteItem> mlist) {
        binding.favoriteList.onLoadMoreComplete();
        mList.addAll(mlist);
        myFavoriteAdapter.setData(mList);
        myFavoriteAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshsuccess(ArrayList<FavoriteItem> mlist) {
        binding.favoriteList.onRefreshComplete();
        myFavoriteAdapter.setData(mList);
        myFavoriteAdapter.notifyDataSetChanged();
    }
}
