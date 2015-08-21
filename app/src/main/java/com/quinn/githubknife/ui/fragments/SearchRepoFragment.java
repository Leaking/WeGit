package com.quinn.githubknife.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.SearchRepoPresenterImpl;
import com.quinn.githubknife.ui.activity.RepoActivity;
import com.quinn.githubknife.ui.adapter.RepoAdapter;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 8/19/15.
 */
public class SearchRepoFragment extends BaseFragment implements RecycleItemClickListener {

    public final static String TAG = SearchRepoFragment.class.getSimpleName();

    private RepoAdapter adapter;
    private SearchUserFragment.TotalCountCallback callback;

    public static SearchRepoFragment getInstance(String keywords) {
        SearchRepoFragment searchRepoFragment = new SearchRepoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keywords", keywords);
        searchRepoFragment.setArguments(bundle);
        return searchRepoFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataItems = new ArrayList<Repository>();
        presenter = new SearchRepoPresenterImpl(this.getActivity(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (SearchUserFragment.TotalCountCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement TotalCountCallback");
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        adapter = new RepoAdapter(dataItems);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        L.i("onCreateView SearchUserFragment");
        return view;
    }


    @Override
    public void setItems(List items) {
        super.setItems(items);
        /**
         * first item mean the total_count ,not a real repo
         */
        int total_count = ((Repository)items.get(0)).getForks_count();
        callback.setTotalCount(total_count);
        items.remove(0);
        for (Object repo : items) {
            dataItems.add((Repository)repo);
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