package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.presenter.CollaboratorsPresenterImpl;
import com.quinn.githubknife.presenter.ForkersPresenterImpl;
import com.quinn.githubknife.presenter.ListFragmentPresenter;
import com.quinn.githubknife.presenter.StargazersPresenterImpl;
import com.quinn.githubknife.presenter.TreePresenterImpl;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.ToastUtils;
import com.quinn.githubknife.utils.UIUtils;
import com.quinn.githubknife.view.ListFragmentView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Quinn on 7/16/15.
 */
public abstract class BaseFragment extends Fragment implements ListFragmentView, onLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    public final static String TAG = BaseFragment.class.getSimpleName();
    protected List dataItems;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.failTxt)
    TextView failTxt;
    @Bind(R.id.progress)
    ProgressBar progress;

    private int visibleItemCount;
    private int firstVisibleItem;
    private int totalItemCount;
    protected boolean loading;
    protected boolean haveMore;
    protected int currPage;
    private LinearLayoutManager layoutManager;
    protected String user;
    protected String repo;
    protected String sha = "master";
    protected String presenterType;
    protected ListFragmentPresenter presenter;


    @Override
    public void onResume() {
        super.onResume();
        if(dataItems.isEmpty())
           loadPage();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if(bundle != null) {
            user = bundle.getString("user");
            repo = bundle.getString("repo");
            presenterType = bundle.getString("presenter");
        }
        layoutManager = new LinearLayoutManager(this.getActivity());
        loading = false;
        haveMore = true;
        if(presenter instanceof TreePresenterImpl){
            haveMore = false;
        }
        currPage = 1;
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if(haveMore && !loading && (lastVisibleItem + 1) == totalItemCount){
                    L.i("try to load more");
                    loadMore();
                }
            }
        });
        return view;
    }


    @Override
    public void loadMore() {
        loading = true;
        loadPage();
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showProgress() {
        if(currPage == 1) {
            progress.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            failTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideProgress() {
        if(currPage == 1)
            UIUtils.crossfade(progress,swipeRefreshLayout);
    }

    @Override
    public void setItems(List<?> items) {
        L.i("request items successfully");
        if(presenter instanceof TreePresenterImpl){
            return;
        }else {
            currPage++;
        }

    }

    @Override
    public void intoItem(int position) {

    }

    @Override
    public void failToLoadMore() {
        L.i("request more items fail");
        loading = false;
        ToastUtils.showMsg(this.getActivity(),R.string.fail_loadMore);
    }

    @Override
    public void failToLoadFirst(String errorMsg) {
        L.i("request items first fail");
        failTxt.setText(errorMsg + "(ERROR)");
        UIUtils.crossfade(progress,failTxt);
    }

    @Override
    public void reLoad(){
        failTxt.setText(R.string.network_error);
        UIUtils.crossfade(failTxt,progress);
        loadPage();
    }

    @OnClick(R.id.failTxt)
    void failTxt(){
        L.i(TAG,"click the fail text");
        reLoad();
    }



    public void loadPage(){
        if(presenter instanceof StargazersPresenterImpl || presenter instanceof ForkersPresenterImpl || presenter instanceof CollaboratorsPresenterImpl){
            presenter.onPageLoad(user,repo,currPage);
        }if(presenter instanceof TreePresenterImpl){
            presenter.onTreeLoad(user,repo,sha);
        }else{
            presenter.onPageLoad(currPage,user);
        }
    }



}
