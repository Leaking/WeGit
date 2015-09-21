package com.quinn.githubknife.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.quinn.githubknife.GithubApplication;
import com.quinn.githubknife.ui.widget.AnimateFirstDisplayListener;


/**
 * Created by Quinn on 7/15/15.
 */
public class BaseActivity extends AppCompatActivity {

    protected ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    protected DisplayImageOptions option;
    protected ImageLoader imageLoader;
    protected GithubApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        option = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).build();
        app = (GithubApplication) getApplication();
       // github = new GithubImpl(this);

    }

    public void redirectToBrowser(String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }

    public void sendEmail(String email){
        Intent data=new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse(email));
        startActivity(data);
    }
}
