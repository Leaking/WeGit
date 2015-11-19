package com.quinn.githubknife.interactor;

import android.content.Context;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnCodeListener;
import com.quinn.githubknife.model.GithubService;
import com.quinn.githubknife.model.RetrofitUtil;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Quinn on 8/15/15.
 */
public class CodeInteractorImpl implements CodeInteractor{

    private final static int SUCCESS = 1;
    private final static int FAILURE = 2;

    private OnCodeListener listener;
    private Context context;
    private GitHubAccount gitHubAccount;
    private GithubService service;

    public CodeInteractorImpl(final Context context, final OnCodeListener listener){
        this.listener = listener;
        this.context = context;
        this.service = RetrofitUtil.getStringRetrofitInstance(context).create(GithubService.class);
        this.gitHubAccount = GitHubAccount.getInstance(context);
    }

    @Override
    public void getContent(final String owner, final String repo, final String path){

        Call<String> call = service.getRawContent(owner,repo,path);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    getContent(owner,repo,path);
                }else if(response.isSuccess()){
                    listener.onCode(response.body());
                }else{
                    listener.onError(context.getString(R.string.fail_load_content));
                }
            }
            @Override
            public void onFailure(Throwable t) {
                RetrofitUtil.printThrowable(t);
                listener.onError(context.getString(R.string.fail_load_content));
            }
        });

    }


}
