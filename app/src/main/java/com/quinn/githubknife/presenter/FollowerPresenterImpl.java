package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.ui.view.ListFragmentView;

/**
 * Created by Quinn on 7/20/15.
 */
public class FollowerPresenterImpl extends PresenterAdapter{

    public FollowerPresenterImpl(Context context, ListFragmentView view){
        super(context, view);
    }

    @Override
    public void onPageLoad(int page, String user) {
        super.onPageLoad(page,user);
        interactor.loadFollwers(user,page);
    }


}
