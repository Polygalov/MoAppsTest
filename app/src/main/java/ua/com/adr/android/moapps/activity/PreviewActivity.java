package ua.com.adr.android.moapps.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import ua.com.adr.android.moapps.R;

public class PreviewActivity extends AppCompatActivity {

    WebView mWebView;

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_preview);

        mWebView = findViewById(R.id.webview);
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setAppCacheEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setDomStorageEnabled(true);

        this.mWebView.setLongClickable(false);
        this.mWebView.setHapticFeedbackEnabled(false);
        this.mWebView.setScrollbarFadingEnabled(false);
        this.mWebView.setWebChromeClient(new WebChromeClient());

        String stringExtra = getIntent().getStringExtra("app_url");
        if (stringExtra != null) {
            this.mWebView.loadUrl(stringExtra);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || !this.mWebView.canGoBack()) {
            return super.onKeyDown(i, keyEvent);
        }
        this.mWebView.goBack();
        return true;
    }
}