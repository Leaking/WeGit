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

    public int getAddStars() {
        return addStars;
    }

    public void setAddStars(int addStars) {
        this.addStars = addStars;
    }
}
