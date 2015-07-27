package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.ui.view.ListFragmentView;

/**
 * Created by Quinn on 7/23/15.
 */
public class StarredRepoPresenterImpl extends PresenterAdapter{


    public StarredRepoPresenterImpl(Context context, ListFragmentView view){
        super(context, view);
    }


    @Override
    public void onPageLoad(int page, String user){
        super.onPageLoad(page,user);
        interactor.loadStarredRepo(user,page);
    }
}
