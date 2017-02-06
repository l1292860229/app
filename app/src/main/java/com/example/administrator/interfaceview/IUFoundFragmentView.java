package com.example.administrator.interfaceview;

import com.example.administrator.entity.Menu;

import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public interface IUFoundFragmentView {
    void init(List<Menu> list);
    public void updateData(List<Menu> list);
}
