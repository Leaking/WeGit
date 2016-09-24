package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.adapter.FragmentPagerAdapter;
import com.quinn.githubknife.ui.fragments.TrendingReposFragment;
import com.quinn.githubknife.ui.fragments.TrendingUsersFragment;
import com.quinn.githubknife.utils.Constants;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TrendingActivity extends BaseActivity {

    private final static String TAG = "TrendingActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private FragmentPagerAdapter adapter;

    private String trendingRepoUrl = Constants.TRENDING_BASE_URL;
    private String trendingUserUrl = "";
    private TrendingReposFragment fragment;
    private final String[] languages = getResources().getStringArray(R.array.language);
    private final String[] urlParams = getResources().getStringArray(R.array.language_param);
    private static final HashMap<String, String> languageUrlMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_fo);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.Trending);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialLanguages();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = TrendingReposFragment.getInstance(trendingRepoUrl);
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void initialLanguages() {
        if(languages == null || urlParams == null || languages.length != urlParams.length || languages.length == 0) {
            throw new IllegalStateException("You have not define languages");
        }

        for(int i = 0, count = languages.length; i < count; i++) {
            languageUrlMap.put(languages[i], urlParams[i]);
        }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_trending, menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_set:
                showPreferenceDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPreferenceDialog(){
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        int currentIndex = 0;

        new MaterialDialog.Builder(this)
                .title(R.string.search)
                .items(languages)
                .itemsCallbackSingleChoice(currentIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Log.i(TAG, "onSelection " + text);
                        if(languageUrlMap.get(text).length() != 0) {
                            trendingRepoUrl = Constants.TRENDING_BASE_URL + File.separator + languageUrlMap.get(text);
                        }
                        Log.i(TAG, "onSelection trendingRepoUrl " + trendingRepoUrl);
                        fragment.reload(trendingRepoUrl);
                        return true;
                    }
                })
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .cancelable(true)
                .show();
    }



    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, TrendingActivity.class);
        return intent;
    }



}
