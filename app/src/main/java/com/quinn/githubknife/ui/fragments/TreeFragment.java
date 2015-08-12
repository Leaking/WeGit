package com.quinn.githubknife.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.TreePresenterImpl;
import com.quinn.githubknife.ui.activity.TreeAdapter;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.TreeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 7/15/15.
 */
public class TreeFragment extends BaseFragment implements RecycleItemClickListener {

    public final static String TAG = TreeFragment.class.getSimpleName();

    private TreeAdapter adapter;

    private PathCallback callback;

    public interface PathCallback{
        public void onPathChoosen(String path);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof PathCallback){
            callback = (PathCallback) activity;
        }else{
            throw new IllegalStateException("FileTreeActivity have not implement PathCallback");
        }
    }

    public static TreeFragment getInstance(String owner,String repo) {
        TreeFragment treeFragment = new TreeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", owner);
        bundle.putString("repo", repo);
        treeFragment.setArguments(bundle);
        return treeFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataItems = new ArrayList<TreeItem>();
        presenter = new TreePresenterImpl(this.getActivity(), this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        adapter = new TreeAdapter(dataItems);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void setItems(List items) {
        super.setItems(items);
        for (Object user : items) {
            dataItems.add((TreeItem) user);
        }
        loading = false;
        if (items.size() < GithubImpl.DEFAULT_PAGE_SIZE)
            haveMore = false;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void intoItem(int position) {
        super.intoItem(position);
        callback.onPathChoosen(((TreeItem) dataItems.get(position)).getPath());
        String sha = ((TreeItem)dataItems.get(position)).getSha();
        dataItems.clear();
        adapter.notifyDataSetChanged();
        presenter.onTreeLoad(user,repo,sha);
    }

    @Override
    public void onItemClick(View view, int position) {
        TreeItem treeItem = ((TreeItem)dataItems.get(position));
        if(treeItem.getType().equals(TreeItem.MODE_TREE)){
            intoItem(position);
        }
    }


}
