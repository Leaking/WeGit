package com.quinn.githubknife.ui.view;

import com.quinn.httpknife.github.User;

/**
 * Created by Quinn on 7/25/15.
 */
public interface UserInfoView {
    public void loadUser(User user);
    public void setFollowState(boolean isFollow);
    public void failLoad(String errorMsg);
}
