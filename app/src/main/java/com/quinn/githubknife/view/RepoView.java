package com.quinn.githubknife.view;

/**
 * Created by Quinn on 8/1/15.
 */
public interface RepoView extends ErrorView{

    public void setStarState(boolean isStar);
    public void forkResult(boolean success);

}
