package com.example.administrator.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.R;
import com.example.administrator.activity.BBSChatMainActivity;
import com.example.administrator.activity.ChatMainActivity;
import com.example.administrator.activity.IMChatMainActivity;
import com.example.administrator.activity.MainActivity;
import com.example.administrator.adapter.ChatFragmentAdapter;
import com.example.administrator.databinding.ChatFragmentBinding;
import com.example.administrator.entity.Bbs;
import com.example.administrator.entity.Session;
import com.example.administrator.enumset.TypeChat;
import com.example.administrator.interfaceview.IUChatFragmentView;
import com.example.administrator.presenter.ChatFragmentPresenter;
import com.example.administrator.util.UIUtil;
import com.jpeng.jptabbar.JPTabBar;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class ChatFragment extends Fragment implements IUChatFragmentView {
    //刷新数据的广播
    final public static String  REFRESH_SESSION_DATA="refresh_session_data_action";
    ChatFragmentBinding binding;
    private SlideAndDragListView chatListView;
    private Context context;
    private ChatFragmentPresenter chatFragmentPresenter;
    private ChatFragmentAdapter mChatFragmentAdapter;
    private JPTabBar jpTabBar;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        context = ChatFragment.this.getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_fragment,container,false);
        chatListView = binding.chatsList;
        chatFragmentPresenter = new ChatFragmentPresenter(this.getActivity(),this);
        jpTabBar = ((MainActivity)this.getActivity()).getTabbar();
        chatFragmentPresenter.init();
        return binding.getRoot();
    }

    @Override
    public void init(final List<Session> mlist) {
        mChatFragmentAdapter = new ChatFragmentAdapter(ChatFragment.this.getActivity(),mlist);
        //设置左滑菜单
        chatListView.setMenu(getMenuList());
        chatListView.setAdapter(mChatFragmentAdapter);
        chatListView.setOnListItemClickListener(new SlideAndDragListView.OnListItemClickListener() {
            @Override
            public void onListItemClick(View view, int position) {
                Intent intent = new Intent(context, IMChatMainActivity.class);
                intent.putExtra(ChatMainActivity.NAME,mlist.get(position).getName());
                intent.putExtra(ChatMainActivity.TOID,mlist.get(position).getToid());
                intent.putExtra(ChatMainActivity.TOHEAD,mlist.get(position).getHeading());
                if (mlist.get(position).getTypechat() == TypeChat.SINGLE.getValue()) {
                    intent.putExtra(IMChatMainActivity.TYPECHAT, TypeChat.SINGLE);
                }else if(mlist.get(position).getTypechat() == TypeChat.GROUP.getValue()){
                    intent.putExtra(IMChatMainActivity.TYPECHAT, TypeChat.GROUP);
                }else if(mlist.get(position).getTypechat() == TypeChat.BBS.getValue()){
                    intent.setClass(context, BBSChatMainActivity.class);
                    intent.putExtra(ChatMainActivity.NAME,mlist.get(position).getName());
                    intent.putExtra(ChatMainActivity.TOID,mlist.get(position).getToid());
                    intent.putExtra(ChatMainActivity.TOHEAD,mlist.get(position).getHeading());
                    intent.putExtra(BBSChatMainActivity.BBSTYPE, Bbs.Bbstype.INDUSTRY);
                    startActivity(intent);
                    return;
                }
                startActivity(intent);
            }
        });
        chatListView.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
            @Override
            public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
                Session session = mChatFragmentAdapter.getData().get(itemPosition);
                switch (direction) {
                    case MenuItem.DIRECTION_RIGHT:
                        switch (buttonPosition) {
                           case 0:
                               chatFragmentPresenter.delSession(session.getId());
                                return Menu.ITEM_SCROLL_BACK;
                            case 1:
                                if (session.itemViewType()== Session.READ_TOP
                                        ||session.itemViewType()== Session.READ_UNTOP) {
                                    chatFragmentPresenter.updateSessionUnReadCount(session,1);
                                }else{
                                    chatFragmentPresenter.updateSessionUnReadCount(session,0);
                                }
                                return Menu.ITEM_SCROLL_BACK;
                            case 2:
                                if (session.itemViewType()== Session.READ_TOP
                                        ||session.itemViewType()== Session.UNREAD_TOP) {
                                    chatFragmentPresenter.setSessionTop(session,0);
                                }else{
                                    chatFragmentPresenter.setSessionTop(session,1);
                                }
                                return Menu.ITEM_SCROLL_BACK;
                        }
                        break;
                }
                return Menu.ITEM_NOTHING;
            }
        });
        registerReceiver();
    }

    @Override
    public void load(List<Session> mlist) {
        mChatFragmentAdapter.setData(mlist);
        mChatFragmentAdapter.notifyDataSetChanged();
    }
    //设置未读消息个数
    @Override
    public void setUnReadCount(int count) {
        jpTabBar.showBadge(0,count,true);
    }
    /**
     * 获取四种不同的状态菜单栏信息
     * @return
     */
    public List<Menu> getMenuList(){
        ArrayList<Menu> menus = new ArrayList<>();
        Menu mMenu = new Menu(true, false,Session.READ_UNTOP);
        mMenu.addItem(getMenuItem("删除",Color.RED));
        mMenu.addItem(getMenuItem("标为未读",Color.GREEN));
        mMenu.addItem(getMenuItem("置顶",Color.GRAY));
        menus.add(mMenu);
        mMenu = new Menu(true, false,Session.UNREAD_UNTOP);
        mMenu.addItem(getMenuItem("删除",Color.RED));
        mMenu.addItem(getMenuItem("标为已读",Color.GREEN));
        mMenu.addItem(getMenuItem("置顶",Color.GRAY));
        menus.add(mMenu);
        mMenu = new Menu(true, false,Session.UNREAD_TOP);
        mMenu.addItem(getMenuItem("删除",Color.RED));
        mMenu.addItem(getMenuItem("标为已读",Color.GREEN));
        mMenu.addItem(getMenuItem("取消置顶",Color.GRAY));
        menus.add(mMenu);
        mMenu = new Menu(true, false,Session.READ_TOP);
        mMenu.addItem(getMenuItem("删除",Color.RED));
        mMenu.addItem(getMenuItem("标为未读",Color.GREEN));
        mMenu.addItem(getMenuItem("取消置顶",Color.GRAY));
        menus.add(mMenu);
        return menus;
    }

    /**
     * 设置listView左滑时的菜单
     * @param title
     * @param color
     * @return
     */
    public MenuItem getMenuItem(String title,int color){
        return UIUtil.getMenu(title,color,150,Color.WHITE,14,MenuItem.DIRECTION_RIGHT);
    }
    /**
     * 处理通知
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action  = intent.getAction();
            if (action.equals(REFRESH_SESSION_DATA)) {
                chatFragmentPresenter.init();
            }
        }
    };
    /**
     * 注册通知
     */
    private void registerReceiver(){
        IntentFilter filter  = new IntentFilter();
        filter.addAction(REFRESH_SESSION_DATA);
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
