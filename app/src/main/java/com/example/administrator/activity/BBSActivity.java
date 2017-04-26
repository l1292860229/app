package com.example.administrator.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.administrator.R;
import com.example.administrator.adapter.BbsAdapter;
import com.example.administrator.databinding.GroupItemBinding;
import com.example.administrator.entity.Bbs;
import com.example.administrator.interfaceview.IUBBSView;
import com.example.administrator.presenter.BBSPresenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/18.
 */

public class BBSActivity extends BaseActivity implements IUBBSView {
    private BBSActivity context;
    GroupItemBinding binding;
    BBSPresenter bbsPresenter;
    private ArrayList<Bbs> mBbsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding =  DataBindingUtil.setContentView(this, R.layout.group_item);
        binding.titleLayout.titlecontext.setText("万人群");
        binding.setBehavior(this);
        binding.titleLayout.setBehavior(this);
        ImageView tvright = binding.titleLayout.rightBtn2;
        tvright.setVisibility(View.VISIBLE);
        tvright.setImageResource(R.drawable.add_icon_btn);
        bbsPresenter = new BBSPresenter(context,this);
        bbsPresenter.init();
    }

    /**
     * 点添加按钮
     * @param view
     */
    @Override
    public void right_btn2(View view) {
        Intent intent = new Intent(context,AddBbsAndIndustryActivity.class);
        intent.putExtra(AddBbsAndIndustryActivity.ISSHOWMONEY,false);
        startActivity(intent);
    }

    @Override
    public void init(ArrayList<Bbs> bbsArrayList) {
        this.mBbsArrayList = bbsArrayList;
        binding.groupList.setAdapter(new BbsAdapter(context,bbsArrayList));
        binding.groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, BBSChatMainActivity.class);
                intent.putExtra(ChatMainActivity.NAME,mBbsArrayList.get(position-1).getTitle());
                intent.putExtra(ChatMainActivity.TOID,mBbsArrayList.get(position-1).getId());
                intent.putExtra(ChatMainActivity.TOHEAD,mBbsArrayList.get(position-1).getHeadsmall());
                intent.putExtra(BBSChatMainActivity.BBSTYPE, mBbsArrayList.get(position-1).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void showLoading() {
        super.showLoading("正在获取万人群");
    }
}
