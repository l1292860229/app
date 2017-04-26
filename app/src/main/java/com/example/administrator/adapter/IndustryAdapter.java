package com.example.administrator.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.activity.BBSChatMainActivity;
import com.example.administrator.activity.ChatMainActivity;
import com.example.administrator.databinding.BbsItemBinding;
import com.example.administrator.entity.Bbs;
import com.example.administrator.util.DateUtil;

import java.util.Date;
import java.util.List;

import static com.example.administrator.entity.Bbs.Userjoin.JOIN_ISJOIN;

/**
 * Created by Administrator on 2017/1/22.
 */

public class IndustryAdapter extends BaseAdapter {
    private Context context;
    private List<Bbs> list;
    public IndustryAdapter(Context context, List<Bbs> list) {
        this.context = context;
        this.list = list;
    }
    public void setData( List<Bbs> list){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final BbsItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bbs_item, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(BR.bbsinfo,list.get(position));
        binding.time.setText(DateUtil.calculaterReleasedTime(context,new Date(list.get(position).getTime()),list.get(position).getTime(),0));
        final Bbs bbs = list.get(position);
        binding.money.setText("￥"+list.get(position).getMoney());
        binding.presioncount.setVisibility(View.VISIBLE);
        if(bbs.getIsvisitors()==Bbs.Visitors.CANVISITOR){
            binding.guanguan.setVisibility(View.VISIBLE);
        }else{
            binding.guanguan.setVisibility(View.GONE);
        }
        binding.join.setVisibility(View.VISIBLE);
        switch (bbs.getIsjoin()){
            case JOIN_NOJOIN:
                binding.join.setText("加入");
                binding.join.setTextColor(context.getResources().getColor(R.color.white));
                binding.join.setBackgroundColor(context.getResources().getColor(R.color.bluebtn));
                binding.join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case JOIN_ISJOIN:
                binding.join.setText("已加入");
                binding.join.setTextColor(context.getResources().getColor(R.color.text_color));
                binding.join.setBackgroundColor(context.getResources().getColor(R.color.white));
                binding.guanguan.setVisibility(View.GONE);
                binding.join.setOnClickListener(null);
                break;
            case JOIN_REVIEW:
                binding.join.setText("待审核");
                binding.join.setTextColor(context.getResources().getColor(R.color.white));
                binding.join.setBackgroundColor(context.getResources().getColor(R.color.friendloop_botton_select));
                binding.join.setOnClickListener(null);
                break;
        }
        binding.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bbs.getIsjoin()==JOIN_ISJOIN){
                    openActivity(bbs);
                }else{
                    showModifybgDialog(bbs);
                }
            }
        });
        binding.guanguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(bbs);
            }
        });
        return binding.getRoot();
    }
    private void openActivity(Bbs bbs){
        Intent intent = new Intent(context, BBSChatMainActivity.class);
        intent.putExtra(ChatMainActivity.NAME,bbs.getTitle());
        intent.putExtra(ChatMainActivity.TOID,bbs.getId());
        intent.putExtra(ChatMainActivity.TOHEAD,bbs.getHeadsmall());
        intent.putExtra(BBSChatMainActivity.BBSTYPE, bbs.getType());
        context.startActivity(intent);
    }
    private void showModifybgDialog(final Bbs bbs){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("你还不是群成员,是否申请加入");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(!bbs.getMoney().equals("0")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("你申请的群不是免费的,是否支付费用");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    builder.create().show();
                }else{
                }
            }
        });
        if(bbs.getIsvisitors() == Bbs.Visitors.CANVISITOR){
            builder.setNeutralButton("逛逛", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    openActivity(bbs);
                }
            });
        }
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
