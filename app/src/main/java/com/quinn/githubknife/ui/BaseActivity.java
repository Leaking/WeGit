package com.quinn.githubknife.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.quinn.githubknife.GithubApplication;
import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.widget.AnimateFirstDisplayListener;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.UIUtils;
import com.tendcloud.tenddata.TCAgent;


/**
 * Created by Quinn on 7/15/15.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    protected ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    protected DisplayImageOptions option;
    protected ImageLoader imageLoader;
    protected GithubApplication app;
    private String activityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        option = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).build();
        app = (GithubApplication) getApplication();
        activityName = this.getClass().getSimpleName();
        TCAgent.onPageStart(this, activityName);

    }

    @Override
    protected void onStop() {
        super.onStop();
        TCAgent.onPageEnd(this, activityName);
    }




    public void redirectToBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }

    public void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.email_title);
        emailIntent.putExtra(Intent.EXTRA_TEXT, R.string.email_body);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


}
