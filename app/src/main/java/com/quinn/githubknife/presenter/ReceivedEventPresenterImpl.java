package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.view.ListFragmentView;

/**
 * Created by Quinn on 7/25/15.
 */
public class ReceivedEventPresenterImpl extends  PresenterAdapter {

    public ReceivedEventPresenterImpl(Context context, ListFragmentView view){
        super(context, view);
    }


    @Override
    public void onPageLoad(int page, String user){
        super.onPageLoad(page,user);
        interactor.loadReceivedEvents(user,page);
    }
}
