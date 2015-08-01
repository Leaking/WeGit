package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.TokenInteractor;
import com.quinn.githubknife.interactor.TokenInteractorImpl;
import com.quinn.githubknife.listener.OnTokenCreatedListener;
import com.quinn.githubknife.view.TokenLoginView;

/**
 * Created by Quinn on 8/1/15.
 */
public class CreateTokenPresenterImpl implements CreateTokenPresenter,OnTokenCreatedListener {

    private Context context;
    private TokenInteractor interactor;
    private TokenLoginView view;


    public CreateTokenPresenterImpl(Context context, TokenLoginView view){
        this.context = context;
        this.view = view;
        this.interactor = new TokenInteractorImpl(context,this);
    }

    @Override
    public void createToken(String username, String password) {
        this.view.showProgress();
        interactor.createToken(username,password);
    }

    @Override
    public void onTokenCreated(String token) {
        this.view.hideProgress();
        this.view.tokenCreated(token);
    }

    @Override
    public void onError(String msg) {
        this.view.hideProgress();
        this.view.onError(msg);
    }
}
