package com.quinn.githubknife.interactor;

import android.content.Context;

import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnLoadRepoAndEventPreviewListener;
import com.quinn.githubknife.model.GithubService;
import com.quinn.githubknife.model.RetrofitUtil;
import com.quinn.httpknife.github.Repository;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Quinn on 10/16/15.
 */
public class RepoAndEventPreviewInteractorImpl implements  RepoAndEventPreviewInteractor {

    private Context context;
    private OnLoadRepoAndEventPreviewListener listener;
    private GithubService service;
    private GitHubAccount gitHubAccount;


    public RepoAndEventPreviewInteractorImpl(final Context context, final OnLoadRepoAndEventPreviewListener listener){
        this.context = context;
        this.listener = listener;
        this.service = RetrofitUtil.getJsonRetrofitInstance(context).create(GithubService.class);
        this.gitHubAccount = GitHubAccount.getInstance(context);
    }


    @Override
    public void previewRepo(final int page, final String user) {
        Call<List<Repository>> call = service.userRepo(user, String.valueOf(page));
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Response<List<Repository>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    previewRepo(page, user);
                } else if (response.isSuccess()) {
                    listener.repoItems(response.body());
                } else {
                    listener.loadRepoError();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                listener.loadRepoError();
            }
        });
    }

    @Override
    public void previewEvent(int page, String user) {

    }
}
