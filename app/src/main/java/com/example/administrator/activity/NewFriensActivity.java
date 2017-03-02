package com.example.administrator.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        ((TextView)binding.titleLayout.findViewById(R.id.titlecontext)).setText("新的朋友");
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewFriensActivity.this.finish();
            }
        });
    }
}
