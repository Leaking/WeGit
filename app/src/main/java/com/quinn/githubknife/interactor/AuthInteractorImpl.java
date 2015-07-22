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

/**
 * Created by Quinn on 7/22/15.
 */
public class AuthInteractorImpl implements  AuthInteractor {

    private Context context;
    private GitHubAccount gitHubAccount;
    private Github github;
    private OnAuthFInishListener listener;

    public AuthInteractorImpl(Context context, OnAuthFInishListener listener){
        String name = PreferenceUtils.getString(context, PreferenceUtils.Key.ACCOUNT);
        if(name.isEmpty())
            name = "NO_ACCOUNT";
        Account account = new Account(name, GitHubAccount.ACCOUNT_TYPE);
        this.gitHubAccount = new GitHubAccount(account,context);
        this.github = new GithubImpl(context);
        this.listener = listener;
    }

    @Override
    public void auth() {

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String avatar = (String)msg.obj;
                listener.authFinish(avatar);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                String avatar = "";
                try {
                    avatar = github.authUser(token).getAvatar_url();
                    L.i("Get new avatar = " + avatar);
                }catch (IllegalStateException e){
                    L.i("update avatar url fail");

                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = avatar;
                handler.sendMessage(msg);
            }
        }).start();


    }
}
