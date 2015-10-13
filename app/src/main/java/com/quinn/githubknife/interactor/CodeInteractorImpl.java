package com.quinn.githubknife.interactor;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnCodeListener;
import com.quinn.githubknife.model.GithubService;
import com.quinn.githubknife.model.RetrofitUtil;
import com.quinn.httpknife.github.AuthError;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubError;
import com.quinn.httpknife.github.GithubImpl;

/**
 * Created by Quinn on 8/15/15.
 */
public class CodeInteractorImpl implements CodeInteractor{

    private final static int SUCCESS = 1;
    private final static int FAILURE = 2;

    private OnCodeListener listener;
    private Context context;
    private Github github;
    private Handler handler;
    private GitHubAccount gitHubAccount;
    private GithubService service;

    public CodeInteractorImpl(final Context context, final OnCodeListener listener){
        this.listener = listener;
        this.context = context;
        this.service = RetrofitUtil.getInstance(context).create(GithubService.class);
        this.gitHubAccount = GitHubAccount.getInstance(context);
        this.github = new GithubImpl(this.context);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case SUCCESS:
                        String content = (String) msg.obj;
                        listener.onCode(content);
                        break;

                    case FAILURE:
                        listener.onError(context.getString(R.string.fail_load_content));
                        break;
                }
            }
        };
    }

    @Override
    public void getContent(final String owner, final String repo, final String path){

//        Call<String> call = service.getRawContent(owner,repo,path);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Response<String> response, Retrofit retrofit) {
//                RetrofitUtil.printResponse(response);
//                if (response.code() == 401) {
//                    gitHubAccount.invalidateToken(RetrofitUtil.token);
//                    getContent(owner,repo,path);
//                }else if(response.isSuccess()){
//                    listener.onCode(response.body());
//                }
//            }
//            @Override
//            public void onFailure(Throwable t) {
//                RetrofitUtil.printThrowable(t);
//                listener.onError(context.getString(R.string.fail_load_content));
//            }
//        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = gitHubAccount.getAuthToken();
                github.makeAuthRequest(token);
                try {
                    String content = github.getRawContent(owner,repo,path);
                    Message msg = new Message();
                    msg.what = SUCCESS;
                    msg.obj = content;
                    handler.sendMessage(msg);
                } catch (GithubError githubError) {
                    githubError.printStackTrace();
                    Message msg = new Message();
                    msg.what = FAILURE;
                    handler.sendMessage(msg);
                }catch (AuthError authError) {
                    authError.printStackTrace();
                    gitHubAccount.invalidateToken(token);
                    getContent(owner,repo,path);
                }
            }
        }).start();;

    }


}
