package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.FindUsersInteractor;
import com.quinn.githubknife.interactor.FindUsersInteractorImpl;
import com.quinn.githubknife.ui.view.ListFragmentView;

/**
 * Created by Quinn on 7/20/15.
 */
public class MyFolloweringPresenter extends PresenterAdapter{

    private FindUsersInteractor interactor;

    public MyFolloweringPresenter(Context context, ListFragmentView view){
        super(view);
        this.interactor = new FindUsersInteractorImpl(context,this);
    }

    @Override
    public void onPageLoad(int page, String user){
        interactor.loadFollowerings(user,page);
    }


}
