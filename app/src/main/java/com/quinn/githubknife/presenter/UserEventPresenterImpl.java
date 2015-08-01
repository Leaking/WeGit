package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.view.ListFragmentView;

/**
 * Created by Quinn on 7/27/15.
 */
public class UserEventPresenterImpl extends  PresenterAdapter {

    public UserEventPresenterImpl(Context context, ListFragmentView view){
        super(context, view);
    }


    @Override
    public void onPageLoad(int page, String user){
        super.onPageLoad(page,user);
        interactor.loadUserEvents(user, page);
    }
}
