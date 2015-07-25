package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.FindItemsInteractor;
import com.quinn.githubknife.interactor.FindItemsInteractorImpl;
import com.quinn.githubknife.ui.view.ListFragmentView;

/**
 * Created by Quinn on 7/25/15.
 */
public class EventPresenterImpl extends  PresenterAdapter {

    private FindItemsInteractor interactor;


    public EventPresenterImpl(Context context, ListFragmentView view) {
        super(view);
        this.interactor = new FindItemsInteractorImpl(context,this);

    }



    @Override
    public void onPageLoad(int page, String user){
        interactor.loadReceivedEvents(user,page);
    }
}
