package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TrendingActivity extends BaseActivity {

    private final static String TAG = "TrendingActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.Trending);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect("https://github.com/trending").get();
                    String title = doc.title();
                    Elements elements = doc.getElementsByClass("repo-list-name");
                    for(Element element: elements) {
                        Element hrefElement = element.child(0);
                        String href = hrefElement.attr("href");
                        Log.i(TAG, "href = " + href);

                        Element despElement = element.nextElementSibling();
                        String desp = despElement.text();
                        Log.i(TAG, "desp = " + desp);

                        Element newStarsElement = despElement.nextElementSibling();
                        String stars = newStarsElement.text();

                        Log.i(TAG, "stars = " + stars);

                    }
                    Log.i(TAG, "title = " + title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, TrendingActivity.class);
        return intent;
    }

}
