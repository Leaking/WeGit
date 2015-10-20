package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReadmeActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webview)
    WebView webview;

    private String url;


    public static void launch(Context context, String url){
        Intent intent = new Intent(context,ReadmeActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        toolbar.setTitle("Readme");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webview.loadUrl(url);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
