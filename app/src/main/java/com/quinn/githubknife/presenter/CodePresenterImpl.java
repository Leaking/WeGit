package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.interactor.CodeInteractor;
import com.quinn.githubknife.interactor.CodeInteractorImpl;
import com.quinn.githubknife.listener.OnCodeListener;
import com.quinn.githubknife.view.CodeView;

/**
 * Created by Quinn on 8/15/15.
 */
public class CodePresenterImpl implements CodePresenter, OnCodeListener {


    private Context context;
    private CodeView view;
    private CodeInteractor interactor;



    public CodePresenterImpl(Context context, CodeView codeView){
        this.context = context;
        this.view = codeView;
        interactor = new CodeInteractorImpl(context,this);

    }
    @Override
    public void getContent(String owner, String repo, String path) {
        interactor.getContent(owner,repo,path);
    }

    @Override
    public void onCode(String content) {
        view.setCode(content);
    }

    @Override
    public void onError(String msg) {
        view.onError(msg);
    }
}
