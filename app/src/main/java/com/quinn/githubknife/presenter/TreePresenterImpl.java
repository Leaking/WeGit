package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.view.ListFragmentView;

/**
 * Created by Quinn on 8/7/15.
 */
public class TreePresenterImpl extends PresenterAdapter{

    public static final String TAG = ForkersPresenterImpl.class.getSimpleName();


    public TreePresenterImpl(Context context, ListFragmentView view){
        super(context, view);
    }

    @Override
    public void onPageLoad(String user,String repo,int page){
        super.onPageLoad(user,repo,page);
        interactor.loadForkers(user, repo, page);
    }

}
