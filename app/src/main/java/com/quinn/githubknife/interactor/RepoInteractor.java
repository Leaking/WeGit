package com.quinn.githubknife.interactor;

/**
 * Created by Quinn on 8/1/15.
 */
public interface RepoInteractor {

    public void hasStar(String owner,String repo);
    public void star(String owner,String repo);
    public void unStar(String owner,String repo);
    public void fork(String owner,String repo);
}
