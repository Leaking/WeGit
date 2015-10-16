package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.RepoAndEventPreviewInteractor;
import com.quinn.githubknife.interactor.RepoAndEventPreviewInteractorImpl;
import com.quinn.githubknife.listener.OnLoadRepoAndEventPreviewListener;
import com.quinn.githubknife.view.RepoAndEventPreviewView;

import java.util.List;

/**
 * Created by Quinn on 10/16/15.
 */
public class RepoAndEventPreviewPresenterImpl implements RepoAndEventPreviewPresenter,OnLoadRepoAndEventPreviewListener {


    private Context context;
    private RepoAndEventPreviewView view;
    private RepoAndEventPreviewInteractor interactor;


    public RepoAndEventPreviewPresenterImpl(Context context, RepoAndEventPreviewView view){
        this.view = view;
        this.context = context;
        this.interactor = new RepoAndEventPreviewInteractorImpl(context,this);
    }

    @Override
    public void previewRepo(int page, String user) {
        interactor.previewRepo(page,user);
    }

    @Override
    public void previewEvent(int page, String user) {
        interactor.previewEvent(page, user);
    }

    @Override
    public void repoItems(List items) {
        view.repoItems(items);
    }

    @Override
    public void eventItems(List items) {
        view.eventItems(items);
    }

    @Override
    public void loadRepoError() {
        view.loadRepoError();
    }

    @Override
    public void loadEventError() {
        view.loadEventError();
    }
}
