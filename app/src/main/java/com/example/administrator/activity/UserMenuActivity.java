package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.adapter.ListAdapter;
import com.example.administrator.databinding.UserMenuPageBinding;
import com.example.administrator.entity.UserMenu;
import com.example.administrator.interfaceview.IUUserMenuView;
import com.example.administrator.presenter.UserMenuPresenter;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.UIUtil;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;

/**
 * Created by dell on 2017/4/19.
 */

public class UserMenuActivity extends BaseActivity implements IUUserMenuView {
    private static final  int myrResultCode =0;
    UserMenuPageBinding binding;
    UserMenuPresenter userMenuPresenter;
    Context context;
    ArrayList<UserMenu> mUsermenu;
    ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding =  DataBindingUtil.setContentView(this, R.layout.user_menu_page);
        binding.setBehavior(this);
        binding.titleLayout.setBehavior(this);
        userMenuPresenter = new UserMenuPresenter(this,this);
        binding.titleLayout.titlecontext.setText("自定义菜单");
        binding.titleLayout.rightBtn.setImageResource(R.drawable.add_icon_btn);
        binding.titleLayout.rightBtn.setVisibility(View.VISIBLE);
        userMenuPresenter.init();
    }

    @Override
    public void init(ArrayList<UserMenu> usermenu) {
        mUsermenu = usermenu;
        if(mUsermenu==null){
            mUsermenu = new ArrayList<>();
        }
        if(mUsermenu.size()>0){
            binding.notmenu.setVisibility(View.GONE);
        }
        if(mUsermenu.size()==4){
            binding.cancreatecount.setText("已达最大上限");
        }else if(mUsermenu.size()>0){
            binding.cancreatecount.setText("还可以创建"+(4-mUsermenu.size())+"个");
        }
        listAdapter = new ListAdapter<>(context,mUsermenu,R.layout.usermenu_item, BR.userMenu);
        //设置左滑菜单
        final Menu mMenu = new Menu(true, false);
        mMenu.addItem(getMenu("删除", Color.RED));
        binding.usermenulist.setMenu(mMenu);
        binding.usermenulist.setAdapter(listAdapter);
        binding.usermenulist.setOnListItemClickListener(new SlideAndDragListView.OnListItemClickListener() {
            @Override
            public void onListItemClick(View v, int position) {
                Intent intent = new Intent(context,AddUserMenuPageAcitivty.class);
                intent.putExtra(AddUserMenuPageAcitivty.DATAKEY,mUsermenu.get(position));
                startActivityForResult(intent,myrResultCode);
            }
        });
        binding.usermenulist.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
            @Override
            public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
                switch (direction) {
                    case MenuItem.DIRECTION_RIGHT:
                        switch (buttonPosition) {
                            case 0:
                                userMenuPresenter.delete(mUsermenu.get(itemPosition).getId());
                                mUsermenu.remove(itemPosition);
                                listAdapter.setData(mUsermenu);
                                listAdapter.notifyDataSetChanged();
                                if(mUsermenu.size()>0){
                                    binding.cancreatecount.setText("还可以创建"+(4-mUsermenu.size())+"个");
                                }else{
                                    binding.cancreatecount.setText("");
                                    binding.notmenu.setVisibility(View.VISIBLE);
                                }
                                return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
                        }
                        break;
                }
                return Menu.ITEM_NOTHING;
            }
        });
        binding.addusermenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right_btn(v);
            }
        });
    }

    @Override
    public void right_btn(View view) {
        Intent intent = new Intent(this,AddUserMenuPageAcitivty.class);
        startActivityForResult(intent,myrResultCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case myrResultCode:
                if(resultCode==RESULT_OK){
                    mUsermenu = GetDataUtil.getUserInfo(context).getUsermenulist();
                    listAdapter.setData(mUsermenu);
                    listAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /**
     * 设置listView左滑时的菜单
     * @param title
     * @param color
     * @return
     */
    public MenuItem getMenu(String title, int color){
        return UIUtil.getMenu(title,color,150,Color.WHITE,14,MenuItem.DIRECTION_RIGHT);
    }
}
