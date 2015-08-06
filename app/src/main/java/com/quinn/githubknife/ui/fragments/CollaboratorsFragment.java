package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.CollaboratorsPresenterImpl;
import com.quinn.githubknife.ui.activity.UserInfoActivity;
import com.quinn.githubknife.ui.activity.UsersAdapter;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 8/6/15.
 * Show who stars or forks X repo
 */
public class CollaboratorsFragment extends BaseFragment implements RecycleItemClickListener {

    public final static String TAG = CollaboratorsFragment.class.getSimpleName();
    private UsersAdapter adapter;


    public static CollaboratorsFragment getInstance(String user,String repo){
        L.i(TAG, "create Fragment");
        CollaboratorsFragment collaboratorsFragment = new CollaboratorsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", user);
        bundle.putString("repo", repo);
        collaboratorsFragment.setArguments(bundle);
        return collaboratorsFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataItems = new ArrayList<User>();
        adapter = new UsersAdapter(dataItems);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        recyclerView.setAdapter(adapter);

        presenter = new CollaboratorsPresenterImpl(this.getActivity(),this);
        adapter.setOnItemClickListener(this);
        return view;
    }





    @Override
    public void setItems(List items) {
        super.setItems(items);

        for(Object user:items){
            dataItems.add((User)user);
        }
        loading = false;
        if(items.size() < GithubImpl.DEFAULT_PAGE_SIZE)
            haveMore = false;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void intoItem(int position) {
        super.intoItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",(User)dataItems.get(position));
        UserInfoActivity.launch(this.getActivity(), bundle);
    }

    @Override
    public void onItemClick(View view, int position) {
        intoItem(position);
    }
}
