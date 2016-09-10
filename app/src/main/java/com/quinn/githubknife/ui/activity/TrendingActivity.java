package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.adapter.FragmentPagerAdapter;
import com.quinn.githubknife.ui.fragments.TrendingReposFragment;
import com.quinn.githubknife.ui.fragments.TrendingUsersFragment;
import com.quinn.githubknife.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TrendingActivity extends BaseActivity {

    private final static String TAG = "TrendingActivity";

    //UI
    @Bind(R.id.toolbar_main)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tabs)
    TabLayout tab;

    private FragmentPagerAdapter adapter;

    private String trendingRepoUrl = Constants.TRENDING_BASE_URL;
    private String trendingUserUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_list_viewpager);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.Trending);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
        adapter.clear();
        viewpager.setOffscreenPageLimit(2);
        adapter.addFragment(TrendingReposFragment.getInstance(trendingRepoUrl), getString(R.string.repository));
        adapter.addFragment(TrendingUsersFragment.getInstance(trendingRepoUrl), getString(R.string.user));
        adapter.notifyDataSetChanged();
        tab.setupWithViewPager(viewpager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, TrendingActivity.class);
        return intent;
    }

}
