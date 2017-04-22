package com.example.administrator.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.R;
import com.example.administrator.databinding.AddUserMenuPageBinding;
import com.example.administrator.entity.UserMenu;
import com.example.administrator.interfaceview.IUAddUserMenuPageView;
import com.example.administrator.presenter.AddUserMenuPagePresenter;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;

/**
 * Created by dell on 2017/4/19.
 */

public class AddUserMenuPageAcitivty extends BaseActivity implements IUAddUserMenuPageView {
    public static final String DATAKEY= "usermenu";
    AddUserMenuPageBinding binding;
    AddUserMenuPagePresenter addUserMenuPagePresenter;
    Context context;
    private UserMenu mUserMenu;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding =  DataBindingUtil.setContentView(this, R.layout.add_user_menu_page);
        binding.setBehavior(this);
        binding.titleLayout.setBehavior(this);
        addUserMenuPagePresenter = new AddUserMenuPagePresenter(this,this);
        binding.titleLayout.rightText.setText("确定");
        addUserMenuPagePresenter.init();
    }

    @Override
    public void init(UserMenu userMenu) {
        mUserMenu = userMenu;
        binding.setUserMenu(mUserMenu);
        if(userMenu==null){
            binding.titleLayout.titlecontext.setText("添加");
        }else{
            binding.titleLayout.titlecontext.setText("修改");
        }
    }

    @Override
    public void right_text(View view) {
        String menuname = binding.menuname.getText().toString().trim();
        String menuurl = binding.menuurl.getText().toString().trim();
        if(StringUtil.isNull(menuname)){
            UIUtil.showMessage(context,"菜单名不能为空");
            return;
        }
        if(StringUtil.isNull(menuurl)){
            UIUtil.showMessage(context,"菜单链接不能为空");
            return;
        }
        if(!(menuurl.startsWith("http://")|| menuurl.startsWith("https://"))){
            UIUtil.showMessage(context,"请输入正确网址,http或https开头");
            return;
        }
        if(mUserMenu==null){
            addUserMenuPagePresenter.add(menuname,menuurl);
        }else{
            mUserMenu.setMenuname(menuname);
            mUserMenu.setMenuurl(menuurl);
            addUserMenuPagePresenter.update(mUserMenu);
        }
    }
}
