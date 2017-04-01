package com.example.administrator.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.R;
import com.example.administrator.activity.ChatMainActivity;
import com.example.administrator.adapter.ChatAdapter;
import com.example.administrator.databinding.ChatFragmentBinding;
import com.example.administrator.entity.Session;
import com.example.administrator.interfaceview.IUChatFragmentView;
import com.example.administrator.util.ImageUitl;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class ChatFragment extends Fragment implements IUChatFragmentView {
    ChatFragmentBinding binding;
    private SlideAndDragListView chatListView;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        context = ChatFragment.this.getActivity();
        ImageUitl.init(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_fragment,container,false);
        chatListView = binding.chatsList;
        init();
        return binding.getRoot();
    }

    @Override
    public void init() {
        List<Session> mlist = new ArrayList<>();
        Session session  = new  Session();
        session.setFromId("2");
        session.setType(0);
        session.setName("小刚");
        session.setHeading("http://139.224.57.105/im2/Uploads/Picture/avatar/18/s_6f0542e07dad7f7a0a655f799b94bc43.jpg");
        session.setUnreadcount(3);
        mlist.add(session);
        //设置左滑菜单
        Menu mMenu = new Menu(true, true);
        mMenu.addItem(getMenu("删除",Color.RED));
        mMenu.addItem(getMenu("标为未读",Color.GREEN));
        mMenu.addItem(getMenu("置顶",Color.GRAY));
        chatListView.setMenu(mMenu);
        chatListView.setAdapter(new ChatAdapter(ChatFragment.this.getActivity(),mlist));
        chatListView.setOnListItemClickListener(new SlideAndDragListView.OnListItemClickListener() {
            @Override
            public void onListItemClick(View view, int position) {
                Intent intent = new Intent(context, ChatMainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置listView左滑时的菜单
     * @param title
     * @param color
     * @return
     */
    public MenuItem getMenu(String title,int color){
        return new MenuItem.Builder().setWidth(150)
                .setText(title)
                .setBackground(new ColorDrawable(color))
                .setTextColor(Color.WHITE)
                .setTextSize(14)
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .build();
    }
}
