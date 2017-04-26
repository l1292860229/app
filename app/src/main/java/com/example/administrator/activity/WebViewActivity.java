package com.example.administrator.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.databinding.WebBinding;
import com.example.administrator.webviewutil.InJavaScriptLocalObj;
import com.example.administrator.webviewutil.MyWebChromeClient;
import com.example.administrator.webviewutil.MyWebViewClient;
import com.smartandroid.sa.bv.BelowView;


/**
 * Created by yf on 2016/11/24.
 */

public class WebViewActivity extends BaseActivity {
    public final static String URL = "url";
    Context context;
    WebBinding binding;
    WebView  webView;
    String mUrl;
    InJavaScriptLocalObj obj = new InJavaScriptLocalObj();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = WebViewActivity.this;
        binding = DataBindingUtil.setContentView(this, R.layout.web);
        binding.setBehavior(WebViewActivity.this);
        binding.titleLayout.setBehavior(this);
        webView = binding.webView;
        mUrl = getIntent().getStringExtra(URL);
        initWebView();
        webView.loadUrl(mUrl);
        blv = new BelowView(context, R.layout.web_operating);
    }
    public void initWebView(){
        //设置标题栏
        binding.titleLayout.leftTitle.setText("返回");
        ImageView ivRight = binding.titleLayout.rightBtn;
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.more_btn);
        TextView clsoe =  binding.titleLayout.leftTitle2;
        clsoe.setText("关闭");
        clsoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.stopLoading();
                webView.removeAllViews();
                webView.destroy();
                WebViewActivity.this.finish();
            }
        });
        //setWebViewClient
        webView.addJavascriptInterface(obj, "java_obj");
        webView.setWebViewClient(new MyWebViewClient(binding.progressBar,binding.webviewloading));
        //设置WebChromeClient
        webView.setWebChromeClient(new MyWebChromeClient(binding.progressBar,binding.titleLayout.titlecontext));
        //硬件加速，为了可以播放视频
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //设置图片自动加载
        webView.getSettings().setLoadsImagesAutomatically(true);
        //允许加载js脚本,这个很重要,当初为了这个弄了半天
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        //H5 localStorage支持
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("gb2312");
    }
    @Override
    public void right_btn(View view) {
        blv.showBelowView(view, true, 30, 0);
        View v =  blv.getBelowView();
    }
    @Override
    public void close(View view) {
        if(webView.canGoBack()) {
            webView.goBack();
        }else{
            webView.stopLoading();
            webView.removeAllViews();
            webView.destroy();
            WebViewActivity.this.finish();
        }
    }
    // goBack()表示返回webView的上一页面
    @Override
    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (webView.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            webView.goBack();
            return true;
        } else if (!webView.canGoBack()) {
            WebViewActivity.this.finish();
            return true;
        }
        return false;
    }
}
