package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearBreadcrumb;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.fragments.TreeFragment;
import com.quinn.githubknife.ui.fragments.TreeFragment.PathCallback;
import com.quinn.githubknife.utils.L;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TreeActivity extends BaseActivity implements PathCallback, LinearBreadcrumb.SelectionCallback {


    private static final String TAG = TreeActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.breadCrumbs)
    LinearBreadcrumb breadCrumbs;

    private String user;
    private String repo;
    private TreeFragment fragment;


    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, TreeActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filetree);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = (String) bundle.getString("user");
            repo = (String) bundle.getString("repo");
        } else if (savedInstanceState != null) {
            user = (String) savedInstanceState.getString("user");
            repo = (String) savedInstanceState.getString("repo");
        }
        breadCrumbs.initRootCrumb();
        breadCrumbs.setCallback(this);
        toolbar.setTitle(repo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = TreeFragment.getInstance(user, repo);
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPathChoosen(String path, String sha) {
        breadCrumbs.addCrumb(new LinearBreadcrumb.Crumb(path, sha), true);
    }

    @Override
    public String getAbosolutePath(int position) {
        return breadCrumbs.getAbsolutePath(breadCrumbs.getCrumb(breadCrumbs.size() - 1),"/");
    }

    @Override
    public void onCrumbSelection(LinearBreadcrumb.Crumb crumb, String absolutePath, int count, int index) {
        L.i(TAG, "crumb = " + crumb);
        L.i(TAG, "absolutePath = " + absolutePath);
        L.i(TAG, "count = " + count);
        L.i(TAG, "index = " + index);
        for (int i = index + 1; i < count; i++) {
            breadCrumbs.removeCrumbAt(breadCrumbs.size() - 1);
        }
        breadCrumbs.setActive(crumb);
        fragment.loadCertainTree(crumb.getmAttachMsg());
    }
}
