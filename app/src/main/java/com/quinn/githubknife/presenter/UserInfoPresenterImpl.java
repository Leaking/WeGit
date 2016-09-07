package com.quinn.githubknife.presenter;

import android.content.Context;
import android.util.Log;

import com.quinn.githubknife.listener.OnLoadUserInfoListener;
import com.quinn.githubknife.interactor.UserInfoInteractor;
import com.quinn.githubknife.interactor.UserInfoInteractorImpl;
import com.quinn.githubknife.view.UserInfoView;
import com.quinn.httpknife.github.User;

/**
 * Created by Quinn on 7/25/15.
 */
public class UserInfoPresenterImpl implements UserInfoPresenter ,OnLoadUserInfoListener{

    public static final String TAG = "UserInfoPresenterImpl";

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
    public void starredCount(String user) {
        interactor.starredCount(user);
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
    public void loadStarredCount(int count) {
        view.setStarredCount(count);
    }

    @Override
    public void onError(String errorMsg) {
        Log.i(TAG,"onError " + errorMsg);
        view.onError(errorMsg);
    }
}
