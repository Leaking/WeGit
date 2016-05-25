package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.presenter.CodePresenter;
import com.quinn.githubknife.presenter.CodePresenterImpl;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.view.CodeView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CodeActivity extends BaseActivity implements CodeView {


    private final static String TAG = CodeActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webview)
    WebView webview;

    private CodePresenter presenter;
    private String owner;
    private String repo;
    private String path;
    private String content;

    public static void launch(Context context, Bundle bundle){
        Intent intent = new Intent(context, CodeActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        repo = (String) bundle.getSerializable("repo");
        owner = (String) bundle.getSerializable("owner");
        path = (String) bundle.getSerializable("path");
        L.i(TAG,"repo = " + repo);
        L.i(TAG,"owner = " + owner);
        L.i(TAG,"path = " + path);
        toolbar.setTitle(repo);
        toolbar.setSubtitle(path);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new CodePresenterImpl(this,this);
        presenter.getContent(owner,repo,path);


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

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setCode(String content) {
        L.i(TAG, "setCode = " + content);
        this.content = content;
        WebSettings settings = webview.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new JavaScriptInterface(), "bitbeaker");

        webview.loadUrl("file:///android_asset/source.html");

    }

    @Override
    public void onError(String msg) {

    }


    protected class JavaScriptInterface {
        @JavascriptInterface
        public String getCode() {
            return TextUtils.htmlEncode(content.replace("\t", "    "));
        }

        @JavascriptInterface
        public String getRawCode() {
            return content;
        }

        @JavascriptInterface
        public String getFilename() {
            return path;
        }

        @JavascriptInterface
        public int getLineHighlight() {
            return 0;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.removeAllViews();
        webview.destroy();
    }
}
