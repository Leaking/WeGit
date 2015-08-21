package com.quinn.githubknife.interactor;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnLoadItemListListener;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.PreferenceUtils;
import com.quinn.httpknife.github.Event;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubError;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.Repository;
import com.quinn.httpknife.github.Tree;
import com.quinn.httpknife.github.TreeItem;
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
    private OnLoadItemListListener listener;
    private Handler handler;

    public FindItemsInteractorImpl(Context context, final OnLoadItemListListener listener) {
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
                        List items = (List) msg.obj;
                        listener.onFinished(items);
                        break;
                    case LOAD_FIRST_FAIL:
                        listener.onError(true,(String)msg.obj);
                        break;
                    case LOAD_MORE_FAIL:
                        listener.onError(false,"");
                        break;
                }

            }
        };
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
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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
                List<Repository> repos = new ArrayList<Repository>();
                Message msg = new Message();
                try {
                    repos = github.repo(user, page);
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadStarredRepo");
                    if(page == 1){
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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
                List<Repository> repos = new ArrayList<Repository>();
                Message msg = new Message();
                try {
                    repos = github.starred(user, page);
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadStarredRepo");
                    if(page == 1){
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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

    @Override
    public void loadStargazers(final String owner, final String repo, final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                List<User> users = new ArrayList<User>();
                Message msg = new Message();
                try {
                    users = github.stargazers(owner, repo,page);
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadFollwers");
                    if(page == 1){
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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
    public void loadForkers(final String owner, final String repo, final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                List<User> users = new ArrayList<User>();
                Message msg = new Message();
                try {
                    users = github.forkers(owner, repo, page);
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadFollwers");
                    if(page == 1){
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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
    public void loadCollaborators(final String owner, final String repo, final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                List<User> users = new ArrayList<User>();
                Message msg = new Message();
                try {
                    users = github.collaborators(owner, repo, page);
                } catch (GithubError e) {
                    if(page == 1){
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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
    public void loadTree(final String owner,final String repo, final String sha) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                List<TreeItem> treeItems = new ArrayList<TreeItem>();
                Message msg = new Message();
                try {
                    Tree tree  = github.getTree(owner, repo, sha);
                    treeItems = tree.getTree();
                } catch (GithubError e) {
                    L.i(TAG,"网络问题 loadFollwers");
                    msg.what = LOAD_FIRST_FAIL;
                    msg.obj = e.getMessage();
                    handler.sendMessage(msg);
                    return;
                }
                msg.what = LOAD_SUCCESS;
                msg.obj = treeItems;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void searchUsers(final List<String> keywords,final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                List<User> users = new ArrayList<User>();
                Message msg = new Message();
                try {
                    users = github.searchUser(keywords,page);
                } catch (GithubError e) {
                    if(page == 1){
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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
    public void searchRepos(final List<String> keywords, final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                List<Repository> repos = new ArrayList<Repository>();
                Message msg = new Message();
                try {
                    repos = github.searchRepo(keywords, page);
                } catch (GithubError e) {
                    if(page == 1){
                        msg.what = LOAD_FIRST_FAIL;
                        msg.obj = e.getMessage();
                        handler.sendMessage(msg);
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
    }

}
