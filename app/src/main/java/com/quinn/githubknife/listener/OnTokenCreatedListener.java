package com.quinn.githubknife.listener;

/**
 * Created by Quinn on 8/1/15.
 */
public interface OnTokenCreatedListener  extends OnErrorListener{
    public void onTokenCreated(String token);
}
