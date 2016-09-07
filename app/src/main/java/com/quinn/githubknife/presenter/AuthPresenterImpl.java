package com.quinn.githubknife.presenter;

import android.content.Context;
import android.util.Log;

import com.quinn.githubknife.interactor.UserInfoInteractor;
import com.quinn.githubknife.interactor.UserInfoInteractorImpl;
import com.quinn.githubknife.listener.OnLoadUserInfoListener;
import com.quinn.githubknife.view.MainAuthView;
import com.quinn.httpknife.github.User;

/**
 * Created by Quinn on 7/22/15.
 */
public class AuthPresenterImpl implements AuthPresenter,OnLoadUserInfoListener {

    public static final String TAG = "AuthPresenterImpl";

    private Context context;
    private MainAuthView view;
    private UserInfoInteractor authInteractor;

    public AuthPresenterImpl(Context context, MainAuthView view){
        this.context = context;
        this.view = view;
        this.authInteractor = new UserInfoInteractorImpl(context, this);
    }

    @Override
    public void auth() {
        this.view.showProgress();
        this.authInteractor.auth();;
    }





    @Override
    public void onFinish(User user) {
        view.hideProgress();
        view.doneAuth(user);
    }

    @Override
    public void updateFollowState(boolean isFollow) {

    }

    @Override
    public void loadStarredCount(int count) {

    }


    @Override
    public void onError(String msg) {
        Log.i(TAG,"onError " + msg);
        view.hideProgress();
        view.onError(msg);
    }
}
