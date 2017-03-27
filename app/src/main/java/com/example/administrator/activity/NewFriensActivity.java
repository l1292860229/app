package com.example.administrator.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.administrator.R;
import com.example.administrator.databinding.NewFriensBinding;

/**
 * Created by Administrator on 2017/2/18.
 */

public class NewFriensActivity extends BaseActivity {
    NewFriensBinding binding;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this, R.layout.new_friens);
        binding.setBehavior(this);
        binding.titleLayout.setBehavior(this);
        binding.titleLayout.titlecontext.setText("新的朋友");
    }
}
