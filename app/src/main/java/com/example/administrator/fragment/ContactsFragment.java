package com.example.administrator.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ab.fragment.AbLoadDialogFragment;
import com.ab.util.AbDialogUtil;
import com.example.administrator.R;
import com.example.administrator.activity.UserInfoActivity;
import com.example.administrator.adapter.ContactsAdapter;
import com.example.administrator.databinding.GroupItemNotitleBinding;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.entity.constant.Constants;
import com.example.administrator.enumset.GetDataType;
import com.example.administrator.interfaceview.IUContactsFragmentView;
import com.example.administrator.presenter.ContactsFragmentPresenter;
import com.smartandroid.sa.view.AutoReFreshListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/28.
 */

public class ContactsFragment extends Fragment implements IUContactsFragmentView {
    private Context context;
    GroupItemNotitleBinding binding;
    private ContactsFragmentPresenter presenter;
    private int page=1;
    private int friends;
    AbLoadDialogFragment loadingfragment;
    ArrayList<UserInfo> userInfoArrayList;
    private ContactsAdapter userInfoListAdapter;
    private AutoReFreshListView mListView;
    public static final ContactsFragment newInstance(int friends){
        ContactsFragment c = new ContactsFragment();
        Bundle bdl = new Bundle();
        bdl.putInt("friends", friends);
        c.setArguments(bdl);
        return c;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();
        binding =  DataBindingUtil.inflate(inflater, R.layout.group_item_notitle,container,false);
        presenter = new ContactsFragmentPresenter(this.getActivity(),this);
        Bundle bundle = getArguments();
        friends = bundle.getInt("friends");
        mListView = binding.groupList;
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getDate(page,friends,GetDataType.INITDATA);
    }

    @Override
    public void init(ArrayList<UserInfo> userInfos) {
        page = userInfos.size()/20;
        userInfoArrayList = userInfos;
        userInfoListAdapter = new ContactsAdapter(context,userInfoArrayList);
        mListView.setAdapter(userInfoListAdapter);
        mListView.setOnRefreshListener(new AutoReFreshListView.OnRefreshListener() {// 上拉刷新
            public void onRefresh() {
                presenter.getDate(1,friends, GetDataType.REFRESHDATA);
            }
        });
        mListView.setOnLoadListener(new AutoReFreshListView.OnLoadMoreListener() {// 下拉加载更多
            @Override
            public void onLoadMore() {
                presenter.getDate(++page,friends,GetDataType.LOADDATA);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra(UserInfoActivity.ID, Constants.ID);
                intent.putExtra(UserInfoActivity.USERINFO,userInfoArrayList.get(position-1));
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadsuccess(ArrayList<UserInfo> userInfos) {
        mListView.onLoadMoreComplete();
        userInfoArrayList = userInfos;
        userInfoListAdapter.setData(userInfoArrayList);
        userInfoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshsuccess(ArrayList<UserInfo> userInfos) {
        mListView.onRefreshComplete();
        userInfoArrayList = userInfos;
        userInfoListAdapter.setData(userInfoArrayList);
        userInfoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        loadingfragment =  AbDialogUtil.showLoadDialog(context, R.mipmap.ic_load, "正在获取好友..");
    }

    @Override
    public void hideLoading() {
        loadingfragment.dismiss();
        loadingfragment.onDestroyView();
    }
}
