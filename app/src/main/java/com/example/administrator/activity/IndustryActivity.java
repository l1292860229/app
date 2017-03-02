package com.example.administrator.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.databinding.GroupItemBinding;

/**
 * Created by Administrator on 2017/2/18.
 */

public class IndustryActivity extends AppCompatActivity {
    private Context context;
    GroupItemBinding binding;
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
                IndustryActivity.this.finish();
            }
        });
    }
}
