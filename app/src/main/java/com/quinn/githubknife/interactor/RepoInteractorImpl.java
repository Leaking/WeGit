package com.quinn.githubknife.interactor;

import android.content.Context;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnLoadRepoListener;
import com.quinn.githubknife.model.GithubService;
import com.quinn.githubknife.model.RetrofitUtil;
import com.quinn.httpknife.github.Branch;
import com.quinn.httpknife.github.Empty;
import com.quinn.httpknife.github.Repository;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

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

    private GithubService service;



    public RepoInteractorImpl(Context context, final OnLoadRepoListener listener){
        this.context = context;
        this.listener = listener;
        this.service = RetrofitUtil.getJsonRetrofitInstance(context).create(GithubService.class);
        this.gitHubAccount = GitHubAccount.getInstance(context);
    }


    @Override
    public void hasStar(final String owner, final String repo) {

        Call<Empty> call = service.hasStar(owner, repo);
        call.enqueue(new Callback<Empty>() {
            @Override
            public void onResponse(Response<Empty> response, Retrofit retrofit) {
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    star(owner,repo);
                }
                if (response.code() == 204) {
                    listener.setStarState(true);
                } else {
                    listener.setStarState(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onError(context.getString(R.string.fail_unfollow) + repo);
            }
        });


    }

    @Override
    public void star(final String owner, final String repo) {

        Call<Empty> call = service.star(owner, repo);
        call.enqueue(new Callback<Empty>() {
            @Override
            public void onResponse(Response<Empty> response, Retrofit retrofit) {
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    star(owner, repo);
                }
                if (response.code() == 204) {
                    listener.setStarState(true);
                } else {
                    listener.onError(context.getString(R.string.fail_unfollow) + repo);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onError(context.getString(R.string.fail_unfollow) + repo);
            }
        });


    }

    @Override
    public void unStar(final String owner, final String repo) {
        Call<Empty> call = service.unStar(owner, repo);
        call.enqueue(new Callback<Empty>() {
            @Override
            public void onResponse(Response<Empty> response, Retrofit retrofit) {
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    star(owner, repo);
                }
                if (response.code() == 204) {
                    listener.setStarState(false);
                } else {
                    listener.onError(context.getString(R.string.fail_unfollow) + repo);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onError(context.getString(R.string.fail_unfollow) + repo);
            }
        });
    }

    @Override
    public void fork(final String owner, final String repo) {

        Call<List<Repository>> call = service.fork(owner, repo);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Response<List<Repository>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    fork(owner, repo);
                }
                if (response.isSuccess()) {
                    listener.forkResult(true);
                } else {
                    listener.forkResult(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                RetrofitUtil.printThrowable(t);
                listener.forkResult(false);
            }
        });

    }


    @Override
    public void loadBranches(final String owner, final String repo) {

        final Call<List<Branch>> call = service.getBranches(owner, repo);
        call.enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Response<List<Branch>> response, Retrofit retrofit) {

                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadBranches(owner, repo);
                } else if (response.isSuccess()) {
                    listener.setBranches(response.body());
                } else {
                    listener.onError("加载分支失败");

                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onError("加载分支失败");
            }
        });
    }

    @Override
    public void loadRepo(String owner, String repo) {

    }
}
