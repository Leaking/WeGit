package com.quinn.githubknife.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.fragments.SearchDescriptionFragment;
import com.quinn.githubknife.ui.fragments.SearchUserFragment;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements SearchUserFragment.TotalCountCallback{

    private final static  String TAG = SearchActivity.class.getSimpleName();

    public enum SEARCH_TYPE{
        SEARCH_USER,
        SEARCH_REPO
    };

    SEARCH_TYPE search_type = SEARCH_TYPE.SEARCH_USER;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.container)
    FrameLayout container;

    private Menu menu;
    private SearchView searchView = null;
    private Fragment searchDescriptionrFragment;
    private Fragment searchUserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        searchDescriptionrFragment = new SearchDescriptionFragment();
        fragmentTransaction.replace(R.id.container, searchDescriptionrFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        this.menu = menu;

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem setItem = menu.findItem(R.id.action_set);

        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);


        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
        }

        //searchView.setQueryHint("");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i(TAG, "click action_search");
                setItem.setVisible(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                UIUtils.closeInputMethod(SearchActivity.this);
                searchView.setIconified(true);
                setItem.setVisible(true);
                Bundle bundle = new Bundle();
                bundle.putString("query",query);
                bundle.putSerializable("search_type", search_type);
                SearchResultActivity.launch(SearchActivity.this,bundle);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                L.i(TAG,"onQueryTextChange : " + newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setItem.setVisible(true);
                invalidateOptionsMenu();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_set) {
            showPreferenceDialog();
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showPreferenceDialog(){
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        int currentIndex = 0;
        if(search_type == SEARCH_TYPE.SEARCH_USER){
            currentIndex = 0;
        }else{
            currentIndex = 1;
        }
        builder.setSingleChoiceItems(R.array.search_type, currentIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    searchView.setQueryHint(getResources().getString(R.string.search_user));
                    search_type = SEARCH_TYPE.SEARCH_USER;
                }else if(which == 1){
                    searchView.setQueryHint(getResources().getString(R.string.search_repository));
                    search_type = SEARCH_TYPE.SEARCH_REPO;
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void setTotalCount(int count) {
        toolbar.setSubtitle("" + count);
    }
}
