package com.quinn.githubknife.view;

import java.util.List;

/**
 * Created by Quinn on 10/16/15.
 */
public interface RepoAndEventPreviewView {

    public void repoItems(List items);
    public void eventItems(List items);

    public void loadRepoError();
    public void loadEventError();


}
