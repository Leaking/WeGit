package com.quinn.githubknife.interactor;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.PreferenceUtils;
import com.quinn.httpknife.github.Event;
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

    private final static String TAG = FindItemsInteractor.class.getSimpleName();
    private final static int LOAD_MORE_FAIL = 3;
    private final static int LOAD_FIRST_FAIL = 2;
    private final static int LOAD_SUCCESS = 1;

    private GitHubAccount gitHubAccount;
    private Github github;
    private OnLoadUserListListener listener;
    private Handler handler;

    public FindItemsInteractorImpl(Context context, final OnLoadUserListListener listener) {
        String name = PreferenceUtils.getString(context, PreferenceUtils.Key.ACCOUNT);
        if (name.isEmpty())
            name = "NO_ACCOUNT";
        Account account = new Account(name, GitHubAccount.ACCOUNT_TYPE);
        this.gitHubAccount = new GitHubAccount(account, context);
        this.github = new GithubImpl(context);
        this.listener = listener;
        this.handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case LOAD_SUCCESS:
                        List<User> users = (List<User>) msg.obj;
                        listener.onFinished(users);
                        break;
                    case LOAD_FIRST_FAIL:
                        listener.onError(true);
                        break;
                    case LOAD_MORE_FAIL:
                        listener.onError(false);
                        break;
                }

            }
        };
    }

    @Override
    public void loadMyFollowings(final int page) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                L.i("token == " + token);
                List<User> users = new ArrayList<User>();
                Message msg = new Message();

                try {
                    users = github.myFollwerings(token, page);
                } catch (GithubError githubError) {
                    githubError.printStackTrace();
                    if(page == 1){
                        handler.sendEmptyMessage(LOAD_FIRST_FAIL);
                    }else{
                        handler.sendEmptyMessage(LOAD_MORE_FAIL);
                    }
                    return;

                }
                msg.what = LOAD_SUCCESS;
                msg.obj = users;
                handler.sendMessage(msg);
            }
        }).start();
        ;
    }

    @Override
    public void loadMyFollwers(final int page) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                L.i("token == " + token);
                List<User> users = new ArrayList<User>();
                Message msg = new Message();

                try {
                    users = github.myFollwers(token, page);
                } catch (GithubError e) {
                    L.i("网络问题 loadMyFollwers");
                    if(page == 1){
                        handler.sendEmptyMessage(LOAD_FIRST_FAIL);
                    }else{
                        handler.sendEmptyMessage(LOAD_MORE_FAIL);
                    }
                    return;

                }
                msg.what = 1;
                msg.obj = users;
                handler.sendMessage(msg);
            }
        }).start();
        ;

    }

    @Override
    public void loadFollowerings(final String user, final int page) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                List<User> users = new ArrayList<User>();
                Message msg = new Message();

                try {
                    users = github.follwerings(user, page);
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadFollowerings");
                    if(page == 1){
                        handler.sendEmptyMessage(LOAD_FIRST_FAIL);
                    }else{
                        handler.sendEmptyMessage(LOAD_MORE_FAIL);
                    }
                    return;
                }

                msg.what = LOAD_SUCCESS;
                msg.obj = users;
                handler.sendMessage(msg);
            }
        }).start();
        ;
    }

    @Override
    public void loadFollwers(final String user, final int page) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                L.i("token == " + token);
                List<User> users = new ArrayList<User>();
                Message msg = new Message();
                try {
                    users = github.followers(user, page);
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadFollwers");
                    if(page == 1){
                        handler.sendEmptyMessage(LOAD_FIRST_FAIL);
                    }else{
                        handler.sendEmptyMessage(LOAD_MORE_FAIL);
                    }
                    return;
                }

                msg.what = LOAD_SUCCESS;
                msg.obj = users;
                handler.sendMessage(msg);
            }
        }).start();
        ;
    }

    @Override
    public void loadAuthUser() {

    }

    @Override
    public void loadAuthRepos() {

    }

    @Override
    public void loadRepo(final String user, final int page) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                L.i("token == " + token);
                List<Repository> repos = new ArrayList<Repository>();
                Message msg = new Message();
                try {
                    repos = github.repo(user, page);
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadRepo");
                    if(page == 1){
                        handler.sendEmptyMessage(LOAD_FIRST_FAIL);
                    }else{
                        handler.sendEmptyMessage(LOAD_MORE_FAIL);
                    }
                    return;

                }
                msg.what = LOAD_SUCCESS;
                msg.obj = repos;
                handler.sendMessage(msg);
            }
        }).start();
        ;
    }

    @Override
    public void loadStarredRepo(final String user, final int page) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                L.i("token == " + token);
                List<Repository> repos = new ArrayList<Repository>();
                Message msg = new Message();

                try {
                    repos = github.starred(user, page);
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadStarredRepo");
                    if(page == 1){
                        handler.sendEmptyMessage(LOAD_FIRST_FAIL);
                    }else{
                        handler.sendEmptyMessage(LOAD_MORE_FAIL);
                    }
                    return;

                }
                msg.what = LOAD_SUCCESS;
                msg.obj = repos;
                handler.sendMessage(msg);
            }
        }).start();
        ;
    }

    @Override
    public void loadReceivedEvents(final String user, final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                L.i("token == " + token);
                List<Event> events = new ArrayList<Event>();
                Message msg = new Message();

                try {
                    events = github.receivedEvent(user, page);
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadReceivedEvents");
                    if(page == 1){
                        handler.sendEmptyMessage(LOAD_FIRST_FAIL);
                    }else{
                        handler.sendEmptyMessage(LOAD_MORE_FAIL);
                    }
                    return;

                }
                msg.what = LOAD_SUCCESS;
                msg.obj = events;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void loadUserEvents(final String user, final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                L.i("token == " + token);
                List<Event> events = new ArrayList<Event>();
                Message msg = new Message();

                try {
                    events = github.userEvent(user, page);
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadReceivedEvents");
                    if(page == 1){
                        handler.sendEmptyMessage(LOAD_FIRST_FAIL);
                    }else{
                        handler.sendEmptyMessage(LOAD_MORE_FAIL);
                    }
                    return;

                }
                msg.what = LOAD_SUCCESS;
                msg.obj = events;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void loadRepoEvents(String user, String repo, int page) {

    }

}
