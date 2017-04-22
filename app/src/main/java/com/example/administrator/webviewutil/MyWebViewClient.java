package com.example.administrator.webviewutil;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by dell on 2017/4/14.
 */

public class MyWebViewClient extends WebViewClient {
    private String mUrl;
    private ProgressBar progressBar;
    private ImageView imageView;
    public MyWebViewClient(ProgressBar progressBar, ImageView imageView){
        this.progressBar = progressBar;
        this.imageView = imageView;
    }
    public String getmUrl() {
        return mUrl;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
        WebView.HitTestResult hit = view.getHitTestResult();
        int hitType = hit.getType();
        if (hitType == WebView.HitTestResult.SRC_ANCHOR_TYPE) {//点击超链接
            //这里执行自定义的操作
            mUrl = url;
            view.loadUrl(url);
            return true;//返回true浏览器不再执行默认的操作
        } else if (hitType == 0) {//重定向时hitType为0
            return false;//不捕获302重定向
        } else {
            return false;
        }
    }
    // 页面开始加载
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        progressBar.setVisibility(View.VISIBLE);
        super.onPageStarted(view, url, favicon);
    }
    // 页面加载完成
    @Override
    public void onPageFinished(WebView view, String url) {
        progressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        //读取网页上的内容
        view.loadUrl("javascript:window.java_obj.showSource(document.getElementsByTagName('body')[0].innerHTML);");
        super.onPageFinished(view, url);
    }
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
        handler.proceed();  // 接受所有网站的证书
    }

}
