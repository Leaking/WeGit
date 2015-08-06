package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.view.ListFragmentView;

/**
 * Created by Quinn on 7/20/15.
 */
public class ForkersPresenterImpl extends PresenterAdapter{

    public static final String TAG = ForkersPresenterImpl.class.getSimpleName();


    public ForkersPresenterImpl(Context context, ListFragmentView view){
        super(context, view);
    }

    @Override
    public void onPageLoad(String user,String repo,int page){
        super.onPageLoad(user,repo,page);
        interactor.loadForkers(user, repo, page);
    }


}
