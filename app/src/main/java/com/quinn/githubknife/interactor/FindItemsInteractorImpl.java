package com.quinn.githubknife.interactor;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.PreferenceUtils;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubError;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.Repository;
import com.quinn.httpknife.github.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public class FindItemsInteractorImpl implements FindItemsInteractor {

    private GitHubAccount gitHubAccount;
    private Github github;
    private OnFinishUserListener listener;

    public FindItemsInteractorImpl(Context context, OnFinishUserListener listener){
        String name = PreferenceUtils.getString(context, PreferenceUtils.Key.ACCOUNT);
        if(name.isEmpty())
            name = "NO_ACCOUNT";
        Account account = new Account(name, GitHubAccount.ACCOUNT_TYPE);
        this.gitHubAccount = new GitHubAccount(account,context);
        this.github = new GithubImpl(context);
        this.listener = listener;
    }

    @Override
    public void loadMyFollowings(final int page) {
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
                List<User> users = new ArrayList<User>();
                try {
                    users = github.myFollwerings(token,page);
                } catch (GithubError githubError) {
                    githubError.printStackTrace();
                    /*

                     */


                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = users;
                handler.sendMessage(msg);
            }
        }).start();;
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
                List<User> users = new ArrayList<User>();
                try{
                    users = github.myFollwers(token,page);
                }catch (GithubError e){
                    L.i("网络问题 loadMyFollwers");
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = users;
                handler.sendMessage(msg);
            }
        }).start();;

    }

    @Override
    public void loadFollowerings(final String user,final int page) {
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
                List<User> users = new ArrayList<User>();
                try{
                    users = github.follwerings(user,page);
                }catch (GithubError e){
                    L.i("网络问题 loadMyFollwers");
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = users;
                handler.sendMessage(msg);
            }
        }).start();;
    }

    @Override
    public void loadFollwers(final String user,final int page) {
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
                List<User> users = new ArrayList<User>();
                try{
                    users = github.followers(user,page);
                }catch (GithubError e){
                    L.i("网络问题 loadMyFollwers");
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = users;
                handler.sendMessage(msg);
            }
        }).start();;
    }

    @Override
    public void loadAuthUser(){

    }

    @Override
    public void loadAuthRepos() {

    }

    @Override
    public void loadRepo(final String user, final int page) {
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                List<Repository> repos = (List<Repository>) msg.obj;
                listener.onFinished(repos);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                L.i("token == " + token);
                List<Repository> repos = new ArrayList<Repository>();
                try{
                    repos = github.repo(user, page);
                }catch (GithubError e){
                    L.i("网络问题 loadMyFollwers");
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = repos;
                handler.sendMessage(msg);
            }
        }).start();;
    }

    @Override
    public void loadStarredRepo(final String user, final int page) {
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                List<Repository> repos = (List<Repository>) msg.obj;
                listener.onFinished(repos);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                L.i("token == " + token);
                List<Repository> repos = new ArrayList<Repository>();
                try{
                    repos = github.starred(user, page);
                }catch (GithubError e){
                    L.i("网络问题 loadMyFollwers");
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = repos;
                handler.sendMessage(msg);
            }
        }).start();;
    }

}
