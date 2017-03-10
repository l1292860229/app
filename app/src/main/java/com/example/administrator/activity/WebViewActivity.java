package com.example.administrator.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.databinding.WebBinding;
import com.tandong.sa.bv.BelowView;

import static com.example.administrator.R.id.url;

/**
 * Created by yf on 2016/11/24.
 */

public class WebViewActivity extends AppCompatActivity {
    public final static String URL = "url";
    Context context;
    WebBinding binding;
    TextView titleTextView;
    WebView  webView;
    private ProgressBar progressBar;
    private String mhtml;
    String mUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = WebViewActivity.this;
        binding = DataBindingUtil.setContentView(this, R.layout.web);
        titleTextView = ((TextView)binding.titleLayout.findViewById(R.id.titlecontext));
        webView = binding.webView;
        progressBar = binding.progressBar;
        mUrl = getIntent().getStringExtra(URL);
        initWebView();
        webView.loadUrl(mUrl);
    }
    public void initWebView(){
        //设置标题栏
        ImageView leftbtn = ((ImageView)binding.titleLayout.findViewById(R.id.left_icon));
        leftbtn.setImageResource(R.drawable.back_btn);
        ((TextView)binding.titleLayout.findViewById(R.id.left_title)).setText("返回");
        ((LinearLayout)binding.titleLayout.findViewById(R.id.left_btn)).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(webView.canGoBack()) {
                            webView.goBack();
                        }else{
                            webView.stopLoading();
                            webView.removeAllViews();
                            webView.destroy();
                            WebViewActivity.this.finish();
                        }
                    }
                });
        ImageView ivRight = ((ImageView)binding.titleLayout.findViewById(R.id.right_btn));
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageDrawable(getResources().getDrawable(R.drawable.more_btn));
        ivRight.setOnClickListener(new View.OnClickListener() {
            BelowView blv = new BelowView(context, R.layout.web_operating);
            @Override
            public void onClick(View view) {
                blv.showBelowView(view, true, 30, 0);
                View v =  blv.getBelowView();
            }
        });
        TextView clsoe =  ((TextView)binding.titleLayout.findViewById(R.id.left_title2));
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
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        webView.setWebViewClient(new WebViewClient() {
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
                //读取网页上的内容
                view.loadUrl("javascript:window.java_obj.showSource(document.getElementsByTagName('body')[0].innerHTML);");
                super.onPageFinished(view, url);
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();  // 接受所有网站的证书
            }
        });
        //设置WebChromeClient
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            // 处理javascript中的alert
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                return super.onJsAlert(view, url, message, result);
            };

            @Override
            // 处理javascript中的confirm
            public boolean onJsConfirm(WebView view, String url,
                                       String message, final JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            };

            @Override
            // 处理javascript中的prompt
            public boolean onJsPrompt(WebView view, String url, String message,
                                      String defaultValue, final JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue,
                        result);
            };

            // 设置网页加载的进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

            // 设置程序的Title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                titleTextView.setText(title);
            }
        });
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

    /**
     * 为了读取网站源码
     */
    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            mhtml = html;
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
