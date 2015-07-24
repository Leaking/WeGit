package com.quinn.githubknife.ui.view;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public interface ListFragmentView {

    public void showProgress();

    public void hideProgress();

    public void setItems(List<?> items);

    public void intoItem(int position);

    public void failToLoadMore();

    public void failToLoadFirst();

    public void reLoad();
}
