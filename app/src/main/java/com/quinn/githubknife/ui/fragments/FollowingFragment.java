package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.ListFragmentPresenter;
import com.quinn.githubknife.presenter.MyFolloweringPresenter;
import com.quinn.githubknife.ui.activity.UsersAdapter;
import com.quinn.githubknife.ui.view.ListFragmentView;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 7/15/15.
 */
public class FollowingFragment extends BaseFragment implements ListFragmentView {

    private UsersAdapter adapter;
    private List<User> follwering = new ArrayList<User>();
    private ListFragmentPresenter presenter;


    public static FollowingFragment getInstance(String user){
        FollowingFragment followingFragment = new FollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", user);
        followingFragment.setArguments(bundle);
        return followingFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        L.i("onResume FollowerFragment");
        if(follwering.isEmpty())
            presenter.onPageLoad(currPage++,user);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("onCreate FollowerFragment");
        presenter = new MyFolloweringPresenter(this.getActivity(),this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        adapter = new UsersAdapter(follwering);
        recyclerView.setAdapter(adapter);
        L.i("onCreateView FollowerFragment");
        return view;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setItems(List items) {
        for(Object user:items){
            follwering.add((User)user);
        }
        loading = false;
        if(items.size() < GithubImpl.DEFAULT_PAGE_SIZE)
            haveMore = false;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void intoItem(int position) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void loadMore() {
        super.loadMore();
        L.i("loading page = " + currPage);
        presenter.onPageLoad(currPage++,user);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
