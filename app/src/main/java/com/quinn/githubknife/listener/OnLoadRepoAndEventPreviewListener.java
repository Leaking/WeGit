package com.quinn.githubknife.listener;

import java.util.List;

/**
 * Created by Quinn on 10/16/15.
 */
public interface OnLoadRepoAndEventPreviewListener {

    public void repoItems(List items);
    public void eventItems(List items);

    public void loadRepoError();
    public void loadEventError();

}
