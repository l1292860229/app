package com.example.administrator.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.fragment.AbAlertDialogFragment;
import com.ab.util.AbDialogUtil;
import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.databinding.FriendsLoopItemBinding;
import com.example.administrator.entity.CommentUser;
import com.example.administrator.entity.FriendsLoopItem;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUFriensLoopView;
import com.example.administrator.presenter.FriensLoopPresenter;
import com.example.administrator.util.DateUtil;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class FriensLoopAdapter extends BaseAdapter{
    private Context context;
    private List<FriendsLoopItem> list;
    private LinearLayout temp;
    private IUFriensLoopView friensLoopView;
    private FriensLoopPresenter friensLoopPresenter;
    public FriensLoopAdapter(Context context, List<FriendsLoopItem> list
            ,IUFriensLoopView friensLoopView,FriensLoopPresenter friensLoopPresenter) {
        this.context = context;
        this.list = list;
        this.friensLoopView = friensLoopView;
        this.friensLoopPresenter = friensLoopPresenter;
    }
    public void setData(List<FriendsLoopItem> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final  int position, View convertView, ViewGroup parent) {
        FriendsLoopItemBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.friends_loop_item, parent, false);
            binding.otherLayout.setTag(list.get(position).getCreatetime()+"");
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(BR.friendsLoopItem, list.get(position));
        //设置常量
        final FriendsLoopItem friendsLoopItem = list.get(position);
        final  FriendsLoopItemBinding bindingFinal = binding;
        //如果有地址就显示
        if (!StringUtil.isNull(friendsLoopItem.getAddress())) {
            binding.locationAddr.setVisibility(View.VISIBLE);
        }
        //设置发表内容
        binding.content.setText(friendsLoopItem.getContent());
        //设置发表时间
        binding.time.setText(DateUtil.calculaterReleasedTime(context,new Date(friendsLoopItem.getCreatetime()*1000),friendsLoopItem.getCreatetime()*1000,0));
        //当文本内容大于6行的时候
        bindingFinal.content.post(new Runnable() {
            @Override
            public void run() {
                final int lineCount = bindingFinal.content.getLineCount();
                if(lineCount>6){
                    bindingFinal.content.setMaxLines(6);
                    bindingFinal.contentMore.setVisibility(View.VISIBLE);
                    bindingFinal.contentMore.setOnClickListener(new View.OnClickListener() {
                        boolean isClick=true;
                        @Override
                        public void onClick(View view) {
                            if (isClick) {
                                bindingFinal.content.setMaxLines(lineCount);
                                bindingFinal.contentMore.setText("收起");
                                isClick = false;
                            }else{
                                bindingFinal.content.setMaxLines(6);
                                bindingFinal.contentMore.setText("全文");
                                isClick = true;
                            }
                        }
                    });
                }else{
                    bindingFinal.contentMore.setVisibility(View.GONE);
                }
            }
        });
        //内容的长按事件
        bindingFinal.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setItems(new String[]{"复制", "收藏"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch(i){
                                    case 0:
                                        GetDataUtil.copy(context,friendsLoopItem.getContent());
                                    break;
                                    case 1:
                                        break;
                                }
                            }
                        }).show();
                return false;
            }
        });
        final UserInfo userInfo = GetDataUtil.getUserInfo(context);
        //如果是自己发的就显示删除按钮
        if (userInfo.getUid().equals(friendsLoopItem.getUid())) {
            binding.delBtn.setVisibility(View.VISIBLE);
            binding.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AbDialogUtil.showAlertDialog(context, null, "你确实要删除该内容吗", new AbAlertDialogFragment.AbDialogOnClickListener() {
                        @Override
                        public void onPositiveClick() {
                            friensLoopPresenter.delData(friendsLoopItem.getId());
                        }
                        @Override
                        public void onNegativeClick() {}
                    });
                }
            });
        }else{
            binding.delBtn.setVisibility(View.GONE);
        }
        //显示评论点赞按钮
        binding.jumpLayout.setVisibility(View.GONE);
        //关闭评论点赞按钮
        binding.bodyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temp!=null){
                    temp.setVisibility(View.GONE);
                }
                friensLoopView.hidePinLun();
            }
        });
        final ArrayList<FriendsLoopItem> friendsLoopItemList = (ArrayList<FriendsLoopItem>) list;
        binding.functionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friendsLoopItem.getIspraise()==1) {
                    bindingFinal.zanText.setText("取消");
                    bindingFinal.zanBtnIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.friends_zan_btn));
                }else if(friendsLoopItem.getIspraise()==0){
                    bindingFinal.zanText.setText("赞");
                    bindingFinal.zanBtnIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.friends_cancle_zan_btn));
                }
                if(temp!=null){
                    temp.setVisibility(View.GONE);
                }
                temp = bindingFinal.jumpLayout;
                bindingFinal.jumpLayout.setVisibility(View.VISIBLE);
                TranslateAnimation animation = new TranslateAnimation(StringUtil.dip2px(context,155), 0, 0, 0);
                animation.setDuration(500);
                bindingFinal.jumpLayout.startAnimation(animation);
                bindingFinal.zanBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (friendsLoopItem.getIspraise()==1) {
                            friensLoopPresenter.setSharePraise(friendsLoopItemList,position,friendsLoopItem.getUid(),friendsLoopItem.getNickname(),false);
                        }else if(friendsLoopItem.getIspraise()==0){
                            friensLoopPresenter.setSharePraise(friendsLoopItemList,position,friendsLoopItem.getUid(),friendsLoopItem.getNickname(),true);
                        }
                    }
                });
            }
        });
        //处理图片的显示
        binding.gridview.setAdapter(new ImageAdapter(context,friendsLoopItem.getPicture()));
        //点击咨讯时的操作
        binding.commentBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friensLoopView.showPinLun(position,null,null,null);
                bindingFinal.jumpLayout.setVisibility(View.GONE);
            }
        });
        //点赞处理
        CommentUser[] zanList = friendsLoopItem.getPraiselist();
        binding.zanLayout.removeAllViews();
        if (zanList!=null && zanList.length>0) {
            binding.otherLayout.setVisibility(View.VISIBLE);
            binding.zanIcon.setVisibility(View.VISIBLE);
            TextView tv = new TextView(context);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(param);
            String userName=" ";
            boolean isHide=zanList.length>5;
            int nameCount;
            String nameStr = "";
            if(isHide){
                nameCount=5;
                nameStr="等"+zanList.length+"个人";
            }else{
                nameCount=zanList.length;
            }
            for (int i = 0; i < nameCount; i++) {
                userName+=zanList[i].getNickname()+",";
            }
            SpannableString spannableString = new SpannableString(userName.substring(0,userName.length()-1)+nameStr);
            for (int i = 0; i < nameCount; i++) {
                final int pos = i;
                int start=i-1<0?0:userName.indexOf(",",userName.indexOf(zanList[i-1].getNickname()));
                int end;
                if (zanList[i].getNickname()==null) {
                    end  = start+1;
                }else{
                    end  = start+zanList[i].getNickname().length()+1;
                }
                end= end<start?start:end;
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(context.getResources().getColor(R.color.application_friends_loop_user_name));
                        ds.setUnderlineText(false);      //设置下划线
                    }
                    @Override
                    public void onClick(View widget) {
                    }
                },start,end , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            tv.setText(spannableString);
            tv.setMovementMethod(LinkMovementMethod.getInstance());
            binding.zanLayout.addView(tv);
        }else{
            binding.otherLayout.setVisibility(View.GONE);
            binding.zanIcon.setVisibility(View.GONE);
        }
        //评论处理
        final CommentUser[] replylist = list.get(position).getReplylist();
        binding.commentLayout.removeAllViews();
        if (replylist!=null && replylist.length>0) {
            binding.otherLayout.setVisibility(View.VISIBLE);
            binding.commentLayout.removeAllViews();
            boolean isHide=replylist.length>20;
            int nameCount;
            if(isHide){
                nameCount=20;
            }else{
                nameCount=replylist.length;
            }
            for (int i = 0; i <nameCount; i++) {
                final int pos = i;
                LinearLayout layout = new LinearLayout(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout.setLayoutParams(params);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                TextView tv = new TextView(context);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(param);
                tv.setBackground(context.getResources().getDrawable(R.drawable.friends_long_click_bg_color));
                String replyStr;
                //如果fid不为0表示回复别人
                if(replylist[i].getFid()==null||replylist[i].getFid().equals("0")){
                    replyStr = replylist[i].getNickname()+":"+replylist[i].getContent();
                }else{
                    replyStr = replylist[i].getNickname()+"回复"+replylist[i].getFnickname()+":"+replylist[i].getContent();
                }
//                SpannableString spannableString = EmojiUtil.getExpressionString(context,replyStr, "emoji_[\\d]{0,3}");
                SpannableString spannableString =  new SpannableString(replyStr);
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(context.getResources().getColor(R.color.application_friends_loop_user_name));
                        ds.setUnderlineText(false);      //设置下划线
                    }
                    @Override
                    public void onClick(View widget) {
                    }
                },0,replylist[i].getNickname().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                if(replylist[i].getFid()!=null&&!replylist[i].getFid().equals("0")){
                    int start = (replylist[i].getNickname()+"回复").length();
                    int end  = start+replylist[i].getFnickname().length();
                    spannableString.setSpan(new ClickableSpan() {
                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(context.getResources().getColor(R.color.application_friends_loop_user_name));
                            ds.setUnderlineText(false);      //设置下划线
                        }
                        @Override
                        public void onClick(View widget) {
                        }
                    },start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                tv.setText(spannableString);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                layout.addView(tv);
                //设置回复
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        friensLoopView.showPinLun(position,replylist[pos].getUid(),replylist[pos].getNickname(),"回复"+replylist[pos].getNickname());
                    }
                });
                binding.commentLayout.addView(layout);
            }
            if(isHide){
                LinearLayout layout = new LinearLayout(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout.setLayoutParams(params);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                TextView tv = new TextView(context);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(param);
                tv.setBackground(context.getResources().getDrawable(R.drawable.friends_long_click_bg_color));
                tv.setTextColor(context.getResources().getColor(R.color.application_friends_loop_more));
                tv.setText("更多");
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                layout.addView(tv);
                binding.commentLayout.addView(layout);
            }
        }
        return binding.getRoot();
    }
}
