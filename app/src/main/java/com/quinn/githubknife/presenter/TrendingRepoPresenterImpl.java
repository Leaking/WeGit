package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.view.ListFragmentView;
import com.quinn.httpknife.github.TrendingRepo;

import java.util.List;

/**
 * Created by Quinn on 9/9/16.
 */
public class TrendingRepoPresenterImpl extends PresenterAdapter {

    private TrendingRepo.SINCE_TYPE sinceType = TrendingRepo.SINCE_TYPE.SINCE_DAY;

    public TrendingRepoPresenterImpl(Context context, ListFragmentView view) {
        super(context, view);
    }

    @Override
    public void onPageLoad(String url) {
        super.onPageLoad(url);
        interactor.trendingRepos(url, sinceType);
    }

    public TrendingRepo.SINCE_TYPE getSinceType() {
        return sinceType;
    }

    public void setSinceType(TrendingRepo.SINCE_TYPE sinceType) {
        this.sinceType = sinceType;
    }

}
