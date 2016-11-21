package com.quinn.githubknife;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.quinn.githubknife.model.APKVersion;
import com.quinn.githubknife.utils.ChannelUtils;
import com.quinn.httpknife.github.User;
import com.tendcloud.tenddata.TCAgent;

/**
 * Created by Quinn on 7/20/15.
 */
public class GithubApplication extends Application {

    private final static String TD_APP_ID = "C7208C73204D6DDF67AA2227D9C06174";

    public static Context instance;

    /**
     * @// TODO: 9/6/16
     * 暂时默认100，后续打多渠道此处得动态获取
     */
    private String channel = "100";

    private User user;

    private String token = "";


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        /**
         * 初始化Talking-data
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                TCAgent.LOG_ON = false;
                channel = ChannelUtils.getChannel(GithubApplication.this);
                TCAgent.init(GithubApplication.this, TD_APP_ID, channel);
                TCAgent.setReportUncaughtExceptions(true);
            }
        }).start();
        // Initialize ImageLoader with configuration.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                        // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(config);
    }


    public APKVersion getAPKVersion(){
        APKVersion version = new APKVersion();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            version.setVersionCode(info.versionCode);
            version.setVersionName(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
