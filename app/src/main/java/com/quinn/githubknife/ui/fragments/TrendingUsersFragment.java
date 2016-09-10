package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;

/**
 * Created by Quinn on 9/10/16.
 */
public class TrendingUsersFragment extends BaseFragment{

    public static TrendingUsersFragment getInstance(String url) {
        TrendingUsersFragment trendingUsersFragment = new TrendingUsersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        trendingUsersFragment.setArguments(bundle);
        return trendingUsersFragment;
    }
}
