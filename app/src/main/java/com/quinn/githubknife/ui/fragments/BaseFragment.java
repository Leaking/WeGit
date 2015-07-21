package com.quinn.githubknife.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.Github;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Quinn on 7/16/15.
 */
public class BaseFragment extends Fragment implements onLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    protected Github github;
    protected GitHubAccount gitHubAccount;
    private GithubAccountCallBack callBack;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected int page = 1;

    private int visibleItemCount;
    private int firstVisibleItem;
    private int totalItemCount;
    protected boolean loading;
    protected boolean haveMore;
    protected int currPage;
    private LinearLayoutManager layoutManager;



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
        swipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(
                R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, swipeRefreshLayout);
        layoutManager = new LinearLayoutManager(this.getActivity());
        loading = false;
        haveMore = true;
        currPage = 1;
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                L.i("visibleItemCount = " + visibleItemCount);
                L.i("totalItemCount = " + totalItemCount);
                L.i("firstVisibleItem = " + firstVisibleItem);
                L.i("lastVisibleItem = " + lastVisibleItem);

                if(haveMore && !loading && (lastVisibleItem + 1) == totalItemCount){
                    L.i("加载更多");
                    loadMore();
                }
            }
        });
        //setupRecyclerView(rv);

        return swipeRefreshLayout;
    }


    @Override
    public void loadMore() {
        loading = true;
    }


    @Override
    public void onRefresh() {

    }

}
