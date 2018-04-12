package com.example.user.portfolio.Activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.user.portfolio.R;
import com.example.user.portfolio.Receiver;
import com.example.user.portfolio.Service.MyIntentService;

import static com.example.user.portfolio.Service.MyIntentService.SAVED_TEXT;

public class WikiActivity extends AppCompatActivity {

    private WebView webView;
    public static final String SERVICE_REQUEST = "service_request";
    public static final String RESULT_PARAM = "resultParam";
    public static final int SERVICE_RESULT = 2;
    private SharedPreferences preferences;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        receiver = new Receiver(this);


        webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        preferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        String data = preferences.getString(SAVED_TEXT,"");
        if(!TextUtils.isEmpty(data)){
            webView.loadDataWithBaseURL("https://ru.m.wikipedia.org/wiki/%D0%91%D1%83%D1%84%D1%84%D0%BE%D0%BD,_%D0%94%D0%B6%D0%B0%D0%BD%D0%BB%D1%83%D0%B8%D0%B4%D0%B6%D0%B8", data, "text/html; charset=UTF-8", "UTF-8", null);

        } else {
            Intent intent = new Intent(this, MyIntentService.class)
                    .putExtra(SERVICE_REQUEST, 1);
            startService(intent);
        }
    }

    public void updateContent(){
        preferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        String data = preferences.getString(SAVED_TEXT,"");
        webView.loadDataWithBaseURL("https://ru.m.wikipedia.org/wiki/%D0%91%D1%83%D1%84%D1%84%D0%BE%D0%BD,_%D0%94%D0%B6%D0%B0%D0%BD%D0%BB%D1%83%D0%B8%D0%B4%D0%B6%D0%B8", data, "text/html; charset=UTF-8", "UTF-8", null);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("GALINA_BLANKA");
        this.registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
