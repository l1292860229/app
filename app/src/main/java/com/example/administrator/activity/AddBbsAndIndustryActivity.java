package com.example.administrator.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.databinding.AddBbsBinding;
import com.example.administrator.entity.Bbs;
import com.example.administrator.interfaceview.IUPublicView;
import com.example.administrator.presenter.AddBbsAndIndustryPresenter;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */

public class AddBbsAndIndustryActivity extends BaseActivity implements IUPublicView {
    public final static String ISSHOWMONEY = "showmoney";
    AddBbsBinding registerBinding;
    AddBbsAndIndustryPresenter addBbsAndIndustryPresenter;
    private String imagePath;
    private boolean showmoney=false;
    private AddBbsAndIndustryActivity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = AddBbsAndIndustryActivity.this;
        addBbsAndIndustryPresenter = new AddBbsAndIndustryPresenter(context,this);
        registerBinding =  DataBindingUtil.setContentView(this, R.layout.add_bbs);
        registerBinding.setBehavior(AddBbsAndIndustryActivity.this);
        showmoney = getIntent().getBooleanExtra(ISSHOWMONEY,false);
        if(showmoney){
            registerBinding.bbsmoney.setVisibility(View.VISIBLE);
        }
        ((TextView)registerBinding.titleLayout.findViewById(R.id.titlecontext)).setText("添加");
        ImageView leftbtn = ((ImageView)registerBinding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBbsAndIndustryActivity.this.finish();
            }
        });
        TextView tvRight = ((TextView)registerBinding.titleLayout.findViewById(R.id.right_text));
        tvRight.setText("确定");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }
    public void check(){
        String title =  registerBinding.bbstitle.getText().toString();
        String content = registerBinding.bbscontent.getText().toString();
        String money =  registerBinding.bbsmoney.getText().toString();
        if(StringUtil.isNull(title)){
            UIUtil.showMessage(context,"标题不能为空");
            return;
        }
        if(StringUtil.isNull(content)){
            UIUtil.showMessage(context,"内容不能为空");
            return;
        }
        addBbsAndIndustryPresenter.add(imagePath,title,content, money,showmoney? Bbs.Bbstype.INDUSTRY: Bbs.Bbstype.MILLION);
    }
    @Override
    public void showLoading() {
        super.showLoading("正在提交数据..");
    }
    public void openImagePicker(View view){
        UIUtil.openImagePicker(AddBbsAndIndustryActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageSelector.IMAGE_REQUEST_CODE) {
                if (data != null) {
                    // 获取选中的图片路径列表 Get Images Path List
                    List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                    for (String path : pathList) {
                        imagePath = path;
                        registerBinding.headsmall.setImageBitmap(BitmapFactory.decodeFile(path));
                    }
                }

            }
        }
    }
}
