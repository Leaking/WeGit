package com.quinn.githubknife.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.httpknife.github.Github;

/**
 * Created by Quinn on 7/16/15.
 */
public class BaseFragment extends Fragment {

    protected Github github;
    protected GitHubAccount gitHubAccount;
    private GithubAccountCallBack callBack;
    protected RecyclerView recyclerView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_friends, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);
        //setupRecyclerView(rv);
        return recyclerView;
    }



}
