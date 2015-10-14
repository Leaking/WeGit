package com.quinn.githubknife.listener;

import com.quinn.httpknife.github.User;

/**
 * Created by Quinn on 7/22/15.
 */
public interface OnLoadUserInfoListener extends OnErrorListener{

    public void onFinish(User user);
    public void updateFollowState(boolean isFollow);
    public void loadStarredCount(int count);

}
