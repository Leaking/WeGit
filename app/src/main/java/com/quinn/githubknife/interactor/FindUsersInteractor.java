package com.quinn.githubknife.interactor;

/**
 * Created by Quinn on 7/20/15.
 */
public interface FindUsersInteractor {

    public void loadMyFollowings(int page);
    public void loadMyFollwers(int page);
    public void loadFollowerings(String account,int page);
    public void loadFollwers(String account,int page);


}
