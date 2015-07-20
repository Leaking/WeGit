package com.quinn.githubknife.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.fragments.BaseFragment;
import com.quinn.githubknife.ui.fragments.ContributeRepoFragment;
import com.quinn.githubknife.ui.fragments.EventFragment;
import com.quinn.githubknife.ui.fragments.FollowerFragment;
import com.quinn.githubknife.ui.fragments.FollowingFragment;
import com.quinn.githubknife.ui.fragments.OwnRepoFragment;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BaseFragment.GithubAccountCallBack,NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationVIew;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tabs)
    TabLayout tab;

    private Adapter adapter;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("Github");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //tv = (TextView) findViewById(R.id.token);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Account account = new Account("Leaking", "com.githubknife");
//                getExistingAccountAuthToken(account,"ah");
//            }
//        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        toggle.syncState();
        mDrawerLayout.setDrawerListener(toggle);
        navigationVIew.setNavigationItemSelectedListener(this);
        adapter = new Adapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
        setUpTab(R.id.nav_home);
        //tab.set
        viewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = new Account("Leaking", "com.githubknife");
                getExistingAccountAuthToken(account, "ah");
            }
        });

//        AccountManager mAccountManager = AccountManager.get(getBaseContext());
//        GitHubAccount gitHubAccount = new GitHubAccount(account,mAccountManager);
//        String token = gitHubAccount.getAuthToken();
        //L.i("FollowerFragment token = " + token);


    }

    private void getExistingAccountAuthToken(final Account account, final String authTokenType) {
        System.out.println("try to get token in MainActivyt");

        final AccountManager mAccountManager = AccountManager.get(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(account, authTokenType, null, MainActivity.this, null, null);

                    L.i("herher");
                    Bundle bnd = future.getResult();

                    final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                    Log.d("udinic", "GetToken Bundle is " + bnd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        L.i("navigation item select = " + menuItem.getItemId());
        TextView tv = (TextView)mDrawerLayout.findViewById(R.id.headerText);
        L.i("header text = " + tv.getText().toString());


        setUpTab(menuItem.getItemId());

        return true;
    }

    public void setUpTab(int id){
        adapter.clear();
        switch (id){
            case R.id.nav_home:
                viewpager.setOffscreenPageLimit(3);
                EventFragment eventFragment = new EventFragment();
                Bundle bundle = new Bundle();
                bundle.putCharSequence("arg","event");
                eventFragment.setArguments(bundle);
                adapter.addFragment(new EventFragment(), "Events");
                adapter.addFragment(new OwnRepoFragment(),"Repository");
                adapter.addFragment(new ContributeRepoFragment(),"Contribute");
                break;
            case R.id.nav_friends:
                viewpager.setOffscreenPageLimit(2);
                adapter.addFragment(new FollowerFragment(),"Follower");
                adapter.addFragment(new FollowingFragment(),"Following");
                break;
            case R.id.nav_gist:
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_about:
                break;
        }
        adapter.notifyDataSetChanged();
        tab.setupWithViewPager(viewpager);

    }

    @Override
    public GitHubAccount getGithubAccount() {
        String name = PreferenceUtils.getString(this,PreferenceUtils.Key.ACCOUNT);
        if(name.isEmpty())
            name = "NO_ACCOUNT";
        Account account = new Account(name, GitHubAccount.ACCOUNT_TYPE);
        GitHubAccount githubAccount = new GitHubAccount(account,this);
        return githubAccount;
    }

    static class Adapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        private FragmentManager fm;


        public Adapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
            this.saveState();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);


            mFragmentTitles.add(title);
        }
        public void clear(){
            mFragmentTitles.clear();;
            mFragments.clear();
            notifyDataSetChanged();
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }
    }




}
