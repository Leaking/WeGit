package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.fragments.CollaboratorsFragment;
import com.quinn.githubknife.ui.fragments.FollowerFragment;
import com.quinn.githubknife.ui.fragments.FollowingFragment;
import com.quinn.githubknife.ui.fragments.ForkersFragment;
import com.quinn.githubknife.ui.fragments.StargazersFragment;
import com.quinn.githubknife.ui.fragments.StarredRepoFragment;
import com.quinn.githubknife.ui.fragments.TreeFragment;
import com.quinn.githubknife.ui.fragments.UserRepoFragment;
import com.quinn.githubknife.utils.L;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoActivity extends BaseActivity {

    private static final String TAG = FoActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private String user;
    private String repo;
    private String contentType;



    public static void launch(Context context, Bundle bundle){
        Intent intent = new Intent(context,FoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fo);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            user = (String)bundle.getString("user");
            repo = (String)bundle.getString("repo");
            contentType = bundle.getString("fragment");
        }else if(savedInstanceState != null){
            user = (String)savedInstanceState.getString("user");
            repo = (String)savedInstanceState.getString("repo");
            contentType = savedInstanceState.getString("fragment");
        }
        L.i(TAG, bundle.toString());
        toolbar.setTitle(user);
        if(repo != null){
            toolbar.setTitle(repo);
        }
        toolbar.setSubtitle(subTitle(contentType));

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = contentFragment(contentType);
        fragmentTransaction.add(R.id.container,fragment);
        fragmentTransaction.commit();
    }


    public Fragment contentFragment(String type){
        if(type.equals(FollowerFragment.TAG)){
            return FollowerFragment.getInstance(user);
        }else if(type.equals(FollowingFragment.TAG)){
            return FollowingFragment.getInstance(user);
        }else if(type.equals(StarredRepoFragment.TAG)){
            return StarredRepoFragment.getInstance(user);
        }else if (type.equals(UserRepoFragment.TAG)) {
            return UserRepoFragment.getInstance(user);
        }else if(type.equals(StargazersFragment.TAG)){
            return StargazersFragment.getInstance(user, repo);
        }else if(type.equals(ForkersFragment.TAG)){
            return ForkersFragment.getInstance(user, repo);
        }else if(type.equals(CollaboratorsFragment.TAG)){
            return CollaboratorsFragment.getInstance(user, repo);
        }else if(type.equals(TreeFragment.TAG)){
            return TreeFragment.getInstance(user,repo);
        }

        return null;
    }

    public String subTitle(String type){
        if(type.equals(FollowerFragment.TAG)){
            return "Followers";
        }else if(type.equals(FollowingFragment.TAG)){
            return "Following";
        }else if(type.equals(StarredRepoFragment.TAG)){
            return "Starred";
        }else if (type.equals(UserRepoFragment.TAG)) {
            return "Repository";
        }else if (type.equals(StargazersFragment.TAG)) {
            return "stargazers";
        }else if (type.equals(ForkersFragment.TAG)) {
            return "forkers";
        }else if (type.equals(CollaboratorsFragment.TAG)){
            return "Contributors";
        }else if (type.equals(TreeFragment.TAG)){
            return "Code";
        }
        return null;
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
        outState.putString("user", user);
        outState.putString("repo", repo);
        outState.putString("fragment",contentType);
    }
}
