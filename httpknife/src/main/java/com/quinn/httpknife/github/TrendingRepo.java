package com.quinn.httpknife.github;

/**
 * Created by Quinn on 9/8/16.
 * 用于Trending的repo
 */
public class TrendingRepo extends Repository {

    /**
     * 新增的star数量
     */
    private int addStars;

    private SINCE_TYPE since_type;

    public SINCE_TYPE getSince_type() {
        return since_type;
    }

    public void setSince_type(SINCE_TYPE since_type) {
        this.since_type = since_type;
    }

    public int getAddStars() {
        return addStars;
    }

    public void setAddStars(int addStars) {
        this.addStars = addStars;
    }

    public enum SINCE_TYPE {
        SINCE_DAY,
        SINCE_WEEK,
        SINCE_MONTH
    }
}
