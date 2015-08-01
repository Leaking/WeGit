package com.quinn.githubknife.interactor;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.R;
import com.quinn.githubknife.listener.OnTokenCreatedListener;
import com.quinn.httpknife.github.AuthError;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubError;
import com.quinn.httpknife.github.GithubImpl;

/**
 * Created by Quinn on 8/1/15.
 */
public class TokenInteractorImpl implements TokenInteractor {

    private final static int TOKEN_CREATED = 1;
    private final static int ERROR = 2;


    private OnTokenCreatedListener listener;
    private Context context;
    private Github github;
    private Handler handler;

    public TokenInteractorImpl(Context context, final OnTokenCreatedListener listener){
        this.context = context;
        this.listener = listener;
        github = new GithubImpl(context);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case TOKEN_CREATED:
                        String token = (String)msg.obj;
                        listener.onTokenCreated(token);
                        break;
                    case ERROR:
                        String errorMsg = (String)msg.obj;
                        listener.onError(errorMsg);
                        break;
                }

            }
        };
    }

    public void createToken(final String username,final String password){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String token = github.createToken(username,password);
                    Message msg = new Message();
                    msg.what = TOKEN_CREATED;
                    msg.obj = token;
                    handler.sendMessage(msg);
                } catch (GithubError githubError) {
                    githubError.printStackTrace();
                    Message msg = new Message();
                    msg.what = ERROR;
                    msg.obj = context.getResources().getString(R.string.network_error);
                    handler.sendMessage(msg);
                } catch (AuthError authError) {
                    authError.printStackTrace();
                    Message msg = new Message();
                    msg.what = ERROR;
                    msg.obj = context.getResources().getString(R.string.auth_error);
                    handler.sendMessage(msg);
                }
            }
        }).start();;


    }

}
