package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.view.ListFragmentView;

/**
 * Created by Quinn on 9/25/15.
 */
public class BranchesPresenterImpl extends PresenterAdapter{

    public static final String TAG = BranchesPresenterImpl.class.getSimpleName();

    public BranchesPresenterImpl(Context context, ListFragmentView view){
        super(context, view);
    }

    @Override
    public void onPageLoad(String owner,String repo,int page){
        super.onPageLoad(owner,repo,page);
        interactor.loadBranches(owner, repo);
    }
}
