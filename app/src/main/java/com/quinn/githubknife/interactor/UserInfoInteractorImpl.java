package com.quinn.githubknife.interactor;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnLoadUserInfoListener;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.PreferenceUtils;
import com.quinn.httpknife.github.AuthError;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubError;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.User;

/**
 * Created by Quinn on 7/22/15.
 */
public class UserInfoInteractorImpl implements UserInfoInteractor {

    private final static String TAG = UserInfoInteractorImpl.class.getSimpleName();
    private final static int USER_MSG = 1;
    private final static int FOLLOW_STATE_MSG = 2;
    private final static int FAIL_MSG = 3;

    private Context context;
    private GitHubAccount gitHubAccount;
    private Github github;
    private OnLoadUserInfoListener listener;
    private Handler handler;

    public UserInfoInteractorImpl(Context context, final OnLoadUserInfoListener listener) {
        this.context = context;
        String name = PreferenceUtils.getString(context, PreferenceUtils.Key.ACCOUNT);
        if (name.isEmpty())
            name = "NO_ACCOUNT";
        Account account = new Account(name, GitHubAccount.ACCOUNT_TYPE);
        this.gitHubAccount = new GitHubAccount(account, context);
        this.github = new GithubImpl(context);
        this.listener = listener;

        this.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case USER_MSG:
                        User user = (User) msg.obj;
                        listener.onFinish(user);
                        break;
                    case FOLLOW_STATE_MSG:
                        boolean isFollow = (boolean) msg.obj;
                        listener.updateFollowState(isFollow);
                        break;
                    case FAIL_MSG:
                        listener.onError((String) msg.obj);
                        break;

                }

            }
        };
    }


    @Override
    public void auth() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                L.i(TAG, "token = " + token);
                User user = null;
                try {
                    user = github.authUser(token);
                    L.i(TAG, user.toString());
                    L.i("Get new avatar = " + user.getAvatar_url());
                } catch (GithubError e) {
                    L.i("update avatar url fail");
                } catch (AuthError authError) {
                    authError.printStackTrace();
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = user;
                handler.sendMessage(msg);
            }
        }).start();

    }

    @Override
    public void userInfo(final String username) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                User user = null;
                try {
                    user = github.user(username);
                    L.i(TAG, user.toString());
                } catch (GithubError e) {
                    L.i(TAG, "userinfo fail");//f
                    Message msg = new Message();
                    msg.what = FAIL_MSG;
                    msg.obj = context.getString(R.string.fail_load_userInfo) + username;
                    handler.sendMessage(msg);
                    return;
                } catch (AuthError authError) {
                    authError.printStackTrace();
                    gitHubAccount.invalidateToken(token);
                    userInfo(username);
                }
                Message msg = new Message();
                msg.what = USER_MSG;
                msg.obj = user;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void hasFollow(final String targetUser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                boolean ifFollow = false;
                try {
                    ifFollow = github.hasFollow(targetUser);
                } catch (GithubError e) {
                    Message msg = new Message();
                    msg.what = FAIL_MSG;
                    msg.obj = context.getString(R.string.fail_load_relation);
                    handler.sendMessage(msg);
                    return;
                } catch (AuthError authError) {
                    authError.printStackTrace();
                    gitHubAccount.invalidateToken(token);
                    hasFollow(targetUser);
                }
                Message msg = new Message();
                msg.what = FOLLOW_STATE_MSG;
                msg.obj = ifFollow;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void follow(final String targetUser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                boolean ifFollow = false;
                try {
                    ifFollow = github.follow(targetUser);
                } catch (GithubError e) {
                    Message msg = new Message();
                    msg.what = FAIL_MSG;
                    msg.obj = context.getString(R.string.fail_follow) + targetUser;
                    handler.sendMessage(msg);
                    return;
                } catch (AuthError authError) {
                    authError.printStackTrace();
                    gitHubAccount.invalidateToken(token);
                    follow(targetUser);
                }
                Message msg = new Message();
                msg.what = FOLLOW_STATE_MSG;
                msg.obj = ifFollow;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void unFollow(final String targetUser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                boolean unFollow = true;
                try {
                    unFollow = github.unfollow(targetUser);
                    unFollow = !unFollow;
                } catch (GithubError e) {
                    Message msg = new Message();
                    msg.what = FAIL_MSG;
                    msg.obj = context.getString(R.string.fail_unfollow) + targetUser;
                    handler.sendMessage(msg);
                    return;
                } catch (AuthError authError) {
                    authError.printStackTrace();
                    gitHubAccount.invalidateToken(token);
                    unFollow(targetUser);
                }
                Message msg = new Message();
                msg.what = FOLLOW_STATE_MSG;
                msg.obj = unFollow;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
