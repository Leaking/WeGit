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

    @Override
    public void trendingRepos(final String language, final SINCE_TYPE since) {

        Observable.create(new Observable.OnSubscribe<List<TrendingRepo>>() {
            @Override
            public void call(Subscriber<? super List<TrendingRepo>> subscriber) {
                // make url
                ArrayList<TrendingRepo> repos = new ArrayList<>();
                String url = TRENDING_BASE_URL;
                if(language != null && language.length() > 0) {
                    url = "/" + language;
                }
                switch (since) {
                    case SINCE_DAY:
                        break;
                    case SINCE_WEEK:
                        url += "?since=weekly";
                        break;
                    case SINCE_MONTH:
                        url += "?since=monthly";
                        break;
                }
                // request
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements elements = doc.getElementsByClass("repo-list-name");
                    TrendingRepo repo;
                    for(Element element: elements) {
                        repo = new TrendingRepo();
                        Element hrefElement = element.child(0);
                        String href = hrefElement.attr("href");
                        Log.i(TAG, "href = " + href);
                        repo.setFull_name(href);

                        Element despElement = element.nextElementSibling();
                        String desp = despElement.text();
                        Log.i(TAG, "desp = " + desp);
                        repo.setDescription(desp);

                        Element newStarsElement = despElement.nextElementSibling();
                        String stars = newStarsElement.text();
                        repo.setAddStars(100);
                        Log.i(TAG, "stars = " + stars);

                        //正则解析语言、Star数量
                        Pattern patternLanguage = Pattern.compile("[A-Za-z]{1,}"); //头个单词就是语言类别
                        Pattern patternStarNum = Pattern.compile("[0-9]{1,}"); //头个数字就是Star数量
                        Matcher matcherLanguage = patternLanguage.matcher(stars);
                        Matcher matcherStarNum = patternStarNum.matcher(stars);

                        if(matcherStarNum.find()) {
                            try {
                                repo.setAddStars(Integer.parseInt(matcherStarNum.group()));
                            }catch (NumberFormatException e) {
                                e.printStackTrace();;
                            }
                        }

                        if(matcherLanguage.find()) {
                            repo.setLanguage(matcherLanguage.group());
                        }

                    }
                    subscriber.onNext(repos);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<TrendingRepo>>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onLoadItemListListener.onError(true, "");
            }

            @Override
            public void onNext(List<TrendingRepo> trendingRepos) {
                onLoadItemListListener.onFinished(trendingRepos);
            }
        });
    }

    @Override
    public void trendingUsers(String language, String since) {

    }
}
