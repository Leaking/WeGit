package com.quinn.githubknife.presenter;

/**
 * Created by Quinn on 8/1/15.
 */
public interface RepoPresenter {


    public void hasStar(String owner,String repo);
    public void fork(String owner,String repo);
    public void star(String owner,String repo);
    public void unStar(String owner,String repo);
    public void branches(String owner,String repo);
    public void loadRepo(String owner, String repo);

}
