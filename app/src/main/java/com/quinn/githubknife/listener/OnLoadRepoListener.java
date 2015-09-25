package com.quinn.githubknife.listener;

import com.quinn.httpknife.github.Branch;

import java.util.List;

/**
 * Created by Quinn on 8/1/15.
 */
public interface OnLoadRepoListener {
    public void setStarState(boolean isStar);
    public void onError(String errorMsg);
    public void forkResult(boolean success);
    public void setBranches(List<Branch> branches);
}
