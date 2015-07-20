package com.quinn.githubknife.interactor;

/**
 * Created by Quinn on 7/20/15.
 */
public interface FindUsersInteractor {

    public void loadMyFollowings();
    public void loadMyFollwers();
    public void loadFollowerings(String account);
    public void loadFollwers(String account);


}
