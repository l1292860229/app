package com.example.administrator.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.R;
import com.example.administrator.adapter.ChatAdapter;
import com.example.administrator.databinding.ChatFragmentBinding;
import com.example.administrator.entity.Session;
import com.example.administrator.interfaceview.IUChatFragmentView;
import com.example.administrator.util.ImageUitl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class ChatFragment extends Fragment implements IUChatFragmentView {
    ChatFragmentBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        ImageUitl.init(ChatFragment.this.getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_fragment,container,false);
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
        binding.chatsList.setAdapter(new ChatAdapter(ChatFragment.this.getActivity(),mlist));
    }
}
