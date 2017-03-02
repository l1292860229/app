package com.example.administrator.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.databinding.GroupItemBinding;
import com.example.administrator.entity.Bbs;
import com.example.administrator.interfaceview.IUBBSView;
import com.example.administrator.presenter.BBSPresenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/18.
 */

public class BBSActivity extends BaseActivity implements IUBBSView {
    private Context context;
    GroupItemBinding binding;
    BBSPresenter bbsPresenter;
    private ArrayList<Bbs> bbsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding =  DataBindingUtil.setContentView(this, R.layout.group_item);
        ((TextView)binding.titleLayout.findViewById(R.id.titlecontext)).setText("万人群");
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BBSActivity.this.finish();
            }
        });
        ImageView tvright = ((ImageView)binding.titleLayout.findViewById(R.id.right_btn2));
        tvright.setVisibility(View.VISIBLE);
        tvright.setBackground(ContextCompat.getDrawable(context,R.drawable.add_icon_btn));
        bbsPresenter = new BBSPresenter(context,this);
        bbsPresenter.init(BBSPresenter.BBS,true);
    }

    @Override
    public void init(ArrayList<Bbs> bbsArrayList) {
            this.bbsArrayList = bbsArrayList;
        for (Bbs bbs : bbsArrayList) {
            Log.e("init","bbs="+bbs);
        }
    }

    @Override
    public void showLoading() {
        super.showLoading("正在获取万人群");
    }
}
