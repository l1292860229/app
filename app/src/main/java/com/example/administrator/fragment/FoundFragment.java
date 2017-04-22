package com.example.administrator.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.R;
import com.example.administrator.adapter.MenuAdapter;
import com.example.administrator.databinding.FragmentFoundBinding;
import com.example.administrator.entity.Menu;
import com.example.administrator.interfaceview.IUFoundFragmentView;
import com.example.administrator.presenter.FoundFragmentPresenter;

import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class FoundFragment extends Fragment implements IUFoundFragmentView {
    Context context;
    FragmentFoundBinding binding;
    MenuAdapter menuAdapter;
    FoundFragmentPresenter foundFragmentPresenter;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        context = FoundFragment.this.getActivity();
        foundFragmentPresenter = new FoundFragmentPresenter(this.getActivity(),this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_found,container,false);
        listView = binding.menu;
        foundFragmentPresenter.init();
        return binding.getRoot();
    }
    @Override
    public void init(List<Menu> list) {
        menuAdapter = new MenuAdapter(context,list);
        listView.setAdapter(menuAdapter);
    }
    @Override
    public void updateData(List<Menu> list){
        menuAdapter.setData(list);
        menuAdapter.notifyDataSetChanged();
    }
}
