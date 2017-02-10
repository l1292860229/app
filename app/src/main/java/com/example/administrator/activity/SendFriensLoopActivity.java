package com.example.administrator.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.databinding.SendFriensloopBinding;
import com.example.administrator.interfaceview.IUSendFriensLoopView;
import com.example.administrator.util.StringUtil;

/**
 * Created by Administrator on 2017/2/10.
 */

public class SendFriensLoopActivity extends AppCompatActivity implements IUSendFriensLoopView {
    SendFriensloopBinding binding;
    Context context;
    private String[] biaoqian = new String[]{"生活","企业","微商","活动"};
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SendFriensLoopActivity.this;
        binding =  DataBindingUtil.setContentView(this, R.layout.send_friensloop);
        init();
    }

    @Override
    public void init() {
        ((TextView)binding.titleLayout.findViewById(R.id.titlecontext)).setText("发布");
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendFriensLoopActivity.this.finish();
            }
        });
        TextView tvRight = ((TextView)binding.titleLayout.findViewById(R.id.right_text));
        tvRight.setText("发送");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(StringUtil.dip2px(context,15),0,0,0);
        for (String s : biaoqian) {
            RadioButton rb = new RadioButton(context);
            rb.setLayoutParams(params);
            rb.setBackground(getResources().getDrawable(R.drawable.selector_biaoqianshape));
            rb.setButtonDrawable(getResources().getDrawable(R.drawable.selector_biaoqianshape));
            rb.setTextColor(context.getResources().getColor(R.drawable.selector_text_color));
            rb.setText(s);
            rb.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size));
        }
    }
}
