package com.quinn.githubknife.interactor;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnLoadUserInfoListener;
import com.quinn.githubknife.model.GithubService;
import com.quinn.githubknife.model.RetrofitUtil;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.LogicUtils;
import com.quinn.httpknife.github.Empty;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.Repository;
import com.quinn.httpknife.github.User;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

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
    private OnLoadUserInfoListener listener;
    private GithubService service;
    private Handler handler;

    private Github github;



    public UserInfoInteractorImpl(Context context, final OnLoadUserInfoListener listener) {
        this.context = context;
        this.listener = listener;
        this.service = RetrofitUtil.getJsonInstance(context).create(GithubService.class);
        this.gitHubAccount = GitHubAccount.getInstance(context);
        this.github = new GithubImpl(context);


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
        Call<User> call = service.authUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    auth();
                } else if(response.isSuccess()) {
                    listener.onFinish(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                L.i(TAG,"onFailure = " + t.toString());
                RetrofitUtil.printThrowable(t);

                listener.onError(context.getString(R.string.fail_auth_user));
            }
        });

    }

    @Override
    public void userInfo(final String username) {


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String token = gitHubAccount.getAuthToken();
//                github.makeAuthRequest(token);
//                User user = null;
//                try {
//                    user = github.user(username);
//                    L.i(TAG, user.toString());
//                } catch (GithubError e) {
//                    L.i(TAG, "userinfo fail");//f
//                    Message msg = new Message();
//                    msg.what = FAIL_MSG;
//                    msg.obj = context.getString(R.string.fail_load_userInfo) + username;
//                    handler.sendMessage(msg);
//                    return;
//                } catch (AuthError authError) {
//                    authError.printStackTrace();
//                    gitHubAccount.invalidateToken(token);
//                    userInfo(username);
//                }
//                Message msg = new Message();
//                msg.what = USER_MSG;
//                msg.obj = user;
//                handler.sendMessage(msg);
//            }
//        }).start();
        Call<User> call = service.user(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    userInfo(username);
                }else if(response.isSuccess()) {
                    listener.onFinish(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                RetrofitUtil.printThrowable(t);

                listener.onError(context.getString(R.string.fail_load_userInfo) + username);
            }
        });

    }

    @Override
    public void hasFollow(final String targetUser) {
        Call<Empty> call = service.hasFollow(targetUser);
        call.enqueue(new Callback<Empty>() {
            @Override
            public void onResponse(Response<Empty> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    hasFollow(targetUser);
                } else if(response.code() == 204){
                    listener.updateFollowState(true);
                } else if(response.code() == 404){
                    listener.updateFollowState(false);
                } else {
                    listener.onError(context.getString(R.string.fail_load_relation));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onError(context.getString(R.string.fail_load_relation));
            }
        });

    }

    @Override
    public void follow(final String targetUser) {
        Call<Empty> call = service.follow(targetUser);
        call.enqueue(new Callback<Empty>() {
            @Override
            public void onResponse(Response<Empty> response, Retrofit retrofit) {
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    follow(targetUser);
                } if(response.code() == 204){
                    listener.updateFollowState(true);
                }else{
                    listener.onError(context.getString(R.string.fail_follow) + targetUser);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onError(context.getString(R.string.fail_follow) + targetUser);
            }
        });

    }

    @Override
    public void unFollow(final String targetUser) {
        Call<Empty> call = service.unFollow(targetUser);
        call.enqueue(new Callback<Empty>() {
            @Override
            public void onResponse(Response<Empty> response, Retrofit retrofit) {
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    unFollow(targetUser);
                }
                if (response.code() == 204) {
                    listener.updateFollowState(false);
                } else {
                    listener.onError(context.getString(R.string.fail_unfollow) + targetUser);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onError(context.getString(R.string.fail_unfollow) + targetUser);
            }
        });
    }

    @Override
    public void starredCount(final String user) {
        Call<List<Repository>> call = service.starredCount(user);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Response<List<Repository>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    starredCount(user);
                }
                if (response.isSuccess()) {
                    String linkHeader = response.headers().get("Link");
                    L.i(TAG,"linkHeader = " + linkHeader);
                    if(TextUtils.isEmpty(linkHeader) == false) {
                        int count = LogicUtils.parseStarredCount(linkHeader);
                        listener.loadStarredCount(count);
                    }else{
                        listener.loadStarredCount(response.body().size());
                    }
                } else {
                    listener.onError(context.getString(R.string.fail_unfollow) + user);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onError(context.getString(R.string.fail_unfollow) + user);
            }
        });
    }


}
