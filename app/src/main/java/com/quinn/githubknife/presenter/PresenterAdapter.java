package com.quinn.githubknife.presenter;

import com.quinn.githubknife.interactor.OnFinishUserListener;
import com.quinn.githubknife.ui.view.ListFragmentView;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public class PresenterAdapter implements ListFragmentPresenter,OnFinishUserListener {

    protected ListFragmentView view;

    public PresenterAdapter(ListFragmentView view){
        this.view = view;
    }

    @Override
    public void onItemClicked(int position) {
        view.intoItem(position);
    }

    @Override
    public void onPageLoad(int page, String user) {

    }

    @Override
    public void onFinished(List items) {
        view.setItems(items);
    }

    @Override
    public void onError(boolean first) {
        if(first)
            view.failToLoadFirst();
        else{
            view.failToLoadMore();
        }
    }
}
