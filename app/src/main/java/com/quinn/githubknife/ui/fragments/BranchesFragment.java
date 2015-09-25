package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.BranchesPresenterImpl;
import com.quinn.githubknife.ui.adapter.BranchAdapter;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.Branch;
import com.quinn.httpknife.github.GithubImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 9/25/15.
 */
public class BranchesFragment extends BaseFragment implements RecycleItemClickListener {

    public final static String TAG = BranchesFragment.class.getSimpleName();
    private BranchAdapter adapter;


    public static BranchesFragment getInstance(String owner,String repo){
        L.i(TAG, "create Fragment");
        BranchesFragment branchesFragment = new BranchesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", owner);
        bundle.putString("repo", repo);
        branchesFragment.setArguments(bundle);
        return branchesFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataItems = new ArrayList<Branch>();
        adapter = new BranchAdapter(dataItems);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        recyclerView.setAdapter(adapter);
        presenter = new BranchesPresenterImpl(this.getActivity(),this);
        adapter.setOnItemClickListener(this);
        return view;
    }





    @Override
    public void setItems(List items) {
        super.setItems(items);

        for(Object branch:items){
            dataItems.add((Branch)branch);
        }
        loading = false;
        if(items.size() < GithubImpl.DEFAULT_PAGE_SIZE)
            haveMore = false;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void intoItem(int position) {
        super.intoItem(position);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("user",(User)dataItems.get(position));
//        UserInfoActivity.launch(this.getActivity(), bundle);
    }

    @Override
    public void onItemClick(View view, int position) {
        intoItem(position);
    }
}
