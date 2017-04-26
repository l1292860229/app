package com.example.administrator.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ab.fragment.AbLoadDialogFragment;
import com.ab.util.AbDialogUtil;
import com.example.administrator.R;
import com.smartandroid.sa.bv.BelowView;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;

/**
 * Created by Administrator on 2017/3/2.
 */

public class BaseActivity extends AppCompatActivity implements EmojiconsFragment.OnEmojiconBackspaceClickedListener,EmojiconGridFragment.OnEmojiconClickedListener {
    AbLoadDialogFragment loadingfragment;
    protected BelowView blv;
    EmojiconEditText emojiconEditText;//支持表情的输入框
    public void showLoading(String msg) {
        loadingfragment =  AbDialogUtil.showLoadDialog(BaseActivity.this, R.mipmap.ic_load, msg);
    }

    public void hideLoading() {
        loadingfragment.dismiss();
        loadingfragment.onDestroyView();
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
}
