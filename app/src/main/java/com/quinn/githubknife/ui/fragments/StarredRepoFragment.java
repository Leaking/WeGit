package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.StarredRepoPresenterImpl;
import com.quinn.githubknife.ui.activity.RepoActivity;
import com.quinn.githubknife.ui.adapter.RepoAdapter;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 7/15/15.
 */
public class StarredRepoFragment extends BaseFragment implements RecycleItemClickListener {


    public final static String TAG = StarredRepoFragment.class.getSimpleName();
    private RepoAdapter adapter;

    public static StarredRepoFragment getInstance(String user) {
        StarredRepoFragment starredRepoFragment = new StarredRepoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", user);
        starredRepoFragment.setArguments(bundle);
        return starredRepoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataItems = new ArrayList<Repository>();
        presenter = new StarredRepoPresenterImpl(this.getActivity(), this);
        adapter = new RepoAdapter(dataItems);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void setItems(List<?> items) {
        super.setItems(items);
        for (Object repo : items) {
            dataItems.add((Repository) repo);
        }
        loading = false;
        if (items.size() < GithubImpl.DEFAULT_PAGE_SIZE)
            haveMore = false;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void intoItem(int position) {
        super.intoItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("repo", (Repository) dataItems.get(position));
        RepoActivity.launch(this.getActivity(), bundle);
    }

    @Override
    public void onItemClick(View view, int position) {
        intoItem(position);
    }


}
