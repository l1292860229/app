package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.R;
import com.example.administrator.databinding.ContactItemBinding;
import com.example.administrator.entity.SortUserInfo;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.util.StringUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/22.
 */

public class ChooseUserAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SortUserInfo> list;
    private ArrayList<UserInfo> selectUserInfoList;
    private boolean isMultiple;
    public ChooseUserAdapter(Context context, ArrayList<SortUserInfo> list, boolean isMultiple) {
        this.context = context;
        this.list = list;
        this.isMultiple = isMultiple;
        selectUserInfoList = new ArrayList<>();
    }
    public void setData( ArrayList<SortUserInfo> list){
        this.list = list;
    }
    public ArrayList<UserInfo> getSelectUserInfoList(){
        return selectUserInfoList;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ContactItemBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.contact_item, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        if(isMultiple){
            binding.chooseUser.setVisibility(View.VISIBLE);
            final ContactItemBinding finalBinding = binding;
            binding.userDetailLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalBinding.chooseUser.isChecked()) {
                        finalBinding.chooseUser.setChecked(false);
                        selectUserInfoList.remove(list.get(position).getUserInfo());
                    }else{
                        finalBinding.chooseUser.setChecked(true);
                        selectUserInfoList.add(list.get(position).getUserInfo());
                    }
                }
            });
        }else{
            binding.chooseUser.setVisibility(View.GONE);
        }
        UserInfo userInfo = list.get(position).getUserInfo();
        binding.setGroup(list.get(position).getSort());
        if(position!=0 && list.get(position).getSort().equals(list.get(position-1).getSort())){
            binding.group.setVisibility(View.GONE);
        }else{
            binding.group.setVisibility(View.VISIBLE);
        }
        if(StringUtil.isNull(userInfo.getRemark())){
            binding.setNickname(userInfo.getNickname());
        }else{
            binding.setNickname(userInfo.getRemark());
        }
        binding.setHeadsmall(userInfo.getHeadsmall());
        binding.setJob(userInfo.getJob());
        binding.setCompany(userInfo.getCompany());
        if(StringUtil.isNull(userInfo.getJob())){
            binding.job.setVisibility(View.GONE);
        }else{
            binding.job.setVisibility(View.VISIBLE);
        }
        if(StringUtil.isNull(userInfo.getCompany())){
            binding.company.setVisibility(View.GONE);
        }else{
            binding.company.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSort();
            if(!StringUtil.isNull(sortStr)){
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }
        }
        return -1;
    }
}
