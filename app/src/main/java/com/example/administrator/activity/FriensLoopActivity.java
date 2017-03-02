package com.example.administrator.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.fragment.AbAlertDialogFragment;
import com.ab.util.AbDialogUtil;
import com.example.administrator.R;
import com.example.administrator.adapter.FriensLoopAdapter;
import com.example.administrator.databinding.FriendsLoopHeaderBinding;
import com.example.administrator.databinding.FriensLoopBinding;
import com.example.administrator.entity.FriendsLoopItem;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUFriensLoopView;
import com.example.administrator.presenter.FriensLoopPresenter;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.ImageUitl;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.tandong.sa.bv.BelowView;
import com.tandong.sa.view.AutoReFreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/25.
 */

public class FriensLoopActivity extends BaseActivity implements IUFriensLoopView {
    //刷新数据的广播
    final public static String  REFRESH_FRIENSLOOP_DATA="refresh_friensloop_data_action";
    private AutoReFreshListView mListView;
    FriensLoopAdapter friensLoopAdapter;
    FriensLoopBinding binding;
    FriendsLoopHeaderBinding friendsLoopHeaderBinding;
    FriensLoopPresenter friensLoopPresenter;
    Context context;
    String type;
    private Button[] mTabs;
    private int page=1;
    private ArrayList<FriendsLoopItem> dataList = new ArrayList<>();
    private int position;
    private String toUid,toName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = FriensLoopActivity.this;
        ImageUitl.init(context);
        friensLoopPresenter = new FriensLoopPresenter(context,this);
        binding =  DataBindingUtil.setContentView(this,R.layout.friens_loop);
        mListView = binding.mListView;
        initView();
        friensLoopPresenter.getData(null,FriensLoopPresenter.INITDATA,1);
    }
    public void init(ArrayList<FriendsLoopItem> mlist) {
        friensLoopAdapter = new FriensLoopAdapter(context,mlist,FriensLoopActivity.this,friensLoopPresenter);
        dataList = mlist;
        mListView.setAdapter(friensLoopAdapter);
        mListView.setOnRefreshListener(new AutoReFreshListView.OnRefreshListener() {// 上拉刷新
            public void onRefresh() {
                friensLoopPresenter.getData(type,FriensLoopPresenter.REFRESHDATA,1);
            }
        });
        mListView.setOnLoadListener(new AutoReFreshListView.OnLoadMoreListener() {// 下拉加载更多
            @Override
            public void onLoadMore() {
                friensLoopPresenter.getData(type,FriensLoopPresenter.LOADDATA,++page);
            }
        });
        registerReceiver();
    }

    /**
     * 注册通知
     */
    private void registerReceiver(){
        IntentFilter filter  = new IntentFilter();
        filter.addAction(REFRESH_FRIENSLOOP_DATA);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    /**
     * 处理通知
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action  = intent.getAction();
            if (action.equals(REFRESH_FRIENSLOOP_DATA)) {
                friensLoopPresenter.getData(type,FriensLoopPresenter.REFRESHDATA,1);
            }
        }
    };
    /**
     * 背景的头部信息
     */
    @Override
    public void initHeader() {
        UserInfo userInfo = GetDataUtil.getUserInfo(context);
        friendsLoopHeaderBinding =  DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.friends_loop_header,null,false);
        friendsLoopHeaderBinding.setBehavior(this);
        friendsLoopHeaderBinding.setUserinfo(userInfo);
        if(!StringUtil.isNull(userInfo.getCover())){
            friendsLoopHeaderBinding.setCoverHint.setVisibility(View.GONE);
            ImageUitl.setImage(friendsLoopHeaderBinding.imgBg,userInfo.getCover());
        }else{
            friendsLoopHeaderBinding.imgBg.setImageDrawable(getResources().getDrawable(R.mipmap.head_img));
        }
        mListView.addHeaderView(friendsLoopHeaderBinding.getRoot());
    }

    /**
     * 点击是否更换背景
     * @param view
     */
    public void updateBg(View view){
        AbDialogUtil.showAlertDialog(context, "更换背景", "是否更换背景？", new AbAlertDialogFragment.AbDialogOnClickListener() {
            @Override
            public void onPositiveClick() {
                UIUtil.openImagePicker(FriensLoopActivity.this);
            }
            @Override
            public void onNegativeClick() {}
        });
    }

    /**
     * 发送评论
     * @param view
     */
    public void sentReply(View view){
        String str = binding.edit.getText().toString();
        hidePinLun();
        binding.edit.setText("");
        friensLoopPresenter.sentReply(dataList,position,toUid,toName,str);
    }
    /**
     * 加载成功时显示数据
     * @param mlist
     */
    @Override
    public void loadsuccess(ArrayList<FriendsLoopItem> mlist) {
        mListView.onLoadMoreComplete();
        dataList.addAll(mlist);
        friensLoopAdapter.setData(dataList);
        friensLoopAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新成功时加载数据
     * @param mlist
     */
    @Override
    public void refreshsuccess(ArrayList<FriendsLoopItem> mlist) {
        mListView.onRefreshComplete();
        friensLoopAdapter.setData(mlist);
        friensLoopAdapter.notifyDataSetChanged();
    }

    /**
     * 显示评论控件
     */
    @Override
    public void showPinLun(int position,String toUid,String toName,String hid) {
        binding.bottomMenu.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(binding.bottomMenu.getWidth(), 0, 0, 0);
        animation.setDuration(500);
        animation.setAnimationListener(mAnimationListener);
        binding.bottomMenu.startAnimation(animation);
        binding.typeBottomMenu.setVisibility(View.GONE);
        this.position = position;
        this.toUid = toUid;
        this.toName = toName;
        if(!StringUtil.isNull(hid)){
            binding.edit.setHint(hid);
        }
    }
    Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }
        @Override
        public void onAnimationRepeat(Animation animation) {
        }
        @Override
        public void onAnimationEnd(Animation animation) {
            //动画加载完成之后，获输入框获得焦点，并弹出输入法
            binding.bottomMenu.clearAnimation();
            binding.edit.setFocusable(true);
            binding.edit.setFocusableInTouchMode(true);
            binding.edit.requestFocus();
            InputMethodManager  inputManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(binding.edit, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    };

    /**
     * 隐藏评论控件
     */
    @Override
    public void hidePinLun() {
        binding.bottomMenu.setVisibility(View.GONE);
        binding.typeBottomMenu.setVisibility(View.VISIBLE);
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 初始化控件以及参数
     */
    private void initView(){
        ((TextView)binding.titleLayout.findViewById(R.id.titlecontext)).setText("商机圈");
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriensLoopActivity.this.finish();
            }
        });
        TextView tvRight = ((TextView)binding.titleLayout.findViewById(R.id.right_text));
        tvRight.setText("商机发布");
        tvRight.setOnClickListener(new View.OnClickListener() {
            BelowView blv = new BelowView(FriensLoopActivity.this, R.layout.send_shanji);
            @Override
            public void onClick(View view) {
                blv.showBelowView(view, true, 30, 0);
                View v =  blv.getBelowView();
                v.findViewById(R.id.sendfriensloop_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,SendFriensLoopActivity.class);
                        startActivity(intent);
                        blv.dismissBelowView();
                    }
                });
            }
        });
        mTabs = new Button[5];
        mTabs[0] = binding.live;
        mTabs[1] = binding.qiye;
        mTabs[2] = binding.btnZhuye;
        mTabs[3] = binding.weishan;
        mTabs[4] = binding.huodong;
        mTabs[2].setSelected(true);
        initHeader();
    }
    private int index,currentTabIndex=2;
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.live:
                index = 0;
                type="生活";
                break;
            case R.id.qiye:
                index = 1;
                type="企业";
                break;
            case R.id.btn_zhuye:
                index = 2;
                type="";
                break;
            case R.id.weishan:
                index = 3;
                type="微商";
                break;
            case R.id.huodong:
                index = 4;
                type="活动";
                break;
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
        friensLoopPresenter.getData(type,FriensLoopPresenter.INITDATA,1);
    }
    @Override
    public void showLoading() {
        super.showLoading("加载中...");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case ImageSelector.IMAGE_REQUEST_CODE:
                    if (data != null) {
                        // 获取选中的图片路径列表 Get Images Path List
                        List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                        for (String path : pathList) {
                            friensLoopPresenter.setImageBg(path);
                            friendsLoopHeaderBinding.imgBg.setImageBitmap(BitmapFactory.decodeFile(path));
                        }
                    }
                    break;
            }
        }
    }
}
