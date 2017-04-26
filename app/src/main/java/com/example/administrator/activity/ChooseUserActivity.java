package com.example.administrator.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.R;
import com.example.administrator.UIView.SideBar;
import com.example.administrator.adapter.ChooseUserAdapter;
import com.example.administrator.databinding.ChooseUserBinding;
import com.example.administrator.entity.SortUserInfo;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUChooseUserView;
import com.example.administrator.presenter.ChooseUserPresenter;
import com.example.administrator.util.CharacterParser;
import com.example.administrator.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by dell on 2017/4/25.
 */

public class ChooseUserActivity extends BaseActivity implements IUChooseUserView {
    public final  static String DATA ="data";
    public final  static String MULTIPLE ="multiple";
    ChooseUserBinding binding;
    ChooseUserPresenter chooseUserPresenter;
    ChooseUserAdapter userAdapter;
    //根据拼音来排列ListView里面的数据类
    private PinyinComparator pinyinComparator;
	//汉字转换成拼音的类
    private CharacterParser characterParser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this, R.layout.choose_user);
        binding.titlelayout.setBehavior(this);
        chooseUserPresenter = new ChooseUserPresenter(this,this);
        pinyinComparator = new PinyinComparator();
        characterParser = CharacterParser.getInstance();
        chooseUserPresenter.init();
    }

    @Override
    public void init(final ArrayList<UserInfo> userInfos,boolean isMultiple) {
        binding.titlelayout.titlecontext.setText("选择联系人");
        final ArrayList<SortUserInfo> sortUserInfos = setUserInfoSort(userInfos);
        userAdapter = new ChooseUserAdapter(this, sortUserInfos,isMultiple);
        binding.contactList.setAdapter(userAdapter);
        binding.sidrbar.setTextView(binding.dialog);
        binding.sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = userAdapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    binding.contactList.setSelection(position);
                }
            }
        });
        binding.contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(DATA, sortUserInfos.get(position).getUserInfo());
                setResult(RESULT_OK,intent);
                ChooseUserActivity.this.finish();
            }
        });
        if(isMultiple){
            binding.titlelayout.rightText.setText("确定");
        }
    }

    @Override
    public void right_text(View view) {
        if(userAdapter.getSelectUserInfoList().size()==0){
            return;
        }
        chooseUserPresenter.createGroup(userAdapter.getSelectUserInfoList());
    }

    public ArrayList<SortUserInfo> setUserInfoSort(ArrayList<UserInfo> userInfos){
        ArrayList<SortUserInfo> sortUserInfos = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            SortUserInfo sortUserInfo = new SortUserInfo();
            sortUserInfo.setUserInfo(userInfo);
            String name;
            if(StringUtil.isNull(userInfo.getRemark())){
                name = userInfo.getNickname();
            }else{
                name = userInfo.getRemark();
            }
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(name);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortUserInfo.setSort(sortString.toUpperCase());
            }else{
                sortUserInfo.setSort("#");
            }
            sortUserInfos.add(sortUserInfo);
        }
        Collections.sort(sortUserInfos, pinyinComparator);
        return sortUserInfos;
    }

    public class PinyinComparator implements Comparator<SortUserInfo> {

        public int compare(SortUserInfo o1, SortUserInfo o2) {
            if (o1.getSort().equals("@")
                    || o2.getSort().equals("#")
                    ) {
                return -1;
            } else if (o1.getSort().equals("#")
                    || o2.getSort().equals("@")) {
                return 1;
            }else if( o1.getSort().equals("↑")
                    || o2.getSort().equals("☆")){
                return 1;
            }else if( o1.getSort().equals("☆")
                    || o2.getSort().equals("↑")){
                return 2;
            }
            else {
                return o1.getSort().compareTo(o2.getSort());
            }
        }
    }
}
