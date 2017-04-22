package com.example.administrator.presenter;

import android.app.Activity;

import com.example.administrator.dbDao.SessionDao;
import com.example.administrator.entity.Session;
import com.example.administrator.interfaceview.IUChatFragmentView;

import java.util.List;

/**
 * Created by dell on 2017/4/21.
 */

public class ChatFragmentPresenter extends BaseFragmentPresenter {
    private IUChatFragmentView chatFragmentView;
    private SessionDao sessionDao;
    public ChatFragmentPresenter(Activity activity, IUChatFragmentView chatFragmentView) {
        super(activity);
        this.chatFragmentView = chatFragmentView;
        this.sessionDao = new SessionDao();
    }
    public void init(){
        List<Session> mlist = sessionDao.select(userInfo.getUid());
        chatFragmentView.init(mlist);
        getUnReadCount();
    }
    public void refresh(){
        List<Session> mlist = sessionDao.select(userInfo.getUid());
        chatFragmentView.load(mlist);
    }
    public void getUnReadCount(){
        chatFragmentView.setUnReadCount(sessionDao.getUnReadCount(userInfo.getUid()));
    }
    public void delSession(String sessionId){
        sessionDao.delete(sessionId);
        refresh();
    }
    public void updateSessionUnReadCount(Session session,int unReadCount){
        session.setUnreadcount(unReadCount);
        sessionDao.insertOrUpdate(session);
        refresh();
        getUnReadCount();
    }
    public void clearSessionUnReadCount(){
        sessionDao.clearReadCount(userInfo.getUid());
        refresh();
        getUnReadCount();
    }
    public void setSessionTop(Session session,int top){
        session.setIsTop(top);
        sessionDao.insertOrUpdate(session);
        refresh();
    }
}
