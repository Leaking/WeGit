package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.RepoInteractor;
import com.quinn.githubknife.interactor.RepoInteractorImpl;
import com.quinn.githubknife.listener.OnLoadRepoListener;
import com.quinn.githubknife.view.RepoView;
import com.quinn.httpknife.github.Branch;

import java.util.List;

/**
 * Created by Quinn on 8/1/15.
 */
public class RepoPresenterImpl implements RepoPresenter,OnLoadRepoListener{

    private Context context;
    private RepoView view;
    private RepoInteractor interactor;

    public RepoPresenterImpl(Context context, RepoView view){
        this.context = context;
        this.view = view;
        this.interactor = new RepoInteractorImpl(this.context,this);
    }


    @Override
    public void hasStar(String owner, String repo) {
        view.showProgress();
        interactor.hasStar(owner,repo);
    }

    @Override
    public void fork(String owner, String repo) {
        this.interactor.fork(owner,repo);
    }

    @Override
    public void star(String owner, String repo) {
        interactor.star(owner, repo);
    }

    @Override
    public void unStar(String owner, String repo) {
        interactor.unStar(owner, repo);
    }

    @Override
    public void branches(String owner, String repo) {
        this.interactor.loadBranches(owner,repo);
    }

    @Override
    public void setStarState(boolean isStar) {
        this.view.setStarState(isStar);
    }

    @Override
    public void onError(String errorMsg) {
        this.view.onError(errorMsg);
    }

    @Override
    public void forkResult(boolean success) {
        this.view.forkResult(success);
    }

    @Override
    public void setBranches(List<Branch> branches) {
        //在最后一个任务隐藏进度条
        this.view.hideProgress();
        this.view.setBranches(branches);
    }
}
