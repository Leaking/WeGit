package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.FindItemsInteractor;
import com.quinn.githubknife.interactor.FindItemsInteractorImpl;
import com.quinn.githubknife.interactor.OnLoadUserListListener;
import com.quinn.githubknife.ui.view.ListFragmentView;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public class PresenterAdapter implements ListFragmentPresenter,OnLoadUserListListener {

    protected ListFragmentView view;
    protected FindItemsInteractor interactor;


    public PresenterAdapter(Context context, ListFragmentView view){
        this.view = view;
        this.interactor = new FindItemsInteractorImpl(context,this);
    }

    @Override
    public void onItemClicked(int position) {
        view.intoItem(position);
    }

    @Override
    public void onPageLoad(int page, String user) {
        view.showProgress();
    }

    @Override
    public void onFinished(List items) {
        view.hideProgress();
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
