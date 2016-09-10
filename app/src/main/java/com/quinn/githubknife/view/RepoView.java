package com.quinn.githubknife.view;

import com.quinn.httpknife.github.Branch;
import com.quinn.httpknife.github.Repository;

import java.util.List;

/**
 * Created by Quinn on 8/1/15.
 */
public interface RepoView extends ErrorView,ProgressView{

    public void setStarState(boolean isStar);
    public void forkResult(boolean success);
    public void reLoad();
    public void setBranches(List<Branch> branches);
    public void setRepo(Repository repository);

}
