package com.quinn.githubknife.interactor;

import android.content.Context;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnLoadUserInfoListener;
import com.quinn.githubknife.model.GithubService;
import com.quinn.githubknife.model.RetrofitUtil;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.Empty;
import com.quinn.httpknife.github.User;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Quinn on 7/22/15.
 */
public class UserInfoInteractorImpl implements UserInfoInteractor {

    private final static String TAG = UserInfoInteractorImpl.class.getSimpleName();

    private Context context;
    private GitHubAccount gitHubAccount;
    private OnLoadUserInfoListener listener;
    private GithubService service;



    public UserInfoInteractorImpl(Context context, final OnLoadUserInfoListener listener) {
        this.context = context;
        this.listener = listener;
        this.service = RetrofitUtil.getInstance(context).create(GithubService.class);
        this.gitHubAccount = GitHubAccount.getInstance(context);

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
                listener.onError(context.getString(R.string.fail_auth_user));
            }
        });

    }

    @Override
    public void userInfo(final String username) {
        Call<User> call = service.user(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    userInfo(username);
                }else if(response.isSuccess()) {
                    listener.onFinish(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
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
}
