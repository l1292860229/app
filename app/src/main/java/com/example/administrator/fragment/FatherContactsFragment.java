package com.example.administrator.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ab.view.sliding.AbSlidingTabView;
import com.example.administrator.R;
import com.example.administrator.databinding.FatherContactFragmentBinding;
import com.example.administrator.interfaceview.IUFatherContactsFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class FatherContactsFragment extends Fragment implements IUFatherContactsFragmentView {
    FatherContactFragmentBinding binding;
    private AbSlidingTabView mAbSlidingTabView;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.father_contact_fragment,container,false);
        init();
        return binding.getRoot();
    }

    @Override
    public void init() {
        mAbSlidingTabView = binding.mAbSlidingTabView;
        //缓存数量
        mAbSlidingTabView.getViewPager().setOffscreenPageLimit(4);
        //设置样式
        mAbSlidingTabView.setTabTextColor(Color.BLACK);
        mAbSlidingTabView.setTabSelectColor(Color.rgb(30, 168, 131));
        mAbSlidingTabView.setTabBackgroundResource(R.drawable.tab_bg);
        mAbSlidingTabView.setTabLayoutBackgroundResource(R.mipmap.slide_top);
        List<String> tabTexts = new ArrayList<String>();
        tabTexts.add("同级");
        tabTexts.add("一度");
        tabTexts.add("二度");
        tabTexts.add("三度");
        List<android.app.Fragment> mFragments = new ArrayList<>();
        mFragments.add(new android.app.Fragment());
        mFragments.add(new android.app.Fragment());
        mFragments.add(new android.app.Fragment());
        mFragments.add(new android.app.Fragment());
        //增加一组
        mAbSlidingTabView.addItemViews(tabTexts, mFragments);
    }
}
