package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.example.administrator.util.UIUtil;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;

import java.util.List;


/**
 * Created by Administrator on 2017/1/24.
 */

public class EditProfileActivity extends BaseActivity implements IUEditProfileView {
    private enum Write{
        WRITE,
        WRITE_NICKNAME,
        WRITE_SIGN,
        WRITE_COMPANYWEBSITE,
        WRITE_INDUSTRY,
        WRITE_COMPANY,
        WRITE_COMPANYADDRESS,
        WRITE_JOB,
        WRITE_PROVIDE,
        WRITE_DEMAND,
        WRITE_TELEPHONE
    }
    EditProfileBinding binding;
    Context context;
    AbSampleDialogFragment abSampleDialogFragment;
    EditProfilePresenter editProfilePresenter;
    private String imagePath,province,city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = EditProfileActivity.this;
        editProfilePresenter = new EditProfilePresenter(this,this);
        binding =  DataBindingUtil.setContentView(this, R.layout.edit_profile);
        editProfilePresenter.init();
    }
    @Override
    public void init(UserInfo userInfo) {
        binding.setUserinfo(userInfo);
        binding.setBehavior(this);
        binding.titleLayout.setBehavior(this);
        if (userInfo.getGender()== UserInfo.SexType.GIRL) {
            binding.sexContent.setText("女");
        }else{
            binding.sexContent.setText("男");
        }
        province = userInfo.getProvince();
        city = userInfo.getCity();
        binding.titleLayout.titlecontext.setText("编辑资料");
        TextView tvRight = binding.titleLayout.rightText;
        tvRight.setText("确定");
    }

    @Override
    public void right_text(View view) {
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(binding.nicknameContent.getText().toString());
        String sex = binding.sexContent.getText().toString();
        if (sex.equals("女")) {
            userInfo.setGender(UserInfo.SexType.GIRL);
        }else{
            userInfo.setGender(UserInfo.SexType.BOY);
        }
        userInfo.setSign(binding.signContent.getText().toString());
        userInfo.setProvince(province);
        userInfo.setCity(city);
        userInfo.setHeadsmall(imagePath);
        userInfo.setCompanywebsite(binding.companywebsite.getText().toString());
        userInfo.setIndustry(binding.industry.getText().toString());
        userInfo.setCompany(binding.company.getText().toString());
        userInfo.setCompanyaddress(binding.companyaddress.getText().toString());
        userInfo.setJob(binding.job.getText().toString());
        userInfo.setProvide(binding.provide.getText().toString());
        userInfo.setDemand(binding.demand.getText().toString());
        userInfo.setTelephone(binding.telephone.getText().toString());
        editProfilePresenter.updateUserInfo(userInfo);
    }

    @Override
    public void close() {
        setResult(RESULT_OK);
        this.finish();
    }
    public void openWriteNickname(View view){
        openWindow(binding.nicknameContent.getText().toString(),"昵称",WriteNameActivity.SINGLE,true, Write.WRITE_NICKNAME);
    }
    public void openWriteSign(View view){
        openWindow(binding.signContent.getText().toString(),"个性签名",WriteNameActivity.MULTI,false, Write.WRITE_SIGN);
    }
    public void openWriteCompanywebsite(View view){
        openWindow(binding.companywebsite.getText().toString(),"公司主页",WriteNameActivity.MULTI,false, Write.WRITE_COMPANYWEBSITE);
    }
    public void openWriteIndustry(View view){
        openWindow(binding.industry.getText().toString(),"行业",WriteNameActivity.SINGLE,false, Write.WRITE_INDUSTRY);
    }
    public void openWriteCompany(View view){
        openWindow(binding.company.getText().toString(),"公司",WriteNameActivity.SINGLE,false, Write.WRITE_COMPANY);
    }
    public void openWriteCompanyaddress(View view){
        openWindow(binding.companyaddress.getText().toString(),"公司地址",WriteNameActivity.MULTI,false, Write.WRITE_COMPANYADDRESS);
    }
    public void openJob(View view){
        openWindow(binding.job.getText().toString(),"职位",WriteNameActivity.SINGLE,false,Write.WRITE_JOB);
    }
    public void openWriteProvide(View view){
        openWindow(binding.provide.getText().toString(),"可供",WriteNameActivity.MULTI,false,Write.WRITE_PROVIDE);
    }
    public void openWriteDemand(View view){
        openWindow(binding.demand.getText().toString(),"需求",WriteNameActivity.MULTI,false,Write.WRITE_DEMAND);
    }
    public void openWriteTelephone(View view){
        openWindow(binding.telephone.getText().toString(),"电话号码",WriteNameActivity.SINGLE,false,Write.WRITE_TELEPHONE);
    }
    public void openWindow(String name,String title,String type,boolean verify,Write backcode){
        Intent intent = new Intent();
        intent.putExtra("name",name);
        intent.putExtra("title",title);
        intent.putExtra("type",type);
        intent.putExtra("verify",verify);
        intent.setClass(context, WriteNameActivity.class);
        startActivityForResult(intent,backcode.ordinal());
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
        UIUtil.openImagePicker(EditProfileActivity.this,true);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode== ImageSelector.IMAGE_REQUEST_CODE){
                if (data != null) {
                    // 获取选中的图片路径列表 Get Images Path List
                    List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                    for (String path : pathList) {
                        imagePath = path;
                        binding.newHeaderIcon.setImageBitmap(BitmapFactory.decodeFile(path));
                    }
                }
            }else{
                Write write = Write.values()[requestCode];
                switch (write){
                    case WRITE_NICKNAME:
                        binding.nicknameContent.setText(data.getStringExtra("name"));
                        break;
                    case WRITE_SIGN:
                        binding.signContent.setText(data.getStringExtra("name"));
                        break;
                    case WRITE_COMPANYWEBSITE:
                        binding.companywebsite.setText(data.getStringExtra("name"));
                        break;
                    case WRITE_INDUSTRY:
                        binding.industry.setText(data.getStringExtra("name"));
                        break;
                    case WRITE_COMPANY:
                        binding.company.setText(data.getStringExtra("name"));
                        break;
                    case WRITE_COMPANYADDRESS:
                        binding.companyaddress.setText(data.getStringExtra("name"));
                        break;
                    case WRITE_JOB:
                        binding.job.setText(data.getStringExtra("name"));
                        break;
                    case WRITE_PROVIDE:
                        binding.provide.setText(data.getStringExtra("name"));
                        break;
                    case WRITE_DEMAND:
                        binding.demand.setText(data.getStringExtra("name"));
                        break;
                    case WRITE_TELEPHONE:
                        binding.telephone.setText(data.getStringExtra("name"));
                        break;
                }
            }
        }
    }

    @Override
    public void showLoading() {
        super.showLoading("正在修改中....");
    }
}
