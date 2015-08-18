package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class ImageActivity extends BaseActivity {

    private static final String TAG = ImageActivity.class.getSimpleName();
    private static final String IMG_ROOT = "https://raw.githubusercontent.com/";
    private static final String SPLIT = "/";
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.img)
    ImageView img;

    private String branch = "master";
    private String user ;
    private String repo ;
    private String path ;



    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        repo = bundle.getString("repo");
        user = bundle.getString("user");
        branch = bundle.getString("branch","master");
        path = bundle.getString("path");
        toolbar.setTitle(repo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String url = IMG_ROOT + user + SPLIT + repo + SPLIT + branch + SPLIT + path;
        imageLoader.displayImage(url,img,option,animateFirstListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
