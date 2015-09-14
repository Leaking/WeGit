package com.quinn.githubknife.view;

import com.quinn.httpknife.github.User;

/**
 * Created by Quinn on 7/21/15.
 * MainActivity使用
 */
public interface MainAuthView extends ErrorView {


    public void doneAuth(User user);


}
