package com.quinn.githubknife.interactor;

import com.quinn.httpknife.github.User;

/**
 * Created by Quinn on 7/22/15.
 */
public interface OnLoadUserInfoListener {

    public void onFinish(User user);
    public void onError();

}
