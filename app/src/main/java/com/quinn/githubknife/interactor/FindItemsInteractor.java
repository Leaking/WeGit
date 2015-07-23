package com.quinn.githubknife.interactor;

/**
 * Created by Quinn on 7/20/15.
 */
public interface FindItemsInteractor {

    public void loadMyFollowings(int page);
    public void loadMyFollwers(int page);
    public void loadFollowerings(String account,int page);
    public void loadFollwers(String account,int page);
    public void loadAuthUser();
    public void loadAuthRepos();
    public void loadRepo(String account,int page);
}
