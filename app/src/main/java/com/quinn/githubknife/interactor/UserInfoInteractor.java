package com.quinn.githubknife.interactor;

/**
 * Created by Quinn on 7/22/15.
 */
public interface UserInfoInteractor {

    public void auth();
    public void userInfo(String usr);
    public void hasFollow(String user);
    public void follow(String user);
    public void unFollow(String user);
    public void starredCount(String user);
}
