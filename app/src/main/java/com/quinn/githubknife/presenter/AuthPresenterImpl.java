package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.AuthInteractor;
import com.quinn.githubknife.interactor.AuthInteractorImpl;
import com.quinn.githubknife.interactor.OnAuthFInishListener;
import com.quinn.githubknife.ui.view.MainAuthView;

/**
 * Created by Quinn on 7/22/15.
 */
public class AuthPresenterImpl implements AuthPresenter,OnAuthFInishListener {

    private Context context;
    private MainAuthView view;
    private AuthInteractor authInteractor;

    public AuthPresenterImpl(Context context, MainAuthView view){
        this.context = context;
        this.view = view;
        this.authInteractor = new AuthInteractorImpl(context, this);
    }

    @Override
    public void auth() {
        this.authInteractor.auth();;
    }

    @Override
    public void authFinish(String avatar) {
        view.doneAuth(avatar);
    }
}
