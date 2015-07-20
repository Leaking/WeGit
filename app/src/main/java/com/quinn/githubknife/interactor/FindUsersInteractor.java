package com.quinn.githubknife.interactor;

import com.quinn.githubknife.account.GitHubAccount;

/**
 * Created by Quinn on 7/20/15.
 */
public interface FindUsersInteractor {

    public void loadMyFollowings(GitHubAccount gitHubAccount);
    public void loadMyFollwers(GitHubAccount gitHubAccount);
    public void loadFollowerings(String account);
    public void loadFollwers(String account);


}
