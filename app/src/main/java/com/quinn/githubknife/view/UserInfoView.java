package com.quinn.githubknife.view;

import com.quinn.httpknife.github.User;

/**
 * Created by Quinn on 7/25/15.
 */
public interface UserInfoView extends ErrorView {


    public void loadUser(User user);
    public void setFollowState(boolean isFollow);
    public void setStarredCount(int count);
}
