package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.FolloweringPresenterImpl;
import com.quinn.githubknife.ui.activity.UserInfoActivity;
import com.quinn.githubknife.ui.activity.UsersAdapter;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 7/15/15.
 */
public class FollowingFragment extends BaseFragment implements RecycleItemClickListener {

    public final static String TAG = FollowingFragment.class.getSimpleName();

    private UsersAdapter adapter;


    public static FollowingFragment getInstance(String user){
        FollowingFragment followingFragment = new FollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", user);
        followingFragment.setArguments(bundle);
        return followingFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("onCreate FollowerFragment");
        dataItems = new ArrayList<User>();
        presenter = new FolloweringPresenterImpl(this.getActivity(),this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        adapter = new UsersAdapter(dataItems);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        L.i("onCreateView FollowerFragment");
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
