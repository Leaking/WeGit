package com.quinn.githubknife.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.TreePresenterImpl;
import com.quinn.githubknife.ui.activity.CodeActivity;
import com.quinn.githubknife.ui.activity.ImageActivity;
import com.quinn.githubknife.ui.adapter.TreeAdapter;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.githubknife.utils.FileUtils;
import com.quinn.githubknife.utils.L;
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


    public interface PathCallback {
        public void onPathChoosen(String path, String sha);

        public String getAbosolutePath(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PathCallback) {
            callback = (PathCallback) activity;
        } else {
            throw new IllegalStateException("TreeActivity have not implement PathCallback");
        }
    }

    public static TreeFragment getInstance(String owner, String repo, String branch) {
        TreeFragment treeFragment = new TreeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", owner);
        bundle.putString("repo", repo);
        bundle.putString("branch",branch);
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
        TreeItem item = (TreeItem) dataItems.get(position);
        callback.onPathChoosen(item.getPath(), item.getSha());
        branch = item.getSha();
        dataItems.clear();
        adapter.notifyDataSetChanged();
        presenter.onTreeLoad(user, repo, branch);
    }

    @Override
    public void onItemClick(View view, int position) {
        TreeItem treeItem = ((TreeItem) dataItems.get(position));
        if (treeItem.getType().equals(TreeItem.MODE_TREE)) {
            intoItem(position);

        } else if (treeItem.getType().equals(TreeItem.MODE_BLOB)) {
            if(FileUtils.isImage(treeItem.getPath())){
                String path = callback.getAbosolutePath(position) + "/" + treeItem.getPath();
                Bundle bundle = new Bundle();
                bundle.putString("path",path);
                bundle.putString("repo",repo);
                bundle.putString("user",user);
                bundle.putString("branch","master");
                ImageActivity.launch(this.getActivity(),bundle);
            }else {
                Bundle bundle = new Bundle();
                bundle.putString("owner", user);
                bundle.putString("repo", repo);
                String absPath = callback.getAbosolutePath(position);
                String path;
                if (absPath != null && absPath.isEmpty() == false)
                    path = callback.getAbosolutePath(position) + "/" + treeItem.getPath();
                else
                    path = ((TreeItem) dataItems.get(position)).getPath();
                bundle.putString("path", path);
                L.i(TAG, "file path = " + path);
                CodeActivity.launch(this.getActivity(), bundle);
            }
        }
    }



    public void loadCertainTree(String certainSha) {
        dataItems.clear();
        adapter.notifyDataSetChanged();
        presenter.onTreeLoad(user, repo, certainSha);
    }


}
