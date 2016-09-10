package com.quinn.githubknife.presenter;

import android.content.Context;

import com.quinn.githubknife.view.ListFragmentView;

import java.util.List;

/**
 * Created by Quinn on 9/9/16.
 */
public class TrendingUserPresenterImpl extends PresenterAdapter {

    public TrendingUserPresenterImpl(Context context, ListFragmentView view) {
        super(context, view);
    }

    @Override
    public void onPageLoad(List<String> keywords, int page) {
        super.onPageLoad(keywords, page);

    }
}
