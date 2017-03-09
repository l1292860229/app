package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.adapter.IndustryAdapter;
import com.example.administrator.databinding.GroupItemBinding;
import com.example.administrator.entity.Bbs;
import com.example.administrator.enumset.GetDataType;
import com.example.administrator.interfaceview.IUIndustryView;
import com.example.administrator.presenter.IndustryPresenter;
import com.tandong.sa.view.AutoReFreshListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/18.
 */

public class IndustryViewActivity extends BaseActivity implements IUIndustryView {
    private Context context;
    GroupItemBinding binding;
    private IndustryPresenter industryPresenter;
    private ArrayList<Bbs> bbsArrayList;
    private int page=1;
    private IndustryAdapter industryAdapter;
    private AutoReFreshListView mListView;
    private boolean isprivate=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding =  DataBindingUtil.setContentView(this, R.layout.group_item);
        ((TextView)binding.titleLayout.findViewById(R.id.titlecontext)).setText("行业圈");
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IndustryViewActivity.this.finish();
            }
        });
        ImageView tvright2 = ((ImageView)binding.titleLayout.findViewById(R.id.right_btn2));
        tvright2.setVisibility(View.VISIBLE);
        tvright2.setBackground(ContextCompat.getDrawable(context,R.drawable.add_icon_btn));
        tvright2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddBbsAndIndustryActivity.class);
                intent.putExtra(AddBbsAndIndustryActivity.ISSHOWMONEY,true);
                startActivity(intent);
            }
        });
        ImageView tvright = ((ImageView)binding.titleLayout.findViewById(R.id.right_btn));
        tvright.setVisibility(View.VISIBLE);
        tvright.setBackground(ContextCompat.getDrawable(context,R.drawable.search_icon_btn));
        mListView = binding.groupList;
        industryPresenter = new IndustryPresenter(context,this);
        industryPresenter.getData(isprivate,page, GetDataType.INITDATA);
    }

    @Override
    public void init(ArrayList<Bbs> bbsArrayList) {
        this.bbsArrayList = bbsArrayList;
        industryAdapter = new IndustryAdapter(context,bbsArrayList);
        mListView.setAdapter(industryAdapter);
        mListView.setOnRefreshListener(new AutoReFreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                industryPresenter.getData(isprivate,page, GetDataType.REFRESHDATA);
            }
        });
        mListView.setOnLoadListener(new AutoReFreshListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                industryPresenter.getData(isprivate,page, GetDataType.LOADDATA);
            }
        });
    }

    @Override
    public void loadsuccess(ArrayList<Bbs> bbsArrayList) {
        mListView.onLoadMoreComplete();
        this.bbsArrayList.addAll(bbsArrayList);
        industryAdapter.setData(this.bbsArrayList);
        industryAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshsuccess(ArrayList<Bbs> bbsArrayList) {
        mListView.onRefreshComplete();
        this.bbsArrayList = bbsArrayList;
        industryAdapter.setData(this.bbsArrayList);
        industryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        super.showLoading("正在获取行业圈");
    }

}
