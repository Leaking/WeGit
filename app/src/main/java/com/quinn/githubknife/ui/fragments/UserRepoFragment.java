package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.UserRepoPresenterImpl;
import com.quinn.githubknife.ui.activity.RepoAdapter;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 7/15/15.
 */
public class UserRepoFragment extends BaseFragment {


    public final static String TAG = UserRepoFragment.class.getSimpleName();

    private RepoAdapter adapter;


    public static UserRepoFragment getInstance(String user){
        UserRepoFragment userRepoFragment = new UserRepoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", user);
        userRepoFragment.setArguments(bundle);
        return userRepoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserRepoPresenterImpl(this.getActivity(),this);
        dataItems = new ArrayList<Repository>();
        adapter = new RepoAdapter(dataItems);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        adapter = new RepoAdapter(dataItems);
        recyclerView.setAdapter(adapter);
        L.i("onCreateView FollowerFragment");
        return view;
    }




    @Override
    public void setItems(List<?> items) {
        super.setItems(items);

        for(Object repo:items){
            dataItems.add((Repository) repo);
        }
        loading = false;
        if(items.size() < GithubImpl.DEFAULT_PAGE_SIZE)
            haveMore = false;
        adapter.notifyDataSetChanged();
    }


}
