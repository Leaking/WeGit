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
import com.quinn.githubknife.ui.fragments.FollowerFragment;
import com.quinn.githubknife.ui.fragments.FollowingFragment;
import com.quinn.githubknife.ui.fragments.StarredRepoFragment;
import com.quinn.githubknife.ui.fragments.UserRepoFragment;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoActivity extends BaseActivity {

    private static final String TAG = FoActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private User user;
    private String contentType;



    public static void  launch(Context context, Bundle bundle){
        Intent intent = new Intent(context,FoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fo);
        ButterKnife.bind(this);
        Bundle bundle  = getIntent().getExtras();
        if(bundle != null){
            user = (User)bundle.getSerializable("user");
            contentType = bundle.getString("fragment");
        }else if(savedInstanceState != null){
            user = (User)savedInstanceState.getSerializable("user");
            contentType = savedInstanceState.getString("fragment");
        }
        L.i(TAG, bundle.toString());
        toolbar.setTitle(user.getLogin());
        toolbar.setSubtitle(subTitle(contentType));

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = contentFragment(contentType,user.getLogin());
        fragmentTransaction.add(R.id.container,fragment);
        fragmentTransaction.commit();
    }


    public Fragment contentFragment(String type,String username){
        if(type.equals(FollowerFragment.TAG)){
            return FollowerFragment.getInstance(username);
        }else if(type.equals(FollowingFragment.TAG)){
            return FollowingFragment.getInstance(username);
        }else if(type.equals(StarredRepoFragment.TAG)){
            return StarredRepoFragment.getInstance(username);
        }else if (type.equals(UserRepoFragment.TAG)) {
            return UserRepoFragment.getInstance(username);
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
        outState.putSerializable("user", user);
        outState.putString("fragment",contentType);

    }
}
