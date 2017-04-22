package com.example.administrator.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.BR;
import com.example.administrator.R;
import com.example.administrator.databinding.FavoriteItemBinding;
import com.example.administrator.entity.FavoriteItem;
import com.example.administrator.util.DateUtil;
import com.example.administrator.util.ImageUitl;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class MyFavoriteAdapter extends BaseAdapter {
    private Context context;
    private List<FavoriteItem> list;
    public MyFavoriteAdapter(Context context, List<FavoriteItem> list) {
        this.context = context;
        this.list = list;
    }
    public void setData( List<FavoriteItem> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        FavoriteItemBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.favorite_item, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        FavoriteItem favoriteItem = list.get(position);
        FavoriteItem.FavoriteItemContent favoriteItemContent =  favoriteItem.getFavoriteItemContent();

        binding.voiceLayout.setVisibility(View.GONE);
        binding.videoLayout.setVisibility(View.GONE);
        binding.showOther.setVisibility(View.GONE);
        binding.msgMap.setVisibility(View.GONE);
        binding.showOther.setVisibility(View.GONE);
        binding.msgMap.setVisibility(View.GONE);
        switch (favoriteItemContent.getTypefile()){
            case VOICE:
                binding.voiceLayout.setVisibility(View.VISIBLE);
                break;
            case VIDEO:
                binding.videoLayout.setVisibility(View.VISIBLE);
                if(StringUtil.isNull(favoriteItemContent.getImage())){
                    binding.msgVideo.setImageBitmap(UIUtil.createVideoThumbnail(favoriteItemContent.getUrl()));
                }else{
                    ImageUitl.setImage(binding.msgVideo,favoriteItemContent.getImage());
                }
                break;
            case MAP:
                ImageUitl.setImage(binding.imageIcon, UIUtil.getMapUrl(favoriteItemContent.getLng(),favoriteItemContent.getLat()));
                binding.showOther.setVisibility(View.VISIBLE);
                binding.msgMap.setVisibility(View.VISIBLE);
                break;
            case PICTURE:
                ImageUitl.setImage(binding.imageIcon,favoriteItemContent.getUrlsmall());
                binding.showOther.setVisibility(View.VISIBLE);
                binding.msgMap.setVisibility(View.GONE);
                break;
        }
        binding.time.setText(DateUtil.calculaterReleasedTime(context,new Date(favoriteItem.getCreatetime()*1000),favoriteItem.getCreatetime()*1000,0));
        binding.setVariable(BR.favoritfeItem, favoriteItem);
        return binding.getRoot();
    }
}
