package com.quinn.githubknife.interactor;

import com.quinn.httpknife.github.TrendingRepo;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public interface FindItemsInteractor {


    public void loadFollowerings(String account,int page);
    public void loadFollwers(String account,int page);
    public void loadAuthUser();
    public void loadAuthRepos();
    public void loadRepo(String account,int page);
    public void loadStarredRepo(String user,int page);
    public void loadReceivedEvents(String user, int page);
    public void loadUserEvents(String user, int page);
    public void loadRepoEvents(String user, String repo, int page);
    public void loadStargazers(String owner,String repo, int page);
    public void loadForkers(String owner,String repo, int page);
    public void loadCollaborators(String owner,String repo, int page);
    public void loadTree(String owner,String repo,String sha);
    public void loadBranches(String owner,String repo);
    public void searchUsers(List<String> keywords,int page);
    public void searchRepos(List<String> keywords,int page);
    public void trendingRepos(String url, TrendingRepo.SINCE_TYPE sinceType);
    public void trendingUsers(String url, TrendingRepo.SINCE_TYPE sinceType);

}
