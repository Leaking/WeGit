package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;

import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.GithubImpl;

/**
 * Created by Quinn on 7/16/15.
 */
public class EventFragment extends BaseFragment {


    public static EventFragment getInstance(String user){
        EventFragment eventFragment = new EventFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", user);
        eventFragment.setArguments(bundle);
        return eventFragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        L.i("onResume EventFragment");



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("onCreate EventFragment");

        github = new GithubImpl(this.getActivity());

    }


}
