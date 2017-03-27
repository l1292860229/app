package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CompoundButton;

import com.example.administrator.R;
import com.example.administrator.databinding.ChatBoxBinding;
import com.example.administrator.databinding.ChatBoxExpraBinding;
import com.example.administrator.databinding.ChatMainBinding;
import com.example.administrator.entity.Constants;
import com.example.administrator.interfaceview.IUChatMainView;
import com.example.administrator.util.FileUtil;
import com.example.administrator.util.UIUtil;

import java.io.File;

/**
 * Created by Administrator on 2017/3/14.
 */

public class ChatMainActivity extends BaseActivity implements IUChatMainView{
    Context context;
    ChatMainBinding binding;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding =  DataBindingUtil.setContentView(this, R.layout.chat_main);
        binding.setBehavior(this);
        init();
    }

    @Override
    public void init() {
        initializeView();
    }

    /**
     * 初始化控件
     */
    public void initializeView(){
        binding.titleLayout.setBehavior(this);
        final ChatBoxBinding chatbox =  binding.chatBox;
        final ChatBoxExpraBinding chatBoxExpraBinding =  binding.chatBoxExpra;
        //设置表情框支持
        setEmojiconFragment(chatbox.chatBoxEditKeyword,R.id.emojicons);
        chatbox.setBehavior(this);
        chatBoxExpraBinding.setBehavior(this);
        //语音切换
        chatbox.chatBoxBtnInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chatbox.chatBoxEditKeyword.setVisibility(View.GONE);
                    chatbox.chatBoxBtnVoice.setVisibility(View.VISIBLE);
                    //要同时隐藏表情框和下拉框
                    chatbox.chatBoxBtnAdd.setChecked(false);
                    chatbox.chatBoxBtnEmoji.setChecked(false);
                }else{
                    chatbox.chatBoxEditKeyword.setVisibility(View.VISIBLE);
                    chatbox.chatBoxBtnVoice.setVisibility(View.GONE);
                }
            }
        });
        //显示下拉框
        chatbox.chatBoxBtnAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chatBoxExpraBinding.chatBoxLayoutExpra.setVisibility(View.VISIBLE);
                    //要隐藏表情框
                    binding.emojicons.setVisibility(View.GONE);
                    chatbox.chatBoxBtnEmoji.setChecked(false);
                }else{
                    chatBoxExpraBinding.chatBoxLayoutExpra.setVisibility(View.GONE);
                }
            }
        });
        //显示表情框
        chatbox.chatBoxBtnEmoji.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.emojicons.setVisibility(View.VISIBLE);
                    //要隐藏下拉框,同时显示输入框
                    chatbox.chatBoxBtnAdd.setChecked(false);
                    chatbox.chatBoxBtnInfo.setChecked(false);
                }else{
                    binding.emojicons.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 打开相机
     * @param view
     */
    public void openCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String tmpUrl = System.currentTimeMillis()+".jpg";
        if(FileUtil.newFolder(Environment.getExternalStorageDirectory() + Constants.TEMP_DIRECTORY)){
            File out = new File(Environment.getExternalStorageDirectory() + Constants.TEMP_DIRECTORY,
                    tmpUrl);
            Uri uri = Uri.fromFile(out);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivity(intent);
            //startActivityForResult(intent, REQUEST_GET_IMAGE_BY_CAMERA);
        }
    }

    /**
     * 打开相册
     * @param view
     */
    public void openAlbum(View view){
        UIUtil.openImagePickers(ChatMainActivity.this,9);
    }
    /**
     * 打开地图
     * @param view
     */
    public void openMap(View view){

    }
    /**
     * 打开名片
     * @param view
     */
    public void openBusinessCard(View view){

    }

    /**
     * 打开收藏
     * @param view
     */
    public void openCollection(View view){

    }
}
