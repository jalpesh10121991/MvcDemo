package in.mvcdemo.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.R;
import in.mvcdemo.Utills.VPreferences;


public class PrivacyPolicy extends AppCompatActivity {

    private WebView mainWebView;
    private Toolbar toolbar;
    private CustomTextView txt_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_title = (CustomTextView) findViewById(R.id.txt_title);

        setupToolbar();
        mainWebView = (WebView) findViewById(R.id.mainWebView);

        mainWebView.loadUrl("file:///android_asset/teammate.html");

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        txt_title.setText(VPreferences.getPreferanceEventName(PrivacyPolicy.this));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Privacy Policy");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}