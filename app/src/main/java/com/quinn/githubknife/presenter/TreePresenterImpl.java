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
    public void onTreeLoad(String user,String repo,String sha){
        super.onTreeLoad(user, repo, sha);
        interactor.loadTree(user, repo, sha);
    }

}
