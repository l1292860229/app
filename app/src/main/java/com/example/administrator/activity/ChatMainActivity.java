package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import com.example.administrator.R;
import com.example.administrator.databinding.ChatBoxBinding;
import com.example.administrator.databinding.ChatBoxExpraBinding;
import com.example.administrator.databinding.ChatMainBinding;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.entity.constant.Constants;
import com.example.administrator.util.FileUtil;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.KeyBoardUtils;
import com.example.administrator.util.MediaManager;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;

import java.io.File;

/**
 * Created by Administrator on 2017/3/14.
 */

public class ChatMainActivity extends BaseActivity {
    //intent key值
    public final static String NAME="name";
    public final static String TOID="toid";
    public final static String TOHEAD="tohead";
    //onActivityResult
    public final static int GET_IMAGE_BY_CAMERA=1;
    public final static int GET_MAP=2;
    public final static int GET_MYFAVORITE=3;
    public final static int GET_USERINFO=4;
    Context context;
    ChatMainBinding binding;
    protected UserInfo userInfo;
    protected String CameraImagePath;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        userInfo = GetDataUtil.getUserInfo(context);
        binding =  DataBindingUtil.setContentView(this, R.layout.chat_main);
        binding.setBehavior(this);
        binding.titleLayout.setBehavior(this);
        binding.chatMainListMsg.setCanLoadMore(false);
        initializeView();
    }


    /**
     * 初始化控件
     */
    public void initializeView(){
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
                    KeyBoardUtils.closeKeybord(chatbox.chatBoxEditKeyword,context);
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
                    KeyBoardUtils.closeKeybord(chatbox.chatBoxEditKeyword,context);
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
                    KeyBoardUtils.closeKeybord(chatbox.chatBoxEditKeyword,context);
                }else{
                    binding.emojicons.setVisibility(View.GONE);
                }
            }
        });
        //弹出输入法时隐藏表情框还有选项框
        chatbox.chatBoxEditKeyword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    chatbox.chatBoxBtnAdd.setChecked(false);
                    chatbox.chatBoxBtnEmoji.setChecked(false);
                }
            }
        });
        chatbox.chatBoxEditKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatbox.chatBoxBtnAdd.setChecked(false);
                chatbox.chatBoxBtnEmoji.setChecked(false);
            }
        });
        chatbox.chatBoxEditKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String text  = chatbox.chatBoxEditKeyword.getText().toString();
                if(StringUtil.isNull(text)){
                    chatbox.chatBoxBtnAdd.setVisibility(View.VISIBLE);
                    chatbox.chatBoxBtnSend.setVisibility(View.GONE);
                }else{
                    chatbox.chatBoxBtnAdd.setVisibility(View.GONE);
                    chatbox.chatBoxBtnSend.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    /**
     * 发送文字消息
     * @param view
     */
    public void sentTextMessage(View view){
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
            CameraImagePath = out.getPath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, GET_IMAGE_BY_CAMERA);
        }
    }
    /**
     * 打开相册
     * @param view
     */
    public void openAlbum(View view){
        UIUtil.openImagePickers(ChatMainActivity.this,9,false);
    }
    /**
     * 打开地图
     * @param view
     */
    public void openMap(View view){
        Intent intent = new Intent(this,BaiduMapActivity.class);
        startActivityForResult(intent,GET_MAP);
    }
    /**
     * 打开名片
     * @param view
     */
    public void openBusinessCard(View view){
        Intent intent = new Intent(this,ChooseUserActivity.class);
        startActivityForResult(intent,GET_USERINFO);
    }

    /**
     * 打开收藏
     * @param view
     */
    public void openCollection(View view){
        Intent intent = new Intent(context, MyFavoriteActivity.class);
        intent.putExtra(MyFavoriteActivity.CANCLICK,true);
        startActivityForResult(intent,GET_MYFAVORITE);
    }

    @Override
    protected void onDestroy() {
        MediaManager.stop();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            //返回时,会变成true
            binding.chatMainListMsg.setCanLoadMore(false);
            binding.chatBox.chatBoxBtnAdd.setChecked(false);
        }
    }
}
