package com.quinn.githubknife.ui.activity;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.quinn.githubknife.R;
import com.quinn.githubknife.presenter.AuthPresenter;
import com.quinn.githubknife.presenter.AuthPresenterImpl;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.fragments.FollowerFragment;
import com.quinn.githubknife.ui.fragments.FollowingFragment;
import com.quinn.githubknife.ui.fragments.ReceivedEventFragment;
import com.quinn.githubknife.ui.fragments.StarredRepoFragment;
import com.quinn.githubknife.ui.fragments.UserRepoFragment;
import com.quinn.githubknife.ui.widget.AnimateFirstDisplayListener;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.PreferenceUtils;
import com.quinn.githubknife.utils.ToastUtils;
import com.quinn.githubknife.view.MainAuthView;
import com.quinn.httpknife.github.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements MainAuthView,NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private final static String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.toolbar_main)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navigationVIew;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tabs)
    TabLayout tab;

    private View headerView;
    private TextView txt_user;
    private CircleImageView img_avatar;
    private boolean doneAuth = false;

    private Adapter adapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageLoader imageLoader;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions option;
    private AuthPresenter presenter;
    private String loginUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        img_avatar = (CircleImageView) navigationVIew.findViewById(R.id.avatar);
        txt_user = (TextView)navigationVIew.findViewById(R.id.headerText);
        headerView = (View)navigationVIew.findViewById(R.id.nav_header);


        imageLoader = ImageLoader.getInstance();
        option = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).build();
        toolbar.setTitle("Github");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        toggle.syncState();
        mDrawerLayout.setDrawerListener(toggle);
        navigationVIew.setNavigationItemSelectedListener(this);
        adapter = new Adapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        presenter = new AuthPresenterImpl(this,this);
        presenter.auth();
        headerView.setOnClickListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if(doneAuth) {
            L.i(TAG, "menuItem id = " + menuItem.getItemId() + " order = " + menuItem.getOrder());
            mDrawerLayout.closeDrawers();
            setUpTab(menuItem.getItemId());
            if(menuItem.getItemId() == R.id.nav_home || menuItem.getItemId() == R.id.nav_friends)
                menuItem.setChecked(true);
        }
        return true;
    }

    public void setUpTab(int id){

        switch (id){
            case R.id.nav_home:
                adapter.clear();
                viewpager.setOffscreenPageLimit(3);
                adapter.addFragment(ReceivedEventFragment.getInstance(loginUser), "Events");
                adapter.addFragment(StarredRepoFragment.getInstance(loginUser),"Starred");
                adapter.addFragment(UserRepoFragment.getInstance(loginUser),"Repository");
                adapter.notifyDataSetChanged();
                tab.setupWithViewPager(viewpager);

                break;
            case R.id.nav_friends:
                adapter.clear();
                viewpager.setOffscreenPageLimit(2);
                adapter.addFragment(FollowerFragment.getInstance(loginUser),"Follower");
                adapter.addFragment(FollowingFragment.getInstance(loginUser),"Following");
                adapter.notifyDataSetChanged();
                tab.setupWithViewPager(viewpager);
                break;
            case R.id.nav_search:{
                Intent intent = new Intent(this,SearchActivity.class);
                this.startActivity(intent);
                //ToastUtils.showMsg(this, R.string.developing);
                break;
            }
            case R.id.nav_setting:
                ToastUtils.showMsg(this, R.string.developing);
                return;
            case R.id.nav_about:
                Intent intent = new Intent(this,AboutActivity.class);
                this.startActivity(intent);
                break;
        }


    }



    @Override
    public void doneAuth(User user) {
        if(user == null){
            this.user = new User();
        }else {
            this.user = user;
        }
        String avatar = "";
        if(user != null && user.getAvatar_url() != null && !user.getAvatar_url().isEmpty())
            avatar = user.getAvatar_url();
        if(avatar.isEmpty() == false)
            PreferenceUtils.putString(this, PreferenceUtils.Key.AVATAR,avatar);
        else{
            avatar = PreferenceUtils.getString(this, PreferenceUtils.Key.AVATAR);
        }
        loginUser = PreferenceUtils.getString(this, PreferenceUtils.Key.ACCOUNT);
        this.user.setAvatar_url(avatar);
        this.user.setLogin(loginUser);
        txt_user.setText(loginUser);
        imageLoader.displayImage(avatar,img_avatar,option,animateFirstListener);
        tab.setupWithViewPager(viewpager);
        setUpTab(R.id.nav_home);
        doneAuth = true;
    }


    @Override
    public void onClick(View v) {
        if(doneAuth){
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            UserInfoActivity.launch(this, bundle);
        }
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
