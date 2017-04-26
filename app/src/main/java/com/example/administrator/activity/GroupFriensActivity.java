package com.example.administrator.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.administrator.R;
import com.example.administrator.adapter.GroupFriensAdapter;
import com.example.administrator.databinding.GroupItemBinding;
import com.example.administrator.entity.Group;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.enumset.GetDataType;
import com.example.administrator.enumset.TypeChat;
import com.example.administrator.interfaceview.IUGroupFriensView;
import com.example.administrator.presenter.GroupFriensPresenter;
import com.smartandroid.sa.view.AutoReFreshListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/18.
 */

public class GroupFriensActivity extends BaseActivity implements IUGroupFriensView {
    //刷新数据的广播
    final public static String  REFRESH_GROUPFRIENS_DATA="refresh_groupfriens_data_action";
    private GroupFriensActivity context;
    GroupItemBinding binding;
    private GroupFriensPresenter groupFriensPresenter;
    private ArrayList<Group> mGroupList;
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
        mListView = binding.groupList;
        groupFriensPresenter.getData(GetDataType.INITDATA);
    }
    @Override
    public void init(ArrayList<Group> groupList) {
        this.mGroupList = groupList;
        groupFriensAdapter = new GroupFriensAdapter(context, groupList);
        mListView.setAdapter(groupFriensAdapter);
        if(groupList.size()>0){
            binding.groupCount.setVisibility(View.VISIBLE);
            binding.groupCount.setText(groupList.size()+"个群");
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, IMChatMainActivity.class);
                intent.putExtra(IMChatMainActivity.NAME, mGroupList.get(position-1).getName());
                intent.putExtra(IMChatMainActivity.TOID, mGroupList.get(position-1).getId());
                StringBuffer headsmall=new StringBuffer();
                ArrayList<UserInfo> userinfos = mGroupList.get(position-1).getList();
                for (int i = 0; (i < userinfos.size() && i<9); i++) {
                    headsmall.append(userinfos.get(i).getHeadsmall()).append(",");
                }
                if(headsmall.length()>0){
                    headsmall.delete(headsmall.lastIndexOf(","),headsmall.length());
                }
                intent.putExtra(IMChatMainActivity.TOHEAD,headsmall.toString());
                intent.putExtra(IMChatMainActivity.TYPECHAT, TypeChat.GROUP);
                startActivity(intent);
            }
        });
        ImageView mAddBtn = binding.titleLayout.rightBtn2;
        mAddBtn.setImageResource(R.drawable.add_icon_btn);
        mAddBtn.setVisibility(View.VISIBLE);
        registerReceiver();
    }

    @Override
    public void refreshsuccess(ArrayList<Group> groupList) {
        mListView.onRefreshComplete();
        this.mGroupList = groupList;
        groupFriensAdapter.setData(mGroupList);
        groupFriensAdapter.notifyDataSetChanged();
    }

    @Override
    public void right_btn2(View view) {
        Intent intent = new Intent(this,ChooseUserActivity.class);
        intent.putExtra(ChooseUserActivity.MULTIPLE,true);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        super.showLoading("正在获取群聊");
    }
    /**
     * 处理通知
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action  = intent.getAction();
            if (action.equals(REFRESH_GROUPFRIENS_DATA)) {
                groupFriensPresenter.getData(GetDataType.REFRESHDATA);
            }
        }
    };
    /**
     * 注册通知
     */
    private void registerReceiver(){
        IntentFilter filter  = new IntentFilter();
        filter.addAction(REFRESH_GROUPFRIENS_DATA);
        context.registerReceiver(receiver,filter);
    }

    @Override
    public void onDestroy() {
        if(receiver!=null){
            context.unregisterReceiver(receiver);
        }
        super.onDestroy();
    }
}
