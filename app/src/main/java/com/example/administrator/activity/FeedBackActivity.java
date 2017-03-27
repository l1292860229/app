package com.example.administrator.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.R;
import com.example.administrator.databinding.FeedbackBinding;
import com.example.administrator.interfaceview.IUFeedBackView;

/**
 * Created by Administrator on 2017/1/24.
 */

public class FeedBackActivity extends AppCompatActivity implements IUFeedBackView {
    FeedbackBinding binding;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this, R.layout.feedback);
        init();
    }
    @Override
    public void init() {
        binding.titleLayout.titlecontext.setText("意见反馈");
        ImageView leftbtn = binding.titleLayout.leftIcon;
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedBackActivity.this.finish();
            }
        });
        ImageView rightBtn = binding.titleLayout.rightBtn;
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setImageResource(R.drawable.send_map_btn);
    }
}
