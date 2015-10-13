package com.quinn.httpknife.github;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Quinn on 10/13/15.
 */
public class UserSearch implements Serializable {

    private static final long serialVersionUID = -5574001261010258032L;
    private int total_count;
    private boolean incomplete_results;
    private List<User> items;


    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
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
        return "UserSearch{" +
                "incomplete_results=" + incomplete_results +
                ", total_count=" + total_count +
                ", items=" + items +
                '}';
    }
}

