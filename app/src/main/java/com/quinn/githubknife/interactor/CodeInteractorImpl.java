package com.quinn.githubknife.interactor;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnCodeListener;
import com.quinn.githubknife.utils.PreferenceUtils;
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

    public CodeInteractorImpl(final Context context, final OnCodeListener listener){
        this.listener = listener;
        this.context = context;
        String name = PreferenceUtils.getString(context, PreferenceUtils.Key.ACCOUNT);
        if (name.isEmpty())
            name = "NO_ACCOUNT";
        Account account = new Account(name, GitHubAccount.ACCOUNT_TYPE);
        this.gitHubAccount = new GitHubAccount(account, context);
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

    public void getContent(final String owner, final String repo, final String path){
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
