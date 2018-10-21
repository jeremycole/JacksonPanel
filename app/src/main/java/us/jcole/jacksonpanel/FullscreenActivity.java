package us.jcole.jacksonpanel;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;

public class FullscreenActivity extends AppCompatActivity {
    // The URL to load in the main WebView.
    private static final String URL = "http://10.0.1.99:3000/";

    // How frequently to force-reload the URL.
    private static final int RELOAD_FREQUENCY_MS = 5 * 60 * 1000;

    private WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mWebView = findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        hideAndroidUi();
        loadUrl();
    }

    private void hideAndroidUi() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mWebView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void loadUrl() {
        mWebView.loadUrl(URL);

        final Handler reloadHandler = new Handler();
        reloadHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadUrl();
            }
        }, RELOAD_FREQUENCY_MS);
    }

}
