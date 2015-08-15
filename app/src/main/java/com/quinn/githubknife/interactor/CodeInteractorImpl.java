package com.quinn.githubknife.interactor;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.listener.OnCodeListener;
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

    public CodeInteractorImpl(Context context, final OnCodeListener listener){
        this.listener = listener;
        this.context = context;
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
                        listener.onError("Get Content fall");
                        break;
                }
            }
        };
    }

    public void getContent(final String owner, final String repo, final String path){
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                }
            }
        }).start();;

    }


}
