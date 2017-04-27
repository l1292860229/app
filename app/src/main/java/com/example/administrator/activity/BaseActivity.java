package com.example.administrator.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ab.fragment.AbLoadDialogFragment;
import com.ab.util.AbDialogUtil;
import com.example.administrator.R;
import com.example.administrator.util.UIUtil;
import com.smartandroid.sa.bv.BelowView;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;

/**
 * Created by Administrator on 2017/3/2.
 */

public class BaseActivity extends AppCompatActivity implements EmojiconsFragment.OnEmojiconBackspaceClickedListener,EmojiconGridFragment.OnEmojiconClickedListener {
    final public static String  CLOSE_ACTIVITY="close_activity_action";
    AbLoadDialogFragment loadingfragment;
    protected BelowView blv;
    EmojiconEditText emojiconEditText;//支持表情的输入框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colseregisterReceiver();
    }

    public void showLoading(String msg) {
        loadingfragment =  AbDialogUtil.showLoadDialog(BaseActivity.this, R.mipmap.ic_load, msg);
    }

    public void hideLoading() {
        loadingfragment.dismiss();
        loadingfragment.onDestroyView();
    }
    /**
     * 处理通知
     */
    private BroadcastReceiver closereceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action  = intent.getAction();
            if (action.equals(CLOSE_ACTIVITY)) {
                Intent intent1 =new Intent(BaseActivity.this,LoginActivity.class);
                startActivity(intent1);
                UIUtil.showMessage(BaseActivity.this,"你的账号在其他设备登陆");
                Log.e("closereceiver","closereceiver");
                finish();
            }
        }
    };
    /**
     * 注册通知
     */
    private void colseregisterReceiver(){
        IntentFilter filter  = new IntentFilter();
        filter.addAction(CLOSE_ACTIVITY);
        registerReceiver(closereceiver,filter);
    }

    /**
     * 关闭当前View
     * @param view
     */
    public void close(View view){
        finish();
    }

    /**
     * left_btn2 的操作
     * @param view
     */
    public void left_btn2(View view){
        // TODO: 2017/3/20
    }
    /**
     * right_btn 的操作
     * @param view
     */
    public void right_btn(View view){
        // TODO: 2017/3/20
    }
    /**
     * right_btn2 的操作
     * @param view
     */
    public void right_btn2(View view){
        // TODO: 2017/3/20
    }
    /**
     * right_text 的操作
     * @param view
     */
    public void right_text(View view){
        // TODO: 2017/3/20
    }
    /**
     * 点表情时将表情添加对应的文本框
     */
    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(emojiconEditText, emojicon);
    }
    /**
     * 表情框里面的删除键
     * @param view
     */
    @Override
    public void onEmojiconBackspaceClicked(View view) {
        EmojiconsFragment.backspace(emojiconEditText);
    }

    /**
     * 设置表情支持
     * @param emojiconEditText
     * @param resourceId Fragment的id
     */
    public void setEmojiconFragment(EmojiconEditText emojiconEditText,int resourceId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(resourceId, EmojiconsFragment.newInstance(false))
                .commit();
        this.emojiconEditText =  emojiconEditText;
    }

    @Override
    protected void onDestroy() {
        if(closereceiver!=null){
            unregisterReceiver(closereceiver);
        }
        super.onDestroy();
    }
}
