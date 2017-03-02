package com.example.administrator.activity;

import android.support.v7.app.AppCompatActivity;

import com.ab.fragment.AbLoadDialogFragment;
import com.ab.util.AbDialogUtil;
import com.example.administrator.R;

/**
 * Created by Administrator on 2017/3/2.
 */

public class BaseActivity extends AppCompatActivity {
    AbLoadDialogFragment loadingfragment;
    public void showLoading(String msg) {
        loadingfragment =  AbDialogUtil.showLoadDialog(BaseActivity.this, R.mipmap.ic_load, msg);
    }

    public void hideLoading() {
        loadingfragment.dismiss();
        loadingfragment.onDestroyView();
    }
}
