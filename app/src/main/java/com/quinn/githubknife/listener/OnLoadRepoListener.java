package com.quinn.githubknife.listener;

/**
 * Created by Quinn on 8/1/15.
 */
public interface OnLoadRepoListener {
    public void setStarState(boolean isStar);
    public void onError(String errorMsg);
}
