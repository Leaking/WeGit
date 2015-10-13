package com.quinn.httpknife.github;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Quinn on 10/13/15.
 */
public class RepoSearch implements Serializable{


    private static final long serialVersionUID = -636105145487277825L;
    private int total_count;
    private boolean incomplete_results;
    private List<Repository> items;


    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<Repository> getItems() {
        return items;
    }

    public void setItems(List<Repository> items) {
        this.items = items;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    @Override
    public String toString() {
        return "RepoSearch{" +
                "incomplete_results=" + incomplete_results +
                ", total_count=" + total_count +
                ", items=" + items +
                '}';
    }
}
