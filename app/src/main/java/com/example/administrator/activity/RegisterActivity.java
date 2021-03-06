package com.example.administrator.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.example.administrator.R;
import com.example.administrator.databinding.RegisterBinding;
import com.example.administrator.interfaceview.IUPublicView;
import com.example.administrator.presenter.RegisterPresenter;

/**
 * Created by Administrator on 2017/1/21.
 */

public class RegisterActivity extends BaseActivity implements IUPublicView {
    RegisterBinding registerBinding;
    private final String TAG="RegisterActivity";
    RegisterPresenter registerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding =  DataBindingUtil.setContentView(this, R.layout.register);
        registerBinding.setBehavior(this);
        registerBinding.titleLayout.setBehavior(this);
        registerPresenter = new RegisterPresenter(RegisterActivity.this,RegisterActivity.this);
        registerBinding.titleLayout.titlecontext.setText("注册");
    }

    /**
     * 进行注册
     */
    public void register(View view){
        String nickname = registerBinding.name.getText().toString();
        String tjr = registerBinding.tjr.getText().toString();
        String phone = registerBinding.telephone.getText().toString();
        String password =  registerBinding.password.getText().toString();
        registerPresenter.register(nickname,tjr,phone,password);
    }
    /**
     * 显示密码
     * @param view
     */
    public void showpassword(View view){
        registerBinding.ivHide.setVisibility(View.GONE);
        registerBinding.ivShow.setVisibility(View.VISIBLE);
        registerBinding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        registerBinding.password.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = registerBinding.password.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }
    /**
     * 显示隐藏密码
     * @param view
     */
    public void hidepassword(View view){
        registerBinding.ivHide.setVisibility(View.VISIBLE);
        registerBinding.ivShow.setVisibility(View.GONE);
        registerBinding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        registerBinding.password.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = registerBinding.password.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }
    @Override
    public void showLoading() {
        super.showLoading("正在注册,请稍候");
    }
}
