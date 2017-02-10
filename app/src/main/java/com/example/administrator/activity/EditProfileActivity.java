package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.fragment.AbSampleDialogFragment;
import com.ab.util.AbDialogUtil;
import com.ab.view.wheel.AbStringWheelAdapter;
import com.ab.view.wheel.AbWheelView;
import com.example.administrator.R;
import com.example.administrator.databinding.DialogTextBinding;
import com.example.administrator.databinding.EditProfileBinding;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUEditProfileView;
import com.example.administrator.presenter.EditProfilePresenter;
import com.example.administrator.util.ImageUitl;
import com.example.administrator.util.UIUtil;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/24.
 */

public class EditProfileActivity extends AppCompatActivity implements IUEditProfileView {
    private static final int WRITE_NICKNAME =1;
    private static final int WRITE_SIGN =2;
    EditProfileBinding binding;
    Context context;
    AbSampleDialogFragment abSampleDialogFragment;
    EditProfilePresenter editProfilePresenter;
    private String imagePath,province,city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = EditProfileActivity.this;
        ImageUitl.init(context);
        editProfilePresenter = new EditProfilePresenter(this,this);
        binding =  DataBindingUtil.setContentView(this, R.layout.edit_profile);
        editProfilePresenter.init();
    }
    @Override
    public void init(UserInfo userInfo) {
        binding.setUserinfo(userInfo);
        binding.setBehavior(this);
        province = userInfo.getProvince();
        city = userInfo.getCity();
        ((TextView)binding.titleLayout.findViewById(R.id.titlecontext)).setText("编辑资料");
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfileActivity.this.finish();
            }
        });
        TextView tvRight = ((TextView)binding.titleLayout.findViewById(R.id.right_text));
        tvRight.setText("确定");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname  =binding.nicknameContent.getText().toString();
                String gender = binding.sexContent.getText().toString();
                String sgin = binding.signContent.getText().toString();
                editProfilePresenter.updateUserInfo(imagePath,nickname,gender,sgin,province,city);
            }
        });
    }

    @Override
    public void close() {
        setResult(RESULT_OK);
        this.finish();
    }
    public void openWriteNickname(View view){
        Intent intent = new Intent();
        intent.putExtra("name",binding.nicknameContent.getText().toString());
        intent.putExtra("title","昵称");
        intent.putExtra("type",WriteNameActivity.SINGLE);
        intent.putExtra("verify",true);
        intent.setClass(context, WriteNameActivity.class);
        startActivityForResult(intent,WRITE_NICKNAME);
    }
    public void openWriteSign(View view){
        Intent intent = new Intent();
        intent.putExtra("name",binding.signContent.getText().toString());
        intent.putExtra("title","个性签名");
        intent.putExtra("type",WriteNameActivity.MULTI);
        intent.putExtra("verify",false);
        intent.setClass(context, WriteNameActivity.class);
        startActivityForResult(intent,WRITE_SIGN);
    }
    public void openSexSelect(View view){
        DialogTextBinding dialogTextBinding =  DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.dialog_text,null,false);
        dialogTextBinding.setBehavior(this);
        abSampleDialogFragment =  AbDialogUtil.showDialog(dialogTextBinding.getRoot(),R.anim.fragment_top_enter,R.anim.fragment_top_exit,R.anim.fragment_pop_top_enter,R.anim.fragment_pop_top_exit);
    }
    public void selectBoy(View view){
        binding.sexContent.setText("男");
        abSampleDialogFragment.dismiss();
    }
    public void selectGirl(View view){
        binding.sexContent.setText("女");
        abSampleDialogFragment.dismiss();
    }
    public void openCitySelect(View view){
        View mTimeView3 = getLayoutInflater().inflate(R.layout.choose_two, null);
        initWheelData(mTimeView3);
        abSampleDialogFragment =  AbDialogUtil.showDialog(mTimeView3, Gravity.BOTTOM);
    }

    /**
     * 地区的轮子控件
     * @param mDataView1
     */
    public void initWheelData(View mDataView1){
        final AbWheelView mWheelView1 = (AbWheelView)mDataView1.findViewById(R.id.wheelView1);
        final AbWheelView mWheelView2 = (AbWheelView)mDataView1.findViewById(R.id.wheelView2);
        mWheelView1.setAdapter(new AbStringWheelAdapter(editProfilePresenter.getProvinceList()));
        initWheelView(mWheelView1);
        initWheelView(mWheelView2);
        mWheelView1.addScrollingListener(new AbWheelView.AbOnWheelScrollListener() {
            @Override
            public void onScrollingStarted(AbWheelView abWheelView) {}
            @Override
            public void onScrollingFinished(AbWheelView abWheelView) {
                int index = (mWheelView1.getCurrentItem()+1)%34;
                mWheelView2.setAdapter(new AbStringWheelAdapter(editProfilePresenter.getCityList(index)));
            }
        });
        Button okBtn = (Button)mDataView1.findViewById(R.id.okBtn);
        Button cancelBtn = (Button)mDataView1.findViewById(R.id.cancelBtn);
        okBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int index1 = mWheelView1.getCurrentItem()+1;
                String val1 = mWheelView1.getAdapter().getItem(index1);
                int index2 = mWheelView2.getCurrentItem()+1;
                String val2="";
                province = val1;
                if(mWheelView2.getAdapter()!=null){
                    val2 = mWheelView2.getAdapter().getItem(index2);
                    city = val2;
                }
                binding.addrContent.setText(val1+" "+val2);
                abSampleDialogFragment.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                abSampleDialogFragment.dismiss();
            }

        });
    }
    private void initWheelView(AbWheelView mWheelView){
        // 可循环滚动
        mWheelView.setCyclic(true);
        // 初始化时显示的数据
        mWheelView.setCurrentItem(1);
        mWheelView.setValueTextSize(40);
        mWheelView.setLabelTextSize(40);
        mWheelView.setLabelTextColor(0x80000000);
        mWheelView.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));
    }

    /**
     * 打开图片选择器
     * @param view
     */
    public void openImagePicker(View view){
        UIUtil.openImagePicker(EditProfileActivity.this);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case ImageSelector.IMAGE_REQUEST_CODE:
                    if (data != null) {
                        // 获取选中的图片路径列表 Get Images Path List
                        List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                        for (String path : pathList) {
                            imagePath = path;
                            binding.newHeaderIcon.setImageBitmap(BitmapFactory.decodeFile(path));
                        }
                    }
                    break;
                case WRITE_NICKNAME:
                    binding.nicknameContent.setText(data.getStringExtra("name"));
                    break;
                case WRITE_SIGN:
                    binding.signContent.setText(data.getStringExtra("name"));
                    break;
            }
        }
    }

    @Override
    public void showLoading() {
        UIUtil.showLoading(this,"正在修改中....");
    }

    @Override
    public void hideLoading() {
        UIUtil.hideLoading(this);
    }
}
