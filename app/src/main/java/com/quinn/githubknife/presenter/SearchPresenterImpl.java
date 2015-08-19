package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.view.ListFragmentView;

import java.util.List;

/**
 * Created by Quinn on 8/19/15.
 */
public class SearchPresenterImpl extends  PresenterAdapter {


    public SearchPresenterImpl(Context context, ListFragmentView view) {
        super(context, view);
    }

    @Override
    public void onPageLoad(List<String> keywords, int page){
        super.onPageLoad(keywords,page);
        interactor.searchUsers(keywords,page);
    }
}
