package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.databinding.BbsItemBinding;
import com.example.administrator.entity.Bbs;
import com.example.administrator.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class BbsAdapter extends BaseAdapter {
    private Context context;
    private List<Bbs> list;
    public BbsAdapter(Context context, List<Bbs> list) {
        this.context = context;
        this.list = list;
    }
    public void setData( List<Bbs> list){
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        BbsItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bbs_item, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(BR.bbsinfo,list.get(position));
        binding.time.setText(DateUtil.calculaterReleasedTime(context,new Date(list.get(position).getTime()),list.get(position).getTime(),0));
        return binding.getRoot();
    }
}
