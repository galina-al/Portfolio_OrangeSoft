package com.example.user.portfolio.Activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.user.portfolio.R;
import com.example.user.portfolio.Receiver;
import com.example.user.portfolio.Service.MyIntentService;

import static com.example.user.portfolio.util.CONSTANTS.*;


public class WikiActivity extends AppCompatActivity {

    private WebView webView;
    private SharedPreferences preferences;
    private BroadcastReceiver receiver;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_wiki);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_camera:
                                intent = new Intent(WikiActivity.this, ListPhotoActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_wiki:
                                break;
                            case R.id.nav_home:
                                intent = new Intent(WikiActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_notice:
                                intent = new Intent(WikiActivity.this, NoticeActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });
        receiver = new Receiver(this);


        webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String data = preferences.getString(SAVED_TEXT, "");

        if (!TextUtils.isEmpty(data)) {
            webView.loadDataWithBaseURL(WIKI_URL, data, "text/html; charset=UTF-8", "UTF-8", null);

        } else {
            Intent intent = new Intent(this, MyIntentService.class);
            startService(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateContent() {
        preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String data = preferences.getString(SAVED_TEXT, "");
        webView.loadDataWithBaseURL(WIKI_URL, data, "text/html; charset=UTF-8", "UTF-8", null);
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
        navigationView.setCheckedItem(R.id.nav_wiki);
        IntentFilter intentFilter = new IntentFilter(ACTION_DOWNLOAD_SITE);
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
