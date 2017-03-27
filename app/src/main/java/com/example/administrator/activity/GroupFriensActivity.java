package com.example.administrator.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.R;
import com.example.administrator.adapter.GroupFriensAdapter;
import com.example.administrator.databinding.GroupItemBinding;
import com.example.administrator.entity.Room;
import com.example.administrator.interfaceview.IUGroupFriensView;
import com.example.administrator.presenter.GroupFriensPresenter;
import com.tandong.sa.view.AutoReFreshListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/18.
 */

public class GroupFriensActivity extends BaseActivity implements IUGroupFriensView {
    private GroupFriensActivity context;
    GroupItemBinding binding;
    private GroupFriensPresenter groupFriensPresenter;
    private ArrayList<Room> roomList;
    private GroupFriensAdapter groupFriensAdapter;
    private AutoReFreshListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding =  DataBindingUtil.setContentView(this, R.layout.group_item);
        binding.titleLayout.titlecontext.setText("群聊");
        binding.setBehavior(this);
        binding.titleLayout.setBehavior(this);
        groupFriensPresenter = new GroupFriensPresenter(context,this);
        groupFriensPresenter.init();
        mListView = binding.groupList;
    }
    @Override
    public void init(ArrayList<Room> roomList) {
        this.roomList = roomList;
        groupFriensAdapter = new GroupFriensAdapter(context,roomList);
        mListView.setAdapter(groupFriensAdapter);
        if(roomList.size()>0){
            binding.groupCount.setVisibility(View.VISIBLE);
            binding.groupCount.setText(roomList.size()+"个群");
        }
    }
    @Override
    public void showLoading() {
        super.showLoading("正在获取群聊");
    }
}
