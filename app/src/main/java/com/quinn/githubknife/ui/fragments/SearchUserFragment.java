package com.quinn.githubknife.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.SearchPresenterImpl;
import com.quinn.githubknife.ui.activity.UserInfoActivity;
import com.quinn.githubknife.ui.adapter.UsersAdapter;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 8/19/15.
 */
public class SearchUserFragment extends BaseFragment implements RecycleItemClickListener {

    public final static String TAG = SearchUserFragment.class.getSimpleName();

    private UsersAdapter adapter;
    private TotalCountCallback callback;

    public static SearchUserFragment getInstance(String keywords) {
        SearchUserFragment searchUserFragment = new SearchUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keywords", keywords);
        searchUserFragment.setArguments(bundle);
        return searchUserFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("onCreate FollowerFragment");
        dataItems = new ArrayList<User>();
        presenter = new SearchPresenterImpl(this.getActivity(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (TotalCountCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement TotalCountCallback");
        }
    }

    public interface TotalCountCallback{
        public void setTotalCount(int count);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        adapter = new UsersAdapter(dataItems);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        L.i("onCreateView SearchUserFragment");
        return view;
    }


    @Override
    public void setItems(List items) {
        super.setItems(items);
        /**
         * first item mean the total_count ,not a real user
         */
        int total_count = ((User)items.get(0)).getFollowers();
        callback.setTotalCount(total_count);
        items.remove(0);
        for (Object user : items) {
            dataItems.add((User) user);
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
        bundle.putSerializable("user", (User) dataItems.get(position));
        UserInfoActivity.launch(this.getActivity(), bundle);
    }

    @Override
    public void onItemClick(View view, int position) {
        intoItem(position);
    }


}