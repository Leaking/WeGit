package com.quinn.githubknife.presenter;

/**
 * Created by Quinn on 7/25/15.
 */
public interface UserInfoPresenter {

    public void user(String user);
    public void hasFollow(String targetUser);
    public void follow(String targetUser);
    public void unFollow(String targetUser);
}
