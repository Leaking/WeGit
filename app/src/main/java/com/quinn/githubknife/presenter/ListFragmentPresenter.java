package com.quinn.githubknife.presenter;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public interface ListFragmentPresenter {

    public void onItemClicked(int position);

    public void onPageLoad(int page, String user);

    public void onPageLoad(String user, String repo, int page);

    public void onPageLoad(List<String> keywords,int page);

    public void onTreeLoad(String owner, String repo, String sha);
}
