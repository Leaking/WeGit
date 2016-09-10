package com.quinn.githubknife.interactor;

/**
 * Created by Quinn on 9/9/16.
 */
public interface TrendingInteractor {

    public static final String TRENDING_BASE_URL = "https://github.com/trending";

    public static final String SINCE_WEEK = "weekly";
    public static final String SINCE_MONTH = "monthly";



    public final String[] TRENDING_LANGUAGES = {"", "cpp", "java", "css", "go"};

    public final String[] TRENDING_SINCE_TIMES = {};




}
