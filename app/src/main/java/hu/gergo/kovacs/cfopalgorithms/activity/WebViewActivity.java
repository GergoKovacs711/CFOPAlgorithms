package hu.gergo.kovacs.cfopalgorithms.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebViewClient;

import hu.gergo.kovacs.cfopalgorithms.R;

public class WebViewActivity extends AppCompatActivity {
    private android.webkit.WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        url = getIntent().getStringExtra("url");
        webView = (android.webkit.WebView) findViewById(R.id._webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }
}
