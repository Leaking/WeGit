package com.quinn.githubknife.interactor;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnLoadRepoListener;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.PreferenceUtils;
import com.quinn.httpknife.github.AuthError;
import com.quinn.httpknife.github.Branch;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubError;
import com.quinn.httpknife.github.GithubImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 8/1/15.
 */
public class RepoInteractorImpl implements RepoInteractor{

    private final static String TAG = RepoInteractorImpl.class.getSimpleName();
    private final static int STAR_STATE = 1;
    private final static int FAIL = 2;
    private final static int FORK_RESULT = 3;
    private final static int BRANCHES = 4;

    private Context context;
    private GitHubAccount gitHubAccount;
    private OnLoadRepoListener listener;
    private Github github;
    private Handler handler;


    public RepoInteractorImpl(Context context, final OnLoadRepoListener listener){
        this.context = context;
        this.listener = listener;
        this.github = new GithubImpl(context);
        String name = PreferenceUtils.getString(context, PreferenceUtils.Key.ACCOUNT);
        if(name.isEmpty())
            name = "NO_ACCOUNT";
        Account account = new Account(name, GitHubAccount.ACCOUNT_TYPE);
        this.gitHubAccount = new GitHubAccount(account,context);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                    switch (msg.what){
                        case STAR_STATE:
                            boolean hasStar = (boolean) msg.obj;
                            listener.setStarState(hasStar);
                            break;
                        case FAIL:
                            String errorMsg = (String) msg.obj;
                            listener.onError(errorMsg);
                            break;
                        case FORK_RESULT:
                            boolean forkMsg = (boolean)msg.obj;
                            listener.forkResult(forkMsg);
                            break;
                        case BRANCHES:
                            List<Branch> branches = (List<Branch>)msg.obj;
                            listener.setBranches(branches);
                            break;

                    }
                }


        };

    }




    @Override
    public void hasStar(final String owner, final String repo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                try {
                    boolean hasStar = github.hasStarRepo(owner, repo);
                    Message msg = new Message();
                    msg.what = STAR_STATE;
                    msg.obj = hasStar;
                    handler.sendMessage(msg);
                } catch (GithubError githubError) {
                    githubError.printStackTrace();
                    Message msg = new Message();
                    msg.what = FAIL;
                    msg.obj = context.getResources().getString(R.string.network_error);
                    handler.sendMessage(msg);
                }  catch (AuthError authError) {
                    authError.printStackTrace();
                    gitHubAccount.invalidateToken(token);
                    hasStar(owner,repo);
                }
            }
        }).start();
    }

    @Override
    public void star(final String owner, final String repo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();

                try {
                    github.makeAuthRequest(token);
                    boolean hasStar = github.starRepo(owner, repo);
                    Message msg = new Message();
                    msg.what = STAR_STATE;
                    msg.obj = hasStar;
                    handler.sendMessage(msg);
                } catch (GithubError githubError) {
                    githubError.printStackTrace();
                    Message msg = new Message();
                    msg.what = FAIL;
                    msg.obj = context.getResources().getString(R.string.network_error);
                    handler.sendMessage(msg);
                }catch (AuthError authError) {
                    authError.printStackTrace();
                    gitHubAccount.invalidateToken(token);
                    star(owner, repo);
                }
            }
        }).start();
    }

    @Override
    public void unStar(final String owner, final String repo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                try {
                    github.makeAuthRequest(token);
                    boolean unstar = github.unStarRepo(owner, repo);
                    Message msg = new Message();
                    msg.what = STAR_STATE;
                    msg.obj = !unstar;
                    handler.sendMessage(msg);
                } catch (GithubError githubError) {
                    githubError.printStackTrace();
                    Message msg = new Message();
                    msg.what = FAIL;
                    msg.obj = context.getResources().getString(R.string.network_error);
                    handler.sendMessage(msg);
                }catch (AuthError authError) {
                    authError.printStackTrace();
                    gitHubAccount.invalidateToken(token);
                    unStar(owner, repo);
                }
            }
        }).start();
    }

    @Override
    public void fork(final String owner, final String repo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                try {
                    github.makeAuthRequest(token);
                    boolean success = github.fork(owner, repo);
                    Message msg = new Message();
                    msg.what = FORK_RESULT;
                    msg.obj = success;
                    handler.sendMessage(msg);
                } catch (GithubError githubError) {
                    githubError.printStackTrace();
                    Message msg = new Message();
                    msg.what = FAIL;
                    msg.obj = context.getResources().getString(R.string.network_error);
                    handler.sendMessage(msg);
                }catch (AuthError authError) {
                    authError.printStackTrace();
                    gitHubAccount.invalidateToken(token);
                    fork(owner, repo);
                }
            }
        }).start();
    }


    @Override
    public void loadBranches(final String owner, final String repo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                L.i(TAG, "loadBranches ");

                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                List<Branch> branches = new ArrayList<Branch>();
                Message msg = new Message();
                try {
                    branches = github.getBranches(owner, repo);
                    L.i(TAG,"branches = " + branches);
                } catch (GithubError e) {
                    msg.what = FAIL;
                    msg.obj = e.getMessage();
                    handler.sendMessage(msg);
                    return;
                } catch (AuthError authError) {
                    authError.printStackTrace();
                    gitHubAccount.invalidateToken(token);
                    loadBranches(owner, repo);
                }
                msg.what = BRANCHES;
                msg.obj = branches;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
