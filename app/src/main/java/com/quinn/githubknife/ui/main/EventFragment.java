package com.quinn.githubknife.ui.main;

import android.os.Bundle;

import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubImpl;

/**
 * Created by Quinn on 7/16/15.
 */
public class EventFragment extends BaseFragment {

    Github github;



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        L.i("onResume EventFragment");


//        Account account = new Account("Leaking", "com.githubknife");
//        AccountManager accountManager = AccountManager.get(this.getActivity());
//        final GitHubAccount gitHubAccount = new GitHubAccount(account,accountManager,this.getActivity());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String token = gitHubAccount.getAuthToken();
//                L.i("token === " + token);
//                List<User> fos = github.myFollwers(token);
//
//                L.i("ffff users = " + fos);
//            }
//        }).start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("onCreate EventFragment");

        github = new GithubImpl(this.getActivity());

    }


}
