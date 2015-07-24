package com.quinn.githubknife.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.quinn.githubknife.ui.activity.AnimateFirstDisplayListener;


/**
 * Created by Quinn on 7/15/15.
 */
public class BaseActivity extends AppCompatActivity {

    protected ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    protected DisplayImageOptions option;
    protected ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        option = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).build();
       // github = new GithubImpl(this);

    }
}
