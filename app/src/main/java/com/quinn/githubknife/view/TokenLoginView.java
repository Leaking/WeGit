package com.quinn.githubknife.view;

/**
 * Created by Quinn on 8/1/15.
 */
public interface TokenLoginView extends ErrorView,ProgressView{
    public void tokenCreated(String token);
}
