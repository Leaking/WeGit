package com.quinn.githubknife.interactor;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.PreferenceUtils;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.User;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public class FindUsersInteractorImpl implements FindUsersInteractor {

    private GitHubAccount gitHubAccount;
    private Github github;
    private OnFinishUserLisstener listener;

    public FindUsersInteractorImpl(Context context, OnFinishUserLisstener listener){
        String name = PreferenceUtils.getString(context, PreferenceUtils.Key.ACCOUNT);
        if(name.isEmpty())
            name = "NO_ACCOUNT";
        Account account = new Account(name, GitHubAccount.ACCOUNT_TYPE);
        this.gitHubAccount = new GitHubAccount(account,context);
        this.github = new GithubImpl(context);
        this.listener = listener;
    }

    @Override
    public void loadMyFollowings(int page) {

    }

    @Override
    public void loadMyFollwers(final int page) {
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                List<User> users = (List<User>) msg.obj;
                listener.onFinished(users);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                L.i("token == " + token);
                List<User> users = github.myFollwers(token,page);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = users;
                handler.sendMessage(msg);
            }
        }).start();;

    }

    @Override
    public void loadFollowerings(String account,int page) {

    }

    @Override
    public void loadFollwers(String account, int page) {

    }
}
