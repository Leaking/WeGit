package com.quinn.githubknife.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.fragments.FollowerFragment;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoActivity extends BaseActivity {

    private static final String TAG = FoActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fo);
        ButterKnife.bind(this);
        Bundle bundle  = getIntent().getExtras();
        if(bundle != null){
            user = (User)bundle.getSerializable("user");
        }else if(savedInstanceState != null){
            user = (User)savedInstanceState.getSerializable("user");
        }
        L.i(TAG, bundle.toString());
        toolbar.setTitle(user.getLogin());
        toolbar.setSubtitle("Follower");

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FollowerFragment fragment = FollowerFragment.getInstance(user.getLogin());
        fragmentTransaction.add(R.id.container,fragment);
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
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user",user);
    }
}
