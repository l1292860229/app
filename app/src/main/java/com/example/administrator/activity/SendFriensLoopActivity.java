package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.adapter.ImageAdapter;
import com.example.administrator.databinding.SendFriensloopBinding;
import com.example.administrator.entity.Picture;
import com.example.administrator.interfaceview.IUSendFriensLoopView;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */

public class SendFriensLoopActivity extends AppCompatActivity implements IUSendFriensLoopView {
    SendFriensloopBinding binding;
    Context context;
    private String[] biaoqian = new String[]{"生活","企业","微商","活动","其它"};
    private String type="微商";
    private List<Picture> pictureList = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private int Max=9;//最大选择照片数
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
        //动态设置标签
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (String s : biaoqian) {
            RadioButton rb = new RadioButton(context);
            rb.setLayoutParams(params);
            rb.setBackground(getResources().getDrawable(R.drawable.selector_biaoqianshape));
            rb.setButtonDrawable(getResources().getDrawable(R.drawable.selector_biaoqianshape));
            rb.setText(s);
            rb.setTextSize(StringUtil.dip2px(context,8));
            rb.setTextColor(getResources().getColorStateList(R.color.selector_text_color));
            binding.radioGroup.addView(rb);
        }
        //设置标签的值
        binding.radioGroup.setOnCheckedChangeListener(new  RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup arg0, int checkedId) {
                type = biaoqian[arg0.getCheckedRadioButtonId()-1];
            }
        });
        //设置图片
        addAddImage();
        imageAdapter = new ImageAdapter(SendFriensLoopActivity.this,pictureList);
        binding.gridview.setAdapter(imageAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case ImageSelector.IMAGE_REQUEST_CODE:
                    if (data != null) {
                        // 获取选中的图片路径列表 Get Images Path List
                        List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                        Max = 9-pictureList.size();
                        if(pictureList.size()==9){
                            pictureList.remove(0);
                        }else{
                            addAddImage();
                        }
                        for (String path : pathList) {
                            pictureList.add(new Picture(path, path, ImageAdapter.LOCAL_TYPE));
                            imageAdapter.setData(pictureList);
                            imageAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 添加添加图片按钮
     */
    public void addAddImage(){
        if(pictureList.size()==0||!pictureList.get(0).getSmallUrl().equals("smiley_add_btn")){
            pictureList.add(0,new Picture("smiley_add_btn", "smiley_add_btn", ImageAdapter.DRAWABLE_TYPE, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UIUtil.openImagePickers(SendFriensLoopActivity.this,Max);
                }
            }));
        }
    }
}
