package com.example.administrator.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.R;
import com.example.administrator.databinding.NewFriensBinding;

/**
 * Created by Administrator on 2017/2/18.
 */

public class NewFriensActivity extends AppCompatActivity {
    NewFriensBinding binding;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this, R.layout.new_friens);
    }
}
