package com.example.administrator.webviewutil;

import android.webkit.JavascriptInterface;

/**
 * Created by dell on 2017/4/14.
 */

final public class InJavaScriptLocalObj {
    private String mhtml;
    @JavascriptInterface
    public void showSource(String html) {
        mhtml = html;
    }
    public String getMhtml() {
        return mhtml;
    }
}
