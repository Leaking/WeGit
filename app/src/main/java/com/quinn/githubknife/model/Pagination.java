package com.quinn.githubknife.model;

/**
 * Created by Quinn on 9/14/16.
 */
public class Pagination {

    private int page;
    private int perPage;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", perPage=" + perPage +
                '}';
    }
}
