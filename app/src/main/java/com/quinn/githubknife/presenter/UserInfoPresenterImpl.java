package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.OnLoadUserInfoListener;
import com.quinn.githubknife.interactor.UserInfoInteractor;
import com.quinn.githubknife.interactor.UserInfoInteractorImpl;
import com.quinn.githubknife.ui.view.UserInfoView;
import com.quinn.httpknife.github.User;

/**
 * Created by Quinn on 7/25/15.
 */
public class UserInfoPresenterImpl implements  UserInfoPresenter ,OnLoadUserInfoListener{


    private UserInfoView view;
    private UserInfoInteractor interactor;

    public UserInfoPresenterImpl(Context context, UserInfoView view){
        this.view = view;
        this.interactor = new UserInfoInteractorImpl(context,this);
    }

    @Override
    public void user(String user) {
        interactor.userInfo(user);
    }

    @Override
    public void hasFollow(String targetUser) {
        interactor.hasFollow(targetUser);
    }

    @Override
    public void follow(String targetUser) {
        interactor.follow(targetUser);
    }

    @Override
    public void unFollow(String targetUser) {
        interactor.unFollow(targetUser);
    }

    @Override
    public void onFinish(User user) {
        view.loadUser(user);
    }

    @Override
    public void updateFollowState(boolean isFollow) {
        view.setFollowState(isFollow);
    }

    @Override
    public void onError(String errorMsg) {
        view.failLoad(errorMsg);
    }
}
