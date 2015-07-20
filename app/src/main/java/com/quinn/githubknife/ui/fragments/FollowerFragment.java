package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.ui.activity.UsersAdapter;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 7/15/15.
 */
public class FollowerFragment extends BaseFragment {

    private Github github;
    private UsersAdapter adapter;
    private List<User> follwers = new ArrayList<User>();


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        L.i("onResume FollowerFragment");
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("onCreate FollowerFragment");
        github = new GithubImpl(this.getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        adapter = new UsersAdapter(follwers);
        recyclerView.setAdapter(adapter);
        L.i("onCreateView FollowerFragment");

        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                adapter.notifyDataSetChanged();
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<User> fol = github.myFollwers(gitHubAccount.getAuthToken());
                    for(User user: fol){
                        follwers.add(user);
                    }
                    handler.sendEmptyMessage(1);
                    L.i("followers = " + follwers);
                }catch (IllegalStateException e){
                    L.i("internet error");
                }
            }
        }).start();;

        return view;
    }
}
