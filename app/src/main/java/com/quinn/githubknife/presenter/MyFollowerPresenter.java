package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.FindUsersInteractor;
import com.quinn.githubknife.interactor.FindUsersInteractorImpl;
import com.quinn.githubknife.ui.view.ListFragmentView;

/**
 * Created by Quinn on 7/20/15.
 */
public class MyFollowerPresenter extends PresenterAdapter{

    private FindUsersInteractor interactor;

    public MyFollowerPresenter(Context context, ListFragmentView view){
        super(view);
        this.interactor = new FindUsersInteractorImpl(context,this);
    }

    @Override
    public void onResume(){
        interactor.loadMyFollwers();
    }


}
