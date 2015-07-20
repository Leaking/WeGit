package com.quinn.githubknife.presenter;

import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.interactor.FindUsersInteractor;
import com.quinn.githubknife.interactor.FindUsersInteractorImpl;
import com.quinn.githubknife.interactor.OnFinishUserLisstener;
import com.quinn.githubknife.ui.ListFragmentView;
import com.quinn.httpknife.github.User;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public class MyFollowerPresenterimpl implements UsersPresenter,OnFinishUserLisstener {

    private ListFragmentView view;
    private FindUsersInteractor interactor;
    private GitHubAccount gitHubAccount;

    public MyFollowerPresenterimpl(GitHubAccount gitHubAccount, ListFragmentView view){
        this.view = view;
        this.gitHubAccount = gitHubAccount;
        this.interactor = new FindUsersInteractorImpl(this);

    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onResume() {
        interactor.loadMyFollowings(gitHubAccount);
    }

    @Override
    public void onFinished(List<User> items) {
        view.setItems(items);
    }
}
