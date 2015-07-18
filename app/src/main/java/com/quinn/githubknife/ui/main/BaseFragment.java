package com.quinn.githubknife.ui.main;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.quinn.githubknife.account.GitHubAccount;

/**
 * Created by Quinn on 7/16/15.
 */
public class BaseFragment extends Fragment {


    protected GitHubAccount gitHubAccount;
    private GithubAccountCallBack callBack;

    public interface GithubAccountCallBack{
        public GitHubAccount getGithubAccount();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (GithubAccountCallBack)activity;
            gitHubAccount = callBack.getGithubAccount();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement TokenCallBack");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        gitHubAccount = callBack.getGithubAccount();
    }
}
