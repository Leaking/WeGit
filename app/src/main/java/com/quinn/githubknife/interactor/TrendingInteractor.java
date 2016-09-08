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

    public enum SINCE_TYPE {
        SINCE_DAY,
        SINCE_WEEK,
        SINCE_MONTH
    }

    /**
     *
     * @param language
     * @param since
     */
    public void trendingRepos(String language, SINCE_TYPE since);

    public void trendingUsers(String language, String since);
}
