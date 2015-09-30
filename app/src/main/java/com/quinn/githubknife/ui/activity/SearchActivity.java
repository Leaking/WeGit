package com.quinn.githubknife.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.adapter.SuggestAdapter;
import com.quinn.githubknife.ui.fragments.SearchUserFragment;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements SearchUserFragment.TotalCountCallback, AdapterView.OnItemClickListener {

    private final static  String TAG = SearchActivity.class.getSimpleName();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        searchView.setQuery(suggestDataItems.get(position),true);
    }

    public enum SEARCH_TYPE{
        SEARCH_USER,
        SEARCH_REPO
    };

    SEARCH_TYPE search_type = SEARCH_TYPE.SEARCH_USER;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.searchSuggest)
    ListView suggestListview;

    private ArrayList<String> suggestDataItems;
    private SuggestAdapter adapter;

    private Menu menu;
    private SearchView searchView = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        suggestDataItems = new ArrayList<>();
        suggestDataItems.add("WeG");
        suggestDataItems.add("rr");
        suggestDataItems.add("hh");
        suggestDataItems.add("bbbb");

        adapter = new SuggestAdapter(this,suggestDataItems);
        suggestListview.setAdapter(adapter);
        suggestListview.setVisibility(View.GONE);
        suggestListview.setOnItemClickListener(this);





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

        searchView.setQueryHint(getResources().getString(R.string.search_user));


        //searchView.setQueryHint("");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItem.setVisible(false);
                suggestListview.setVisibility(View.VISIBLE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                UIUtils.closeInputMethod(SearchActivity.this);
                searchView.setIconified(true);
                setItem.setVisible(true);
                Bundle bundle = new Bundle();
                bundle.putString("query", query);
                bundle.putSerializable("search_type", search_type);
                SearchResultActivity.launch(SearchActivity.this, bundle);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                L.i(TAG, "onQueryTextChange : " + newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setItem.setVisible(true);
                invalidateOptionsMenu();
                suggestListview.setVisibility(View.GONE);
                return true;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    suggestListview.setVisibility(View.VISIBLE);
                } else {
                    suggestListview.setVisibility(View.GONE);
                }
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
        }else {
            currentIndex = 1;
        }

        new MaterialDialog.Builder(this)
                .title(R.string.branches)
                .items(R.array.search_type)
                .itemsCallbackSingleChoice(currentIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if(which == 0){
                            searchView.setQueryHint(getResources().getString(R.string.search_user));
                            search_type = SEARCH_TYPE.SEARCH_USER;
                        }else if(which == 1){
                            searchView.setQueryHint(getResources().getString(R.string.search_repository));

                            search_type = SEARCH_TYPE.SEARCH_REPO;
                        }
                        return true;
                    }
                })
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .cancelable(true)
                .show();
    }

    @Override
    public void setTotalCount(int count) {
        toolbar.setSubtitle("" + count);
    }
}
