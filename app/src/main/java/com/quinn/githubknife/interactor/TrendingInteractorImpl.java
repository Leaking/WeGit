package com.quinn.githubknife.interactor;

import android.content.Context;
import android.util.Log;

import com.quinn.githubknife.listener.OnLoadItemListListener;
import com.quinn.httpknife.github.TrendingRepo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Quinn on 9/9/16.
 */
public class TrendingInteractorImpl implements TrendingInteractor {

    public static final String TAG = "TrendingInteractorImpl";


    private OnLoadItemListListener onLoadItemListListener;

    public TrendingInteractorImpl(OnLoadItemListListener onLoadItemListListener) {
        this.onLoadItemListListener = onLoadItemListListener;
    }

}
