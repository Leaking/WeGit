package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.fragments.BaseFragment;
import com.quinn.githubknife.ui.fragments.SearchRepoFragment;
import com.quinn.githubknife.ui.fragments.SearchUserFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity implements SearchUserFragment.TotalCountCallback {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private SearchActivity.SEARCH_TYPE search_type;
    private String query;
    private BaseFragment fragment;


    public static void launch(Context context, Bundle bundle){
        Intent intent = new Intent(context,SearchResultActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            search_type = (SearchActivity.SEARCH_TYPE) bundle.getSerializable("search_type");
            query = bundle.getString("query");
        }

        if(search_type == SearchActivity.SEARCH_TYPE.SEARCH_REPO){
            toolbar.setTitle("Repository");
            fragment = SearchRepoFragment.getInstance(query);
        }else if(search_type == SearchActivity.SEARCH_TYPE.SEARCH_USER){
            toolbar.setTitle("User");
            fragment = SearchUserFragment.getInstance(query);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTotalCount(int count) {
        toolbar.setSubtitle(String.valueOf(count) + getResources().getString(R.string.search_result_count));
    }
}
