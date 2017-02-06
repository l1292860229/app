package com.example.administrator.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.databinding.WriteNameBinding;
import com.example.administrator.interfaceview.IUWriteNameView;
import com.example.administrator.presenter.WriteNamePresenter;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;

/**
 * Created by Administrator on 2017/1/24.
 */

public class WriteNameActivity extends AppCompatActivity  implements IUWriteNameView {
    public static final String SINGLE = "single";
    public static final String MULTI = "multi";
    private String type;
    private boolean verify=true;
    WriteNameBinding binding;
    private WriteNamePresenter writeNamePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this, R.layout.write_name);
        writeNamePresenter = new WriteNamePresenter(this,this);
        writeNamePresenter.init();
    }
    public void clearname(View view){
        binding.setUsername("");
    }
    @Override
    public void setSingle(){
        binding.markname.setLines(1);
        binding.markname.setMaxLines(1);
    }
    @Override
    public void setMulti(){
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, StringUtil.dip2px(WriteNameActivity.this,110));
        int margin = StringUtil.dip2px(WriteNameActivity.this, 10);
        param.setMargins(margin, margin,margin, 0);
        binding.markname.setGravity(Gravity.TOP|Gravity.LEFT);
        binding.markname.setPadding(10,10,10,10);
        binding.markname.setLayoutParams(param);
        binding.markname.setLines(5);
        binding.markname.setMaxLines(5);
    }
    @Override
    public void init(String name,final String title,final boolean verify) {
        binding.setUsername(name);
        binding.markname.setHint(title);
        binding.setBehavior(WriteNameActivity.this);
        //设置标题
        ((TextView)binding.titleLayout.findViewById(R.id.titlecontext)).setText(title);
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteNameActivity.this.finish();
            }
        });
        TextView tvright = ((TextView)binding.titleLayout.findViewById(R.id.right_text));
        tvright.setText("确定");
        tvright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.markname.getText().toString();
                if(verify&&StringUtil.isNull(name)){
                    UIUtil.showMessage(WriteNameActivity.this,title+"不能为空");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("name",name);
                setResult(RESULT_OK,intent);
                WriteNameActivity.this.finish();
            }
        });
    }
}
