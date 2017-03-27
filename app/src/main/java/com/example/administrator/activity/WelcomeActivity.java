package com.example.administrator.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.R;
import com.example.administrator.entity.Constants;
import com.example.administrator.entity.Picture;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.StringUtil;

import static com.example.administrator.entity.Picture.PictureType.MIPMAP_TYPE;


/**
 * Created by Administrator on 2017/1/21.
 */

public class WelcomeActivity extends AppCompatActivity {
    private final String TAG="WelcomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //改变状态栏的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }
        setContentView(R.layout.welcome);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                String db = Constants.USERINFO;
                String userInfo = (String) GetDataUtil.get(WelcomeActivity.this,db,Constants.USERINFO,"");
                String firstopenapp = (String) GetDataUtil.get(WelcomeActivity.this,db,Constants.FIRSTOPENAPP,"");
                Intent intent;
                //如果没有记住密码就跳到登录页面，否则就进入app
                if(StringUtil.isNull(firstopenapp)){
                    Picture[] images  = new Picture[]{
                            new Picture("denglv","denglv", MIPMAP_TYPE),
                            new Picture("image_3","image_3",MIPMAP_TYPE),
                            new Picture("image_2","image_2",MIPMAP_TYPE),
                            new Picture("image_1","image_1",MIPMAP_TYPE)};
                    intent = new Intent(WelcomeActivity.this,ImagePagerActivity.class);
                    intent.putExtra(ImagePagerActivity.IMAGES,images);
                }else if(StringUtil.isNull(userInfo)){
                    intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                }else{
                    intent = new Intent(WelcomeActivity.this,MainActivity.class);
                }
                WelcomeActivity.this.startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 2000);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            this.finish();
            System.exit(0);
        }
        return super.dispatchKeyEvent(event);
    }
}
