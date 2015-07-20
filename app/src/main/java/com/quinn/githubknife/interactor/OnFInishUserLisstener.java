package com.quinn.githubknife.interactor;

import com.quinn.httpknife.github.User;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public interface OnFinishUserLisstener {
    public void onFinished(List<User> items);
}
