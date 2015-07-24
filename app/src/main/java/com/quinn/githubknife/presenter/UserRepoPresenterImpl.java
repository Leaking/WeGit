package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.FindItemsInteractor;
import com.quinn.githubknife.interactor.FindItemsInteractorImpl;
import com.quinn.githubknife.ui.view.ListFragmentView;

/**
 * Created by Quinn on 7/23/15.
 */
public class UserRepoPresenterImpl extends PresenterAdapter{

    private FindItemsInteractor interactor;

    public UserRepoPresenterImpl(Context context, ListFragmentView view){
        super(view);
        this.interactor = new FindItemsInteractorImpl(context,this);
    }


    @Override
    public void onPageLoad(int page, String user){
        interactor.loadRepo(user,page);
    }
}
