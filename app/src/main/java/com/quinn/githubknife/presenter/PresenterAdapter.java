package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.FindItemsInteractor;
import com.quinn.githubknife.interactor.FindItemsInteractorImpl;
import com.quinn.githubknife.listener.OnLoadItemListListener;
import com.quinn.githubknife.view.ListFragmentView;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public class PresenterAdapter implements ListFragmentPresenter,OnLoadItemListListener {

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
    public void onPageLoad(String user,String repo,int page) {
        view.showProgress();
    }

    @Override
    public void onPageLoad(List<String> keywords, int page) {
        view.showProgress();
    }


    @Override
    public void onTreeLoad(String owner, String repo, String sha) {
        view.showProgress();
    }

    @Override
    public void onFinished(List items) {
        view.hideProgress();
        view.setItems(items);
    }

    @Override
    public void onError(boolean first,String errorMsg) {
        if(first)
            view.failToLoadFirst(errorMsg);
        else{
            view.failToLoadMore();
        }
    }
}
