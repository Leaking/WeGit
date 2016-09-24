package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.TrendingRepoPresenterImpl;
import com.quinn.githubknife.ui.activity.RepoActivity;
import com.quinn.githubknife.ui.adapter.TrendingRepoAdapter;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.httpknife.github.Repository;
import com.quinn.httpknife.github.TrendingRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 9/10/16.
 */
public class TrendingReposFragment extends BaseFragment implements RecycleItemClickListener {

    private String url;
    private TrendingRepoAdapter adapter;


    public static TrendingReposFragment getInstance(String url){
        TrendingReposFragment trendingReposFragment = new TrendingReposFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        trendingReposFragment.setArguments(bundle);
        return trendingReposFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataItems = new ArrayList<Repository>();
        presenter = new TrendingRepoPresenterImpl(this.getActivity(),this);
        adapter = new TrendingRepoAdapter(dataItems);
    }

    public void reload(String url) {
        this.trendingUrl = url;
        dataItems.clear();
        adapter.notifyDataSetChanged();
        currPage = 1;
        presenter.onPageLoad(url);
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
        for(Object repo:items){
            dataItems.add((TrendingRepo) repo);
        }
        loading = false;
        haveMore = false;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void intoItem(int position) {
        super.intoItem(position);
        RepoActivity.launch(this.getContext(), (Repository) dataItems.get(position));
    }

    @Override
    public void onItemClick(View view, int position) {
        intoItem(position);
    }
}
