package com.example.administrator.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.activity.FriensLoopActivity;
import com.example.administrator.activity.ScanActivity;
import com.example.administrator.activity.WebViewActivity;
import com.example.administrator.databinding.MenuListItemBinding;
import com.example.administrator.entity.Menu;

import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private List<Menu> list;
    public MenuAdapter(Context context, List<Menu> list) {
        this.context = context;
        this.list = list;
    }
    public void setData(List<Menu> list){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        MenuListItemBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.menu_list_item, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(BR.menu, list.get(position));
        binding.menubody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL,list.get(position).getUrl());
                context.startActivity(intent);
            }
        });
        if (list.get(position).getTitle().equals("商机圈")) {
            binding.topLayout.setVisibility(View.VISIBLE);
            binding.imageUrl.setBackground(ContextCompat.getDrawable(context,R.mipmap.outlander_icon));
            binding.bottomLayout.setVisibility(View.VISIBLE);
            binding.menubody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(context, FriensLoopActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if(list.get(position).getTitle().equals("扫一扫")){
            binding.imageUrl.setBackground(ContextCompat.getDrawable(context,R.mipmap.fx_find_erweima));
            binding.bottomLayout.setVisibility(View.VISIBLE);
            binding.menubody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(context, ScanActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if(list.get(position).getTitle().equals("附近的店")){
            binding.imageUrl.setBackground(ContextCompat.getDrawable(context,R.mipmap.fujindedian));
        }else if(list.get(position).getTitle().equals("一元夺宝")){
            binding.imageUrl.setBackground(ContextCompat.getDrawable(context,R.mipmap.yiyuan));
            binding.bottomLayout.setVisibility(View.VISIBLE);
        }else if(list.get(position).getTitle().equals("购物")){
            binding.imageUrl.setBackground(ContextCompat.getDrawable(context,R.mipmap.gouwuche));
        }else if(list.get(position).getTitle().equals("游戏")){
            binding.imageUrl.setBackground(ContextCompat.getDrawable(context,R.mipmap.game));
            binding.bottomLayout.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }
}
