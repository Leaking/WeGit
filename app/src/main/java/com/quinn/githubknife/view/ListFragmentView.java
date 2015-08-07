package com.quinn.githubknife.view;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public interface ListFragmentView extends ProgressView{

    public void setItems(List<?> items);

    public void intoItem(int position);

    public void failToLoadMore();

    public void failToLoadFirst(String errorMsg);

    public void reLoad();
}
