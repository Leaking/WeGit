package com.quinn.githubknife.ui.main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.utils.L;

public class MainActivity extends BaseActivity implements DrawerFragment.NavigationDrawerSelectCallbacks,DrawerFragment.NaviagtionDawerCloseCallbacks {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar.setTitle("Github");
        setSupportActionBar(toolbar);
        toolbar.setPopupTheme(R.color.theme_color);
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


    }

    private void getExistingAccountAuthToken(Account account, String authTokenType) {
        System.out.println("try to get token in MainActivyt");

        AccountManager mAccountManager = AccountManager.get(getBaseContext());
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(account, authTokenType, null, this, null, null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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
    public void onNavigationDrawerItemSelected(int position) {
        L.i("You click NavigationDrawerItem : " + position);
        Fragment fragment = null;
        if(position == 0){
            /**
             * jump to user info acitivity
             */

            return;
        }

        switch (position){
            case 1:
                fragment = new HomeFragment();
                break;
            case 2:
                fragment = new RepoFragment();
                break;
            case 3:
                fragment = new FriendFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }

        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onNavigationDrawerClose() {
        mDrawerLayout.closeDrawer(findViewById(R.id.drawerWrap));
    }
}
