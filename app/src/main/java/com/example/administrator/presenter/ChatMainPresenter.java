package com.example.administrator.presenter;

import com.example.administrator.activity.ChatMainActivity;
import com.example.administrator.dbDao.MessageTableDao;
import com.example.administrator.interfaceview.IUChatMainView;

/**
 * Created by dell on 2017/4/22.
 */

public class ChatMainPresenter extends  BasePresenter {
    private IUChatMainView chatMainView;
    private MessageTableDao messagetableDao;
    public ChatMainPresenter(ChatMainActivity context, IUChatMainView chatMainView) {
        super(context);
        this.chatMainView = chatMainView;
        messagetableDao = new MessageTableDao();
    }

    public void init() {
        chatMainView.init(messagetableDao.select(userInfo.getUid(),userInfo.getUid()));
    }
//    public void insert(){
//        for (int i = 2; i > 0; i--) {
//            MessageTable messageTable = new MessageTable();
//            messageTable.setId(i+"3");
//            messageTable.setTag(i+"");
//            messageTable.setBid(null);
//            messageTable.setContent("{\"id\":\"oj5lbxcnlbx0lpgrep3mdrs5jxki\",\"name\":\"\\u6211\\u8fd8\\u6709\\u5f69\\u8679\\u7684\\u989c\\u8272\",\"url\":\"http:\\/\\/139.224.57.105\\/im2\\/Uploads\\/Picture\\/avatar\\/1\\/s_ed20d14cee93616d401095f6fb2e9ea9.jpg\"}");
//            messageTable.setTypechat(100);
//            messageTable.setTypefile(1);
//            messageTable.setTime(System.currentTimeMillis());
//            messageTable.setFromid(userInfo.getUid());
//            messageTable.setUid(userInfo.getUid());
//            messageTable.setFrom("{\"id\":\"oj5lbxcnlbx0lpgrep3mdrs5jxki\",\"name\":\"\\u6211\\u8fd8\\u6709\\u5f69\\u8679\\u7684\\u989c\\u8272\",\"url\":\"http:\\/\\/139.224.57.105\\/im2\\/Uploads\\/Picture\\/avatar\\/1\\/s_ed20d14cee93616d401095f6fb2e9ea9.jpg\"}");
//            messageTable.setTo("{\"id\":\"oj5lbxcnlbx0lpgrep3mdrs5jxki\",\"name\":\"\\ud83c\\udf3a\\u5c0f\\u73b2\\ud83c\\udf3a\\ud83d\\ude97\",\"url\":\"http:\\/\\/139.224.57.105\\/im2\\/Uploads\\/Picture\\/avatar\\/1\\/s_ed20d14cee93616d401095f6fb2e9ea9.jpg\"}");
//            messageTable.setImage("");
//            messageTable.setVoice("");
//            messageTable.setLocation("");
//            messageTable.setRedpacket("");
//            messageTable.setReadState(0);
//            messagetableDao.insert(messageTable);
//        }
//    }
}
